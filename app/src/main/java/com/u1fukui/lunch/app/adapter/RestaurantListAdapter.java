package com.u1fukui.lunch.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.model.SLRestaurant;

import java.util.List;

public class RestaurantListAdapter extends ArrayAdapter<SLRestaurant> {

  private LayoutInflater mInflater;

  public RestaurantListAdapter(Context context, List<SLRestaurant> itemList) {
    super(context, 0, itemList);
    mInflater = LayoutInflater.from(context);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.restaurant_list_item, parent, false);
      holder = new ViewHolder();
      holder.nameView = (TextView) convertView.findViewById(R.id.restaurant_name);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    SLRestaurant restaurant = getItem(position);
    holder.nameView.setText(restaurant.name);
    return convertView;
  }


  private static class ViewHolder {
    TextView nameView;
  }
}
