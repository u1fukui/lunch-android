package com.u1fukui.lunch.app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.model.SLRestaurant;

import java.io.IOException;
import java.io.InputStream;

public class RestaurantListItem extends RelativeLayout {

  private Context mContext;

  private ImageView mThumbnailView;
  private TextView mNameView;
  private TextView mTimeView;
  private TextView mHolidayView;
  private TextView mMenuView;

  public RestaurantListItem(Context context) {
    this(context, null);
  }

  public RestaurantListItem(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RestaurantListItem(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    mContext = context;
    setBackgroundColor(0xffffffff);

    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rootView = inflater.inflate(R.layout.restaurant_list_item, this, true);

    mThumbnailView = (ImageView) rootView.findViewById(R.id.restaurant_thumbnail);
    mNameView = (TextView) rootView.findViewById(R.id.restaurant_name);
    mTimeView = (TextView) rootView.findViewById(R.id.restaurant_time);
    mHolidayView = (TextView) rootView.findViewById(R.id.restaurant_holiday);
    mMenuView = (TextView) rootView.findViewById(R.id.restaurant_menu);
  }

  public void setRestaurant(SLRestaurant restaurant) {
    mNameView.setText(restaurant.name);
    mTimeView.setText(restaurant.getLunchTimeString());
    mHolidayView.setText(restaurant.holiday);
    mMenuView.setText(restaurant.featuredMenu);
    mThumbnailView.setImageBitmap(getThumbnailBitmap(restaurant.thumbnailName));
  }

  private Bitmap getThumbnailBitmap(String thumbnailName) {
    try {
      String path = "thumbnails/" + thumbnailName + "1@2x.jpg";
      InputStream is = mContext.getAssets().open(path);
      if (is != null) {
        return BitmapFactory.decodeStream(is);
      }
    } catch (IOException e) {
      Log.e("TAG", "IOException", e);
    }
    return null;
  }

}
