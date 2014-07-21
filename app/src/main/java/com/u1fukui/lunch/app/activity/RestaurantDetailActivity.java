package com.u1fukui.lunch.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.adapter.PhotoPagerAdapter;
import com.u1fukui.lunch.app.model.SLRestaurant;
import com.u1fukui.lunch.app.view.RestaurantDetailItem;

public class RestaurantDetailActivity extends BaseActivity {

  private ViewPager mPager;
  private RestaurantDetailItem mAddressItem;
  private RestaurantDetailItem mTimeItem;
  private RestaurantDetailItem mHolidayItem;
  private RestaurantDetailItem mMenuItem;
  private RestaurantDetailItem mCommentItem;
  private ImageButton mTabelogButton;
  private ImageButton mMapButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_detail);

    mPager = (ViewPager) findViewById(R.id.detail_pager);
    mAddressItem = (RestaurantDetailItem) findViewById(R.id.detail_address);
    mTimeItem = (RestaurantDetailItem) findViewById(R.id.detail_time);
    mHolidayItem = (RestaurantDetailItem) findViewById(R.id.detail_holiday);
    mMenuItem = (RestaurantDetailItem) findViewById(R.id.detail_menu);
    mCommentItem = (RestaurantDetailItem) findViewById(R.id.detail_comment);
    mTabelogButton = (ImageButton) findViewById(R.id.detail_tabelog_button);
    mMapButton = (ImageButton) findViewById(R.id.detail_map_button);

    final SLRestaurant restaurant = (SLRestaurant) getIntent()
        .getSerializableExtra(SLRestaurant.EXTRA_RESTAURANT);

    mAddressItem.setItem("住所", restaurant.address);
    mTimeItem.setItem("ランチ\nタイム", restaurant.getLunchTimeString());
    mHolidayItem.setItem("定休日", restaurant.holiday);
    mMenuItem.setItem("メニュー", restaurant.featuredMenu);
    mCommentItem.setItem("補足", restaurant.comment);

    PhotoPagerAdapter adapter = new PhotoPagerAdapter(this,
        restaurant.thumbnailName, restaurant.thumbnailCount);
    mPager.setAdapter(adapter);

    mMapButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(RestaurantDetailActivity.this,
            RestaurantMapActivity.class);
        intent.putExtra(SLRestaurant.EXTRA_RESTAURANT, restaurant);
        startActivity(intent);
      }
    });

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
}
