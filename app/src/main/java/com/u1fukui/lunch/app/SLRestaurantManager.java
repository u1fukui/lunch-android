package com.u1fukui.lunch.app;

import android.content.Context;
import android.util.Log;

import com.u1fukui.lunch.app.model.SLRestaurant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SLRestaurantManager {

  public interface OnFilterListener {
    public void onFilter();
  }

  private static SLRestaurantManager sInstance;

  private ArrayList<SLRestaurant> mRestaurantArray;
  private ArrayList<SLRestaurant> mFilteredRestaurantArray;
  private OnFilterListener mFilterListener;

  /** HH:mm */
  private String mFilterTime;

  private SLRestaurantManager() {
    mRestaurantArray = new ArrayList<SLRestaurant>();
  }

  public static SLRestaurantManager getInstance() {
    if (sInstance == null) {
      sInstance = new SLRestaurantManager();
    }
    return sInstance;
  }

  public void loadRestaurantList(Context context) {
    try {
      InputStream is = context.getAssets().open("lunch.csv");
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
          r.thumbnailCount = Integer.parseInt(array[12]);
          mRestaurantArray.add(r);
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
    }
  }


  //================================================================================
  // getter / setter
  //================================================================================

  public String getFilterTime() {
    return mFilterTime;
  }

  public ArrayList<SLRestaurant> getFilteredRestaurantArray() {
    if (mFilteredRestaurantArray != null) {
      return mFilteredRestaurantArray;
    }
    return mRestaurantArray;
  }

  public void setOnFilterListener(OnFilterListener listener) {
    mFilterListener = listener;
  }


  //================================================================================
  // filter
  //================================================================================

  public void filter(String filterTime) {
    mFilterTime = filterTime;
    mFilteredRestaurantArray = new ArrayList<SLRestaurant>();
    for (SLRestaurant r : mRestaurantArray) {
      if (isFilterRange(mFilterTime, r)) {
        mFilteredRestaurantArray.add(r);
      }
    }

    if (mFilterListener != null) {
      mFilterListener.onFilter();
    }
  }

  private boolean isFilterRange(String filterTime, SLRestaurant r) {
    if (filterTime == null) {
      return true;
    }

    int filter = integerFromTimeString(filterTime);
    int start = integerFromTimeString(r.startLunchTime);
    int finish = integerFromTimeString(r.finishLunchTime);

    return filter >= start && filter < finish;
  }

  private int integerFromTimeString(String filterTime) {
    String[] array = filterTime.split(":");
    return Integer.parseInt(array[0] + array[1]);
  }
}