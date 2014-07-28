package com.u1fukui.lunch.app;

import android.content.Context;
import android.location.Location;

import com.u1fukui.lunch.app.model.SLRestaurant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

public class SLRestaurantManager {

  private static SLRestaurantManager sInstance;

  private ArrayList<SLRestaurant> mRestaurantList;
  private ArrayList<SLRestaurant> mFilteredRestaurantList;

  /** HH:mm */
  private String mFilterTime;

  /** 現在地 */
  private Location mCurrentLocation;

  private SLRestaurantManager() {
    mRestaurantList = new ArrayList<SLRestaurant>();
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
          mRestaurantList.add(r);
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
    if (mFilteredRestaurantList != null) {
      return (ArrayList<SLRestaurant>) mFilteredRestaurantList.clone();
    }
    return (ArrayList<SLRestaurant>) mRestaurantList.clone();
  }

  public void setCurrentLocaton(Location location) {
    mCurrentLocation = location;
  }

  public Location getCurrentLocation() {
    return mCurrentLocation;
  }


  //================================================================================
  // filter
  //================================================================================

  public void filter(String filterTime) {
    mFilterTime = filterTime;
    mFilteredRestaurantList = new ArrayList<SLRestaurant>();
    for (SLRestaurant r : mRestaurantList) {
      if (isFilterRange(mFilterTime, r)) {
        mFilteredRestaurantList.add(r);
      }
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

  public void sortInOrderOfDistace() {
    if (mCurrentLocation == null) {
      return;
    }

    if (mFilteredRestaurantList == null) {
      mFilteredRestaurantList = mRestaurantList;
    }

    TreeMap<Float, SLRestaurant> map = new TreeMap<Float, SLRestaurant>();
    for (SLRestaurant r : mFilteredRestaurantList) {
      float[] results = new float[1];
      Location.distanceBetween(mCurrentLocation.getLatitude(),
          mCurrentLocation.getLongitude(), r.lat, r.lng, results);
      map.put(Float.valueOf(results[0]), r);
    }

    mFilteredRestaurantList = new ArrayList<SLRestaurant>();
    for (SLRestaurant r : map.values()) {
      mFilteredRestaurantList.add(r);
    }
  }
}