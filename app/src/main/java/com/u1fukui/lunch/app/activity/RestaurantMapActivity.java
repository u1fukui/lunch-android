package com.u1fukui.lunch.app.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.model.SLRestaurant;

public class RestaurantMapActivity extends FragmentActivity {

  private SupportMapFragment mMapFragment;
  private SLRestaurant mRestaurant;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_map);
    getActionBar().setTitle("お店の場所");

    SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.restaurant_map);
    GoogleMap map = mapFragment.getMap();
    map.setMyLocationEnabled(true);

    mRestaurant = (SLRestaurant) getIntent().getSerializableExtra(SLRestaurant.EXTRA_RESTAURANT);
    LatLng restaurantPosition = new LatLng(mRestaurant.lat, mRestaurant.lng);
    CameraUpdate camera =
        CameraUpdateFactory.newLatLngZoom(restaurantPosition, 17);
    map.moveCamera(camera);

    MarkerOptions marker = new MarkerOptions();
    marker.title(mRestaurant.name);
    marker.position(restaurantPosition);
    map.addMarker(marker);
  }
}
