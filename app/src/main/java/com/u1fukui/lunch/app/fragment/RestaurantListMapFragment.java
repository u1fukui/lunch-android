package com.u1fukui.lunch.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.u1fukui.lunch.app.R;

/**
 * Created by u1 on 2014/05/25.
 */
public class RestaurantListMapFragment extends Fragment {

  private GoogleMap map;
  private SupportMapFragment mMapFragment;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_restaurant_list_map, container, false);

    mMapFragment = (SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
    map = mMapFragment.getMap();

    return rootView;
  }

  @Override
  public void onDestroyView() {
    //TODO: Auto-generated method stub
    super.onDestroyView();
    if (map != null) {
      getActivity().getSupportFragmentManager().beginTransaction()
          .remove(mMapFragment).commit();
      map = null;
    }
  }

}
