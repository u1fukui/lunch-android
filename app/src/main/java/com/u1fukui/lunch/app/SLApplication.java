package com.u1fukui.lunch.app;

import android.app.Application;

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
