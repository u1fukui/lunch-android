package com.u1fukui.lunch.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;

import com.u1fukui.lunch.app.SLRestaurantManager;
import com.u1fukui.lunch.app.activity.RestaurantDetailActivity;
import com.u1fukui.lunch.app.adapter.RestaurantListAdapter;
import com.u1fukui.lunch.app.model.SLRestaurant;

public class RestaurantListFragment extends ListFragment
    implements AdapterView.OnItemClickListener, SLRestaurantManager.OnFilterListener {

  private static final String TAG = RestaurantListFragment.class.getSimpleName();

  private RestaurantListAdapter mListAdapter;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mListAdapter = new RestaurantListAdapter(getActivity(),
        SLRestaurantManager.getInstance().getFilteredRestaurantArray());
    setListAdapter(mListAdapter);
    getListView().setOnItemClickListener(this);
    setListShown(true);
  }

  @Override
  public void onResume() {
    super.onResume();
    SLRestaurantManager.getInstance().setOnFilterListener(this);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    SLRestaurant restaurant = mListAdapter.getItem(position);

    Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
    intent.putExtra(SLRestaurant.EXTRA_RESTAURANT, restaurant);
    startActivity(intent);
  }

  @Override
  public void onFilter() {
    mListAdapter.clear();
    mListAdapter.addAll(SLRestaurantManager.getInstance().getFilteredRestaurantArray());
  }
}
