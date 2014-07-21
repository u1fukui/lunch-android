package com.u1fukui.lunch.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.lunch.app.model.SLRestaurant;
import com.u1fukui.lunch.app.view.RestaurantListItem;

import java.util.List;

public class RestaurantPagerAdapter extends PagerAdapter {

  private Context mContext;
  private List<SLRestaurant> mItemList;

  public RestaurantPagerAdapter(Context context, List<SLRestaurant> itemList) {
    mContext = context;
    mItemList = itemList;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    RestaurantListItem itemView = new RestaurantListItem(mContext);
    itemView.setRestaurant(mItemList.get(position));
    container.addView(itemView);
    return itemView;
  }

  @Override
  public int getCount() {
    return mItemList.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view.equals(object);
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }
}
