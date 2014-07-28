package com.u1fukui.lunch.app.fragment;

import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.SLRestaurantManager;
import com.u1fukui.lunch.app.activity.MainActivity;
import com.u1fukui.lunch.app.adapter.RestaurantListAdapter;

public class RestaurantListFragment extends Fragment
    implements MainActivity.OnFilterListener, SwipeRefreshLayout.OnRefreshListener {

  private ListView mListView;
  private SwipeRefreshLayout mSwipeRefresh;

  private MainActivity mActivity;
  private RestaurantListAdapter mListAdapter;
  private LocationClient mLocationClient;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mActivity = (MainActivity) getActivity();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    if (mLocationClient == null) {
      mLocationClient = new LocationClient(getActivity(), mConnectionCallbacks, mOnConnectionFailedListener);
    }
    mLocationClient.connect();

    // View
    View rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
    mListView = (ListView) rootView.findViewById(R.id.list);
    mSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);

    // list
    mListAdapter = new RestaurantListAdapter(getActivity(),
        SLRestaurantManager.getInstance().getFilteredRestaurantArray());
    mListView.setAdapter(mListAdapter);
    mListView.setDivider(new ColorDrawable(getResources().getColor(R.color.theme_color)));
    mListView.setDividerHeight(1);

    // pull to refresh
    mSwipeRefresh.setColorScheme(R.color.theme_color,
        R.color.theme_color, R.color.theme_color, R.color.theme_color);
    mSwipeRefresh.setOnRefreshListener(this);

    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();
    mActivity.setOnFilterListener(this);
  }

  @Override
  public void onFilter() {
    mListAdapter.clear();
    mListAdapter.addAll(SLRestaurantManager.getInstance().getFilteredRestaurantArray());
  }

  @Override
  public void onRefresh() {
    Location location = getCurrentLocation();
    if (location == null) {
      mLocationClient.connect();
      return;
    }

    SLRestaurantManager.getInstance().setCurrentLocaton(location);
    SLRestaurantManager.getInstance().sortInOrderOfDistace();
    mActivity.setFilterDescriotion();
    mListAdapter.clear();
    mListAdapter.addAll(SLRestaurantManager.getInstance().getFilteredRestaurantArray());
    mSwipeRefresh.setRefreshing(false);
  }

  private Location getCurrentLocation() {
    if (mLocationClient.isConnected()) {
      return mLocationClient.getLastLocation();
    } else {
      return null;
    }
  }

  private GooglePlayServicesClient.ConnectionCallbacks mConnectionCallbacks = new GooglePlayServicesClient.ConnectionCallbacks() {
    @Override
    public void onConnected(Bundle bundle) {
      getCurrentLocation();
    }
    @Override
    public void onDisconnected() {
    }
  };

  private GooglePlayServicesClient.OnConnectionFailedListener mOnConnectionFailedListener = new GooglePlayServicesClient.OnConnectionFailedListener() {
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
  };
}
