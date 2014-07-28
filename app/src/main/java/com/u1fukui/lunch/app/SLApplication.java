package com.u1fukui.lunch.app;

import android.app.Application;

public class SLApplication extends Application {

  private static SLApplication sApplication;
  private static SLRestaurantManager sRestaurantManager;
  private static float sDensity;

  @Override
  public void onCreate() {
    super.onCreate();

    sApplication = this;
    sRestaurantManager = SLRestaurantManager.getInstance();
    sRestaurantManager.loadRestaurantList(this);

    sDensity = getResources().getDisplayMetrics().density;
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

  public static int dipToPx(int dp) {
    return (int) (dp * sDensity);
  }

}
