package com.u1fukui.lunch.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.SLRestaurantManager;
import com.u1fukui.lunch.app.model.SLRestaurant;

import java.util.List;

public class RestaurantListMapFragment extends Fragment {

  private GoogleMap mMap;
  private SupportMapFragment mMapFragment;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_restaurant_list_map, container, false);

    mMapFragment = (SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
    mMap = mMapFragment.getMap();
    mMap.setMyLocationEnabled(true);

    CameraUpdate camera =
        CameraUpdateFactory.newLatLngZoom(
            new LatLng(35.658517, 139.701334), 16);
    mMap.moveCamera(camera);

    setRestaurantList();

    return rootView;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (mMap != null) {
      getActivity().getSupportFragmentManager().beginTransaction()
          .remove(mMapFragment).commit();
      mMap = null;
    }
  }

  private void setRestaurantList() {
    List<SLRestaurant> restaurantList = SLRestaurantManager.getInstance().getFilteredRestaurantArray();
    for (SLRestaurant restaurant : restaurantList) {
      MarkerOptions marker = new MarkerOptions();
      marker.title(restaurant.name);
      marker.position(new LatLng(restaurant.lat, restaurant.lng));
      mMap.addMarker(marker);
    }
  }

}
