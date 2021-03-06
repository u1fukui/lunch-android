package com.u1fukui.lunch.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.u1fukui.lunch.app.R;

public class RestaurantDetailItem extends RelativeLayout {

  private TextView mTitleView;
  private TextView mDescriptionView;
  private View mDivider1;
  private View mDivider2;

  public RestaurantDetailItem(Context context) {
    this(context, null);
  }

  public RestaurantDetailItem(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RestaurantDetailItem(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    View root = View.inflate(context, R.layout.restaurant_detail_item, this);
    mTitleView = (TextView) root.findViewById(R.id.detail_item_title);
    mDescriptionView = (TextView) root.findViewById(R.id.detail_item_description);
    mDivider1 = root.findViewById(R.id.detail_item_bottom_divider1);
    mDivider2 = root.findViewById(R.id.detail_item_bottom_divider2);
  }

  public void setItem(String title, String description) {
    mTitleView.setText(title);
    mDescriptionView.setText(description);
  }

  public void hideDivider() {
    mDivider1.setVisibility(View.GONE);
    mDivider2.setVisibility(View.GONE);
  }

  public void showDivider() {
    mDivider1.setVisibility(View.VISIBLE);
    mDivider2.setVisibility(View.VISIBLE);
  }
}