package com.u1fukui.lunch.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.SLRestaurantManager;
import com.u1fukui.lunch.app.fragment.RestaurantListMapFragment;
import com.u1fukui.lunch.app.fragment.RestaurantListFragment;

public class MainActivity extends BaseActivity {

  private static final int MENU_FILTER  = 1;
  private static final int MENU_MAIL    = 2;

  private static final String TAB_LIST = "tab_list";
  private static final String TAB_MAP = "tab_map";

  private FragmentTabHost mTabHost;
  private TextView mFilterDescriptionView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mFilterDescriptionView = (TextView) findViewById(R.id.main_filter_description);
    mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
    mTabHost.setup(this, getSupportFragmentManager(), R.id.content);

    TabSpec tabSpec1 = mTabHost.newTabSpec(TAB_LIST);
    ImageButton button1 = new ImageButton(this);
    button1.setImageResource(R.drawable.tab_list_icon);
    button1.setBackgroundResource(0);
    tabSpec1.setIndicator(button1);
    mTabHost.addTab(tabSpec1, RestaurantListFragment.class, null);

    TabSpec tabSpec2 = mTabHost.newTabSpec(TAB_MAP);
    ImageButton button2 = new ImageButton(this);
    button2.setImageResource(R.drawable.tab_map_icon);
    button2.setBackgroundResource(0);
    tabSpec2.setIndicator(button2);
    mTabHost.addTab(tabSpec2, RestaurantListMapFragment.class, null);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // 表示する条件を設定
    MenuItem filterItem = menu.add(Menu.NONE, MENU_FILTER, Menu.NONE, null);
    filterItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    filterItem.setIcon(R.drawable.clock);

    // 開発者にメール
    MenuItem mailItem = menu.add(Menu.NONE, MENU_MAIL, Menu.NONE, null);
    mailItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    mailItem.setIcon(R.drawable.misc);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case MENU_FILTER:
        showTimeFilterDialog();
        return true;
      case MENU_MAIL:
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void showTimeFilterDialog() {
    final String[] items = getResources().getStringArray(R.array.filter_time_array);

    new AlertDialog.Builder(this)
        .setTitle("指定した時間に開いているお店を表示")
        .setItems(items, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            SLRestaurantManager manager = SLRestaurantManager.getInstance();
            if (which == 0) {
              manager.filter(null);
            } else {
              manager.filter(items[which]);
              manager.sortInOrderOfDistace();
              mFilterDescriptionView.setText(manager.getFilterTime() + "に営業しているお店を表示");
            }
          }
        })
        .show();
  }
}
