package com.u1fukui.lunch.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
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

  public interface OnFilterListener {
    public void onFilter();
  }

  private static final int MENU_FILTER  = 1;
  private static final int MENU_MAIL    = 2;

  private static final String TAB_LIST = "tab_list";
  private static final String TAB_MAP = "tab_map";

  private FragmentTabHost mTabHost;
  private TextView mFilterDescriptionView;

  private OnFilterListener mFilterListener;

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
        showMailConfirmDialog();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public void setOnFilterListener(OnFilterListener listener) {
    mFilterListener = listener;
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
              manager.sortInOrderOfDistace();
              setFilterDescriotion();
            } else {
              manager.filter(items[which]);
              manager.sortInOrderOfDistace();
              setFilterDescriotion();
            }
            if (mFilterListener != null) {
              mFilterListener.onFilter();
            }
          }
        })
        .show();
  }

  public void setFilterDescriotion() {
    StringBuilder sb = new StringBuilder();

    String filterTime = SLRestaurantManager.getInstance().getFilterTime();
    if (filterTime != null) {
      sb.append(filterTime).append("に営業しているお店を");
    } else {
      sb.append("全てのお店を");
    }

    Location location = SLRestaurantManager.getInstance().getCurrentLocation();
    if (location != null) {
      sb.append("近い順に");
    }
    sb.append("表示");

    mFilterDescriptionView.setText(sb.toString());
  }

  private void showMailConfirmDialog() {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder.setTitle("開発者に教える");
    alertDialogBuilder.setMessage("情報の間違いや、他にオススメのお店の情報などあれば教えて下さいm(_ _)m");
    alertDialogBuilder.setPositiveButton("メールする",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:u1fukuicom+lunch@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "渋谷500円ランチ");
            startActivity(intent);
          }
        }
    );
    alertDialogBuilder.setNegativeButton("キャンセル",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
          }
        }
    );
    alertDialogBuilder.setCancelable(true);
    alertDialogBuilder.create().show();
  }
}
