package com.u1fukui.lunch.app;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;

import com.u1fukui.lunch.app.model.SLRestaurant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SLApplication extends Application {

  private static final String TAG = SLApplication.class.getSimpleName();

  private static SLApplication sApplication;

  private List<SLRestaurant> mRestaurantList;

  @Override
  public void onCreate() {
    super.onCreate();
    mRestaurantList = loadRestaurantList();
    sApplication = this;
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    sApplication = null;
  }

  public static SLApplication getInstance() {
    if (sApplication != null) {
      return sApplication;
    } else {
      throw new RuntimeException("Application is not attached.");
    }
  }

  public List<SLRestaurant> getRestaurantList() {
    return mRestaurantList;
  }

  private List<SLRestaurant> loadRestaurantList() {
    List<SLRestaurant> list = new ArrayList<SLRestaurant>();
    try {
      InputStream is = getResources().getAssets().open("lunch.csv");
      InputStreamReader isr = new InputStreamReader(is, "UTF-8");
      BufferedReader reader = new BufferedReader(isr);
      try {
        String line;
        while ((line = reader.readLine()) != null) {
          String[] array = line.split(",");
          SLRestaurant r = new SLRestaurant();
          r.id = array[0];
          r.name = array[1];
          r.address = array[2];
          r.lat = Double.parseDouble(array[3]);
          r.lng = Double.parseDouble(array[4]);
          r.featuredMenu = array[5];
          r.startLunchTime = array[6];
          r.finishLunchTime = array[7];
          r.holiday = array[8];
          r.tabelogUrl = array[9];
          r.comment = array[10];
          r.thumbnailName = array[11];
          list.add(r);
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      } finally {
        try {
          is.close();
          isr.close();
        } catch (IOException e) {
          // handle exception
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      return list;
    }
    return list;
  }
}
