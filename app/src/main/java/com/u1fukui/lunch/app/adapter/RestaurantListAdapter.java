package com.u1fukui.lunch.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.u1fukui.lunch.app.model.SLRestaurant;
import com.u1fukui.lunch.app.view.RestaurantListItem;

import java.util.List;

public class RestaurantListAdapter extends ArrayAdapter<SLRestaurant> {

  private Context mContext;

  public RestaurantListAdapter(Context context, List<SLRestaurant> itemList) {
    super(context, 0, itemList);
    mContext = context;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    RestaurantListItem itemView;
    if (convertView == null) {
      itemView = new RestaurantListItem(mContext);
    } else {
      itemView = (RestaurantListItem) convertView;
    }
    itemView.setRestaurant(getItem(position));
    return itemView;
  }
}
