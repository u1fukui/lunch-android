package com.u1fukui.lunch.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.u1fukui.lunch.app.SLApplication;
import com.u1fukui.lunch.app.activity.RestaurantDetailActivity;
import com.u1fukui.lunch.app.adapter.RestaurantListAdapter;
import com.u1fukui.lunch.app.model.SLRestaurant;

public class RestaurantListFragment extends ListFragment implements AdapterView.OnItemClickListener {

  private static final String TAG = RestaurantListFragment.class.getSimpleName();

  private RestaurantListAdapter mListAdapter;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mListAdapter = new RestaurantListAdapter(getActivity(),
        SLApplication.getInstance().getRestaurantList());
    getListView().setAdapter(mListAdapter);
    getListView().setOnItemClickListener(this);
    setListShown(true);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    SLRestaurant restaurant = mListAdapter.getItem(position);

    Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
    intent.putExtra(SLRestaurant.EXTRA_RESTAURANT, restaurant);
    startActivity(intent);
  }
}
