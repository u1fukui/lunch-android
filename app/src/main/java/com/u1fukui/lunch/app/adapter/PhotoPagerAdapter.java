package com.u1fukui.lunch.app.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PhotoPagerAdapter extends PagerAdapter {

  private Context mContext;
  private String mFileName;
  private int mCount;

  public PhotoPagerAdapter(Context context, String fileName, int count) {
    mContext = context;
    mFileName = fileName;
    mCount = count;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    try {
      String path = "thumbnails/" + mFileName + Integer.toString(position + 1) + "@2x.jpg";
      InputStream is = mContext.getAssets().open(path);
      if (is != null) {
        ImageView photoView = new ImageView(mContext);
        photoView.setImageBitmap(BitmapFactory.decodeStream(is));
        container.addView(photoView);
        return photoView;
      }
    } catch (IOException e) {
      Log.e("TAG", "IOException", e);
    }
    return null;
  }

  @Override
  public int getCount() {
    return mCount;
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
