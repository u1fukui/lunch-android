package com.u1fukui.lunch.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.model.SLRestaurant;

public class RestaurantSlideItem extends LinearLayout {

  private RestaurantListItem mDetailView;

  public RestaurantSlideItem(Context context) {
    this(context, null);
  }

  public RestaurantSlideItem(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RestaurantSlideItem(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    setBackgroundColor(0xffffffff);

    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rootView = inflater.inflate(R.layout.restaurant_slide_item, this, true);

    mDetailView = (RestaurantListItem) rootView.findViewById(R.id.slide_item_detail);
  }

  public void setRestaurant(SLRestaurant restaurant) {
    mDetailView.setRestaurant(restaurant);
  }

}
