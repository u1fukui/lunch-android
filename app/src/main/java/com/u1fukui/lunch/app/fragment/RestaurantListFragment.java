package com.u1fukui.lunch.app.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.u1fukui.lunch.app.R;
import com.u1fukui.lunch.app.SLRestaurantManager;
import com.u1fukui.lunch.app.adapter.RestaurantListAdapter;

public class RestaurantListFragment extends ListFragment
    implements SLRestaurantManager.OnFilterListener {

  private static final String TAG = RestaurantListFragment.class.getSimpleName();

  private RestaurantListAdapter mListAdapter;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mListAdapter = new RestaurantListAdapter(getActivity(),
        SLRestaurantManager.getInstance().getFilteredRestaurantArray());
    setListAdapter(mListAdapter);
    getListView().setDivider(new ColorDrawable(getResources().getColor(R.color.theme_color)));
    getListView().setDividerHeight(1);
    setListShown(true);
  }

  @Override
  public void onResume() {
    super.onResume();
    SLRestaurantManager.getInstance().setOnFilterListener(this);
  }

  @Override
  public void onFilter() {
    mListAdapter.clear();
    mListAdapter.addAll(SLRestaurantManager.getInstance().getFilteredRestaurantArray());
  }
}
