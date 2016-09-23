package com.shoppin.customer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shoppin.customer.R;
import com.shoppin.customer.activity.NavigationDrawerActivity;
import com.shoppin.customer.adapter.StoreListAdapter;
import com.shoppin.customer.database.DBAdapter;
import com.shoppin.customer.database.IDatabase;
import com.shoppin.customer.model.Store;
import com.shoppin.customer.network.DataRequest;
import com.shoppin.customer.network.IWebService;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ubuntu on 24/8/16.
 */

public class StoreListFragment extends BaseFragment {

    public static final String TAG = StoreListFragment.class.getSimpleName();

    @BindView(R.id.rlvGlobalProgressbar)
    RelativeLayout rlvGlobalProgressbar;

    @BindView(R.id.recyclerListNearByStore)
    RecyclerView recyclerListNearByStore;

    ArrayList<Store> storeArrayList;
    StoreListAdapter storeListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_store_list, container, false);

        ButterKnife.bind(this, layoutView);

        storeArrayList = new ArrayList<>();
        storeListAdapter = new StoreListAdapter(getActivity(), storeArrayList);
        recyclerListNearByStore.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerListNearByStore.setAdapter(storeListAdapter);

        getNearByStore();

        return layoutView;
    }


    @Override
    public void updateFragment() {
        super.updateFragment();
        if (getActivity() != null && getActivity() instanceof NavigationDrawerActivity) {
            ((NavigationDrawerActivity) getActivity()).setToolbarTitle(getActivity().getResources().getString(R.string.fragment_store_list));
        }
    }

    private void getNearByStore() {
        JSONObject myOrderParam = new JSONObject();
        try {
            myOrderParam.put(IWebService.KEY_REQ_SUBURB_ID,
                    DBAdapter.getMapKeyValueString(getActivity(), IDatabase.IMap.SUBURB_ID));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataRequest getSuburbsDataRequest = new DataRequest(getActivity());
        getSuburbsDataRequest.execute(IWebService.NEAR_BY_STORES, myOrderParam.toString(), new DataRequest.CallBack() {
            public void onPreExecute() {
                rlvGlobalProgressbar.setVisibility(View.VISIBLE);
            }

            public void onPostExecute(String response) {
                try {
                    rlvGlobalProgressbar.setVisibility(View.GONE);
                    if (!DataRequest.hasError(getActivity(), response, true)) {
                        Gson gson = new Gson();
                        JSONObject dataJObject = DataRequest.getJObjWebdata(response);
                        ArrayList<Store> tmpStoreArrayList = gson.fromJson(
                                dataJObject.getJSONArray(IWebService.KEY_RES_STORE_LIST).toString(),
                                new TypeToken<ArrayList<Store>>() {
                                }.getType());
                        if (tmpStoreArrayList != null) {
                            Log.e(TAG, "tmpStoreArrayList.size = " + tmpStoreArrayList.size());
                            storeArrayList.clear();
                            storeArrayList.addAll(tmpStoreArrayList);
                            storeListAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
