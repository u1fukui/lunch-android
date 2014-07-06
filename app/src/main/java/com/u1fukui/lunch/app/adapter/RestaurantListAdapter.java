package com.u1fukui.lunch.app.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.model.SLRestaurant;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RestaurantListAdapter extends ArrayAdapter<SLRestaurant> {

  private Context mContext;
  private LayoutInflater mInflater;

  public RestaurantListAdapter(Context context, List<SLRestaurant> itemList) {
    super(context, 0, itemList);
    mContext = context;
    mInflater = LayoutInflater.from(context);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.restaurant_list_item, parent, false);
      holder = new ViewHolder();
      holder.thumbnailView = (ImageView) convertView.findViewById(R.id.restaurant_thumbnail);
      holder.nameView = (TextView) convertView.findViewById(R.id.restaurant_name);
      holder.timeView = (TextView) convertView.findViewById(R.id.restaurant_time);
      holder.holidayView = (TextView) convertView.findViewById(R.id.restaurant_holiday);
      holder.menuView = (TextView) convertView.findViewById(R.id.restaurant_menu);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    SLRestaurant restaurant = getItem(position);

    //TODO: ちゃんとしたやり方に書き換える
    try {
      String path = "thumbnails/" + restaurant.thumbnailName + "1@2x.jpg";
      InputStream is = mContext.getAssets().open(path);
      if (is != null) {
        holder.thumbnailView.setImageBitmap(BitmapFactory.decodeStream(is));
      }
    } catch (IOException e) {
      Log.e("TAG", "IOException", e);
    }

    holder.nameView.setText(restaurant.name);
    holder.timeView.setText(restaurant.getLunchTimeString());
    holder.holidayView.setText(restaurant.holiday);
    holder.menuView.setText(restaurant.featuredMenu);
    return convertView;
  }


  private static class ViewHolder {
    ImageView thumbnailView;
    TextView nameView;
    TextView timeView;
    TextView holidayView;
    TextView menuView;
  }
}
