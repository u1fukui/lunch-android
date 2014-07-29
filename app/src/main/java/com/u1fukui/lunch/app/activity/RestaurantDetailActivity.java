package com.u1fukui.lunch.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.SLApplication;
import com.u1fukui.lunch.app.adapter.PhotoPagerAdapter;
import com.u1fukui.lunch.app.model.SLRestaurant;
import com.u1fukui.lunch.app.view.RestaurantDetailItem;
import com.viewpagerindicator.CirclePageIndicator;

public class RestaurantDetailActivity extends FragmentActivity {

  private ViewPager mPager;
  private CirclePageIndicator mPageIndicator;
  private RestaurantDetailItem mAddressItem;
  private RestaurantDetailItem mTimeItem;
  private RestaurantDetailItem mHolidayItem;
  private RestaurantDetailItem mMenuItem;
  private RestaurantDetailItem mCommentItem;
  private ImageButton mTabelogButton;
  private ImageButton mMapButton;

  private SLRestaurant mRestaurant;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_detail);

    mPager = (ViewPager) findViewById(R.id.detail_pager);
    mPageIndicator = (CirclePageIndicator) findViewById(R.id.page_indicator);
    mAddressItem = (RestaurantDetailItem) findViewById(R.id.detail_address);
    mTimeItem = (RestaurantDetailItem) findViewById(R.id.detail_time);
    mHolidayItem = (RestaurantDetailItem) findViewById(R.id.detail_holiday);
    mMenuItem = (RestaurantDetailItem) findViewById(R.id.detail_menu);
    mCommentItem = (RestaurantDetailItem) findViewById(R.id.detail_comment);
    mTabelogButton = (ImageButton) findViewById(R.id.detail_tabelog_button);
    mMapButton = (ImageButton) findViewById(R.id.detail_map_button);

    setViewPagerHeight(mPager);
    mPageIndicator.setRadius(SLApplication.dipToPx(4));
    mPageIndicator.setFillColor(getResources().getColor(R.color.theme_color));
    mPageIndicator.setPageColor(0xffcccccc);

    // お店情報をセット
    final SLRestaurant restaurant = (SLRestaurant) getIntent()
        .getSerializableExtra(SLRestaurant.EXTRA_RESTAURANT);
    showRestaurant(restaurant);
    PhotoPagerAdapter adapter = new PhotoPagerAdapter(this,
        restaurant.thumbnailName, restaurant.thumbnailCount);
    mPager.setAdapter(adapter);
    mPageIndicator.setViewPager(mPager);

    // 地図ボタン
    mMapButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(RestaurantDetailActivity.this,
            RestaurantMapActivity.class);
        intent.putExtra(SLRestaurant.EXTRA_RESTAURANT, restaurant);
        startActivity(intent);
      }
    });

    // 食べログボタン
    mTabelogButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(RestaurantDetailActivity.this,
            TabelogActivity.class);
        intent.putExtra(TabelogActivity.EXTRA_URL, restaurant.tabelogUrl);
        startActivity(intent);
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuItem filterItem = menu.add(Menu.NONE, 0, Menu.NONE, null);
    filterItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    filterItem.setIcon(R.drawable.share);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder.setMessage("お店情報を友達に教える");
    alertDialogBuilder.setPositiveButton("メールする",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_TEXT, mRestaurant.name + "\n" + mRestaurant.tabelogUrl);
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
    return true;
  }

  private void setViewPagerHeight(ViewPager pager) {
    Display display = getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    pager.getLayoutParams().height = size.x * 3 / 4;
  }

  private void showRestaurant(SLRestaurant restaurant) {
    mRestaurant = restaurant;

    getActionBar().setTitle(restaurant.name);
    mAddressItem.setItem("住所", restaurant.address);
    mTimeItem.setItem("ランチ\nタイム", restaurant.getLunchTimeString());
    mHolidayItem.setItem("定休日", restaurant.holiday);
    mMenuItem.setItem("メニュー", restaurant.featuredMenu);
    mCommentItem.setItem("補足", restaurant.comment);
    mCommentItem.hideDivider();
  }

}
