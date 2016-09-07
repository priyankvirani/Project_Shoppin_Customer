package com.shoppin.customer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shoppin.customer.R;
import com.shoppin.customer.activity.NavigationDrawerActivity;
import com.shoppin.customer.adapter.StoreListAdapter;
import com.shoppin.customer.model.Store;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ubuntu on 24/8/16.
 */

public class StoreListFragment extends BaseFragment {

    public static final String TAG = StoreListFragment.class.getSimpleName();

    @BindView(R.id.recyclerListNearByStore)
    RecyclerView recyclerListNearByStore;

    ArrayList<Store> storeArrayList;
    StoreListAdapter storeListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_store_list, container, false);

        ButterKnife.bind(this, layoutView);

        storeArrayList = new ArrayList<Store>();
        storeListAdapter = new StoreListAdapter(getActivity(), storeArrayList);
        recyclerListNearByStore.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerListNearByStore.setAdapter(storeListAdapter);

        dummyData();

        return layoutView;
    }

    private void dummyData() {
        Store objStore = new Store("1", "Store 1", "Street no 1.");
        storeArrayList.add(objStore);
        Store objStore1 = new Store("2", "Store 2", "Street no 2.");
        storeArrayList.add(objStore1);
        Store objStore2 = new Store("3", "Store 3", "Street no 3.");
        storeArrayList.add(objStore2);
        Store objStore3 = new Store("4", "Store 4", "Street no 4.");
        storeArrayList.add(objStore3);
        Store objStore4 = new Store("5", "Store 5", "Street no 5.");
        storeArrayList.add(objStore4);

        storeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateFragment() {
        super.updateFragment();
        if (getActivity() != null && getActivity() instanceof NavigationDrawerActivity) {
            ((NavigationDrawerActivity) getActivity()).setToolbarTitle("Near By Store");
        }
    }
}
