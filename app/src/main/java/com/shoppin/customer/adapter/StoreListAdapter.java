package com.shoppin.customer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shoppin.customer.R;
import com.shoppin.customer.model.Store;

import java.util.ArrayList;

/**
 * Created by ubuntu on 24/8/16.
 */

public class StoreListAdapter extends BaseAdapter {
    private static final String TAG = StoreListAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Store> storeArrayList;

    public StoreListAdapter(Context context, ArrayList<Store> storeArrayList) {
        this.context = context;
        this.storeArrayList = storeArrayList;
    }

    @Override
    public int getCount() {
        return storeArrayList == null ? 0 : storeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return storeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.cell_store_list, null);
            holder = new ViewHolder();

            holder.txtStoreName = (TextView) convertView.findViewById(R.id.txtStoreName);
            holder.txtStoreAddress = (TextView) convertView.findViewById(R.id.txtStoreAddress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtStoreName.setText(storeArrayList.get(position).store_name);
        holder.txtStoreAddress.setText(storeArrayList.get(position).store_address);
        return convertView;
    }

    class ViewHolder {
        public TextView txtStoreName, txtStoreAddress;
    }
}
