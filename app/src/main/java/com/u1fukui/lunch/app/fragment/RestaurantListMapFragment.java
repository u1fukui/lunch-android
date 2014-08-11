package com.u1fukui.lunch.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.SLRestaurantManager;
import com.u1fukui.lunch.app.activity.MainActivity;
import com.u1fukui.lunch.app.adapter.RestaurantPagerAdapter;
import com.u1fukui.lunch.app.model.SLRestaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListMapFragment extends Fragment implements MainActivity.OnFilterListener {

  private GoogleMap mMap;
  private SupportMapFragment mMapFragment;
  private ViewPager mViewPager;
  private ImageButton mSlideLeftButton;
  private ImageButton mSlideRightButton;

  private MainActivity mActivity;
  private RestaurantPagerAdapter mPagerAdapter;
  private List<SLRestaurant> mRestaurantList;
  private List<Marker> mMarkerList;
  private boolean mIsTappedMarker = false;
  private int mCurrentIndex;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mActivity = (MainActivity) getActivity();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_restaurant_list_map, container, false);

    mViewPager = (ViewPager) rootView.findViewById(R.id.map_restaurant_pager);
    mMapFragment = (SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
    mSlideLeftButton = (ImageButton) rootView.findViewById(R.id.map_slide_left_button);
    mSlideRightButton = (ImageButton) rootView.findViewById(R.id.map_slide_right_button);

    // 地図
    mMap = mMapFragment.getMap();
    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
      @Override
      public boolean onMarkerClick(Marker marker) {
        int pos = findPositionByName(marker.getTitle());
        if (pos >= 0) {
          mIsTappedMarker = true;
          mViewPager.setCurrentItem(pos, false);
        }
        return false;
      }
    });

    // スライドボタン
    mSlideLeftButton.setVisibility(View.INVISIBLE);
    mSlideLeftButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int current = mViewPager.getCurrentItem();
        if (current > 0) {
          mViewPager.setCurrentItem(current - 1, true);
        }
      }
    });

    mSlideRightButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final int current = mViewPager.getCurrentItem();
        if (current < mPagerAdapter.getCount() - 1) {
          mViewPager.setCurrentItem(current + 1, true);
        }
      }
    });

    // ページャー
    mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      @Override
      public void onPageSelected(int position) {
        mCurrentIndex = position;

        // マーカータップによる処理を区別する
        if (mIsTappedMarker) {
          mIsTappedMarker = false;
        } else {
          selectMarker(mMarkerList.get(position));
        }
        setSlideButton(position);
      }

      @Override
      public void onPageScrollStateChanged(int state) {
      }
    });

    // データセット
    mRestaurantList = SLRestaurantManager.getInstance().getFilteredRestaurantArray();
    setRestaurantList(mRestaurantList);
    mViewPager.setCurrentItem(mCurrentIndex);

    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();
    mActivity.setOnFilterListener(this);
  }

  @Override
  public void onDestroyView() {
    if (mMap != null && mMapFragment.isResumed()) {
      getActivity().getSupportFragmentManager().beginTransaction()
          .remove(mMapFragment).commit();
      mMap = null;
    }
    super.onDestroyView();
  }

  private int findPositionByName(String name) {
    int size = mRestaurantList.size();
    for (int i = 0; i < size; i++) {
      SLRestaurant r = mRestaurantList.get(i);
      if (r.name.equals(name)) {
        return i;
      }
    }
    return -1;
  }

  private void selectMarker(Marker marker) {
    marker.showInfoWindow();
    mMap.setMyLocationEnabled(true);
    CameraUpdate camera =
        CameraUpdateFactory.newLatLngZoom(
            marker.getPosition(), 15);
    mMap.moveCamera(camera);
  }

  private void setRestaurantList(List<SLRestaurant> restaurantList) {
    // 地図
    mMap.clear();
    mMarkerList = new ArrayList<Marker>(restaurantList.size());
    int size = restaurantList.size();
    for (int i = 0; i < size; i++) {
      SLRestaurant r = restaurantList.get(i);
      MarkerOptions marker = new MarkerOptions();
      marker.title(r.name);
      marker.position(new LatLng(r.lat, r.lng));
      Marker m = mMap.addMarker(marker);
      mMarkerList.add(m);

      // 初期位置
      if (i == 0) {
        selectMarker(m);
      }
    }

    // 詳細情報
    mPagerAdapter = new RestaurantPagerAdapter(getActivity(), restaurantList);
    mViewPager.setAdapter(mPagerAdapter);
    setSlideButton(0);
  }

  private void setSlideButton(int position) {
    mSlideLeftButton.setVisibility(View.VISIBLE);
    mSlideRightButton.setVisibility(View.VISIBLE);
    if (position == 0) {
      mSlideLeftButton.setVisibility(View.INVISIBLE);
    } else if (position == mPagerAdapter.getCount() - 1) {
      mSlideRightButton.setVisibility(View.INVISIBLE);
    }
  }

    
  //=================================================
  // SLRestaurantManager.OnFilterListener
  //=================================================

  @Override
  public void onFilter() {
    mRestaurantList = SLRestaurantManager.getInstance().getFilteredRestaurantArray();
    setRestaurantList(mRestaurantList);
  }
}
