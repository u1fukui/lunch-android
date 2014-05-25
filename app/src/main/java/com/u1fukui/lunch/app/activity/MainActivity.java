package com.u1fukui.lunch.app.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.adapter.RestaurantListAdapter;
import com.u1fukui.lunch.app.fragment.RestaurantListFragment;

public class MainActivity extends FragmentActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    FragmentTabHost host = (FragmentTabHost) findViewById(android.R.id.tabhost);
    host.setup(this, getSupportFragmentManager(), R.id.content);

    TabSpec tabSpec1 = host.newTabSpec("tab1");
    Button button1 = new Button(this);
    button1.setText("お店リスト");
    tabSpec1.setIndicator(button1);
    host.addTab(tabSpec1, RestaurantListFragment.class, null);

    TabSpec tabSpec2 = host.newTabSpec("tab2");
    Button button2 = new Button(this);
    button2.setText("地図");
    tabSpec2.setIndicator(button2);
    host.addTab(tabSpec2, RestaurantListFragment.class, null);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

}
