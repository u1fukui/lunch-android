package com.u1fukui.lunch.app;

import android.app.Application;

public class SLApplication extends Application {

  private static SLRestaurantManager sRestaurantManager;
  private static float sDensity;

  @Override
  public void onCreate() {
    super.onCreate();

    sRestaurantManager = SLRestaurantManager.getInstance();
    sRestaurantManager.loadRestaurantList(this);
    sDensity = getResources().getDisplayMetrics().density;
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    sRestaurantManager = null;
  }

  public static int dipToPx(int dp) {
    return (int) (dp * sDensity);
  }

}
