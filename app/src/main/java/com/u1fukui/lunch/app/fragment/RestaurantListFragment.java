package com.u1fukui.lunch.app.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.lunch.app.SLApplication;
import com.u1fukui.lunch.app.adapter.RestaurantListAdapter;

public class RestaurantListFragment extends ListFragment {

  private static final String TAG = RestaurantListFragment.class.getSimpleName();

  private RestaurantListAdapter mListAdapter;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mListAdapter = new RestaurantListAdapter(getActivity(),
        SLApplication.getInstance().getRestaurantList());
    getListView().setAdapter(mListAdapter);
    setListShown(true);
  }
}
