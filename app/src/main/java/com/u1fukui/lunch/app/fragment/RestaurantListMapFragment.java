package com.u1fukui.lunch.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.SLRestaurantManager;
import com.u1fukui.lunch.app.adapter.RestaurantPagerAdapter;
import com.u1fukui.lunch.app.model.SLRestaurant;

import java.util.List;

public class RestaurantListMapFragment extends Fragment implements SLRestaurantManager.OnFilterListener {

  private GoogleMap mMap;
  private SupportMapFragment mMapFragment;
  private ViewPager mViewPager;

  private List<SLRestaurant> mRestaurantList;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_restaurant_list_map, container, false);

    mViewPager = (ViewPager) rootView.findViewById(R.id.map_restaurant_pager);
    mMapFragment = (SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.map);

    // 地図
    mMap = mMapFragment.getMap();
    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
      @Override
      public boolean onMarkerClick(Marker marker) {
        int pos = findPositionByName(marker.getTitle());
        if (pos >= 0) {
          mViewPager.setCurrentItem(pos, false);
        }
        return false;
      }
    });

    // データセット
    mRestaurantList = SLRestaurantManager.getInstance().getFilteredRestaurantArray();
    setRestaurantList(mRestaurantList);

    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();
    SLRestaurantManager.getInstance().setOnFilterListener(this);
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

  private void setRestaurantList(List<SLRestaurant> restaurantList) {
    // 地図
    mMap.clear();
    int size = restaurantList.size();
    for (int i = 0; i < size; i++) {
      SLRestaurant r = restaurantList.get(i);
      MarkerOptions marker = new MarkerOptions();
      marker.title(r.name);
      marker.position(new LatLng(r.lat, r.lng));
      Marker m = mMap.addMarker(marker);

      // 初期位置
      if (i == 0) {
        m.showInfoWindow();
        mMap.setMyLocationEnabled(true);
        CameraUpdate camera =
            CameraUpdateFactory.newLatLngZoom(
                marker.getPosition(), 16);
        mMap.moveCamera(camera);
      }
    }

    // 詳細情報
    mViewPager.setAdapter(new RestaurantPagerAdapter(getActivity(), restaurantList));
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

  @Override
  public void onFilter() {
    mRestaurantList = SLRestaurantManager.getInstance().getFilteredRestaurantArray();
    setRestaurantList(mRestaurantList);
  }
}
