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
  private static SLRestaurantManager sRestaurantManager;

  @Override
  public void onCreate() {
    super.onCreate();

    sApplication = this;
    sRestaurantManager = SLRestaurantManager.getInstance();
    sRestaurantManager.loadRestaurantList(this);
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    sApplication = null;
    sRestaurantManager = null;
  }

  public static SLApplication getInstance() {
    if (sApplication != null) {
      return sApplication;
    } else {
      throw new RuntimeException("Application is not attached.");
    }
  }
}
