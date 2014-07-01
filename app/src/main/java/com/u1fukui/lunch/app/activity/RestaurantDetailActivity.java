package com.u1fukui.lunch.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;

import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.model.SLRestaurant;
import com.u1fukui.lunch.app.view.RestaurantDetailItem;

public class RestaurantDetailActivity extends FragmentActivity {

  private RestaurantDetailItem mAddressItem;
  private RestaurantDetailItem mTimeItem;
  private RestaurantDetailItem mHolidayItem;
  private RestaurantDetailItem mMenuItem;
  private RestaurantDetailItem mCommentItem;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_detail);

    mAddressItem = (RestaurantDetailItem) findViewById(R.id.detail_address);
    mTimeItem = (RestaurantDetailItem) findViewById(R.id.detail_time);
    mHolidayItem = (RestaurantDetailItem) findViewById(R.id.detail_holiday);
    mMenuItem = (RestaurantDetailItem) findViewById(R.id.detail_menu);
    mCommentItem = (RestaurantDetailItem) findViewById(R.id.detail_comment);

    SLRestaurant restaurant = (SLRestaurant) getIntent()
        .getSerializableExtra(SLRestaurant.EXTRA_RESTAURANT);

    mAddressItem.setItem("住所", restaurant.address);
    mTimeItem.setItem("ランチ\nタイム", restaurant.getLunchTimeString());
    mHolidayItem.setItem("定休日", restaurant.holiday);
    mMenuItem.setItem("メニュー", restaurant.featuredMenu);
    mCommentItem.setItem("補足", restaurant.comment);
  }
}
