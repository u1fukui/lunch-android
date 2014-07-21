package com.u1fukui.lunch.app.activity;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.u1fukui.lunch.app.R;

public class BaseActivity extends FragmentActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ActionBar bar = getActionBar();
    bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.theme_color)));
    bar.setDisplayShowHomeEnabled(false);
  }
}
