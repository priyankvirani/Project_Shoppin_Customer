package com.shoppin.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shoppin.customer.R;
import com.shoppin.customer.model.Store;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ubuntu on 24/8/16.
 */

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.MyViewHolder> {
    private static final String TAG = StoreListAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Store> storeArrayList;

    public StoreListAdapter(Context context, ArrayList<Store> storeArrayList) {
        this.context = context;
        this.storeArrayList = storeArrayList;
    }

    @Override
    public int getItemCount() {
        return storeArrayList == null ? 0 : storeArrayList.size();
    }

    @Override
    public StoreListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_store_list, parent, false);
        return new StoreListAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final StoreListAdapter.MyViewHolder holder, final int position) {
        holder.txtStoreName.setText(storeArrayList.get(position).storeName);
        holder.txtStoreAddress.setText(storeArrayList.get(position).storeAddress);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtStoreName)
        TextView txtStoreName;

        @BindView(R.id.txtPreferredStoreAddress)
        TextView txtStoreAddress;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
