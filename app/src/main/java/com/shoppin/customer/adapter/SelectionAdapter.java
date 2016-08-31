package com.shoppin.customer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.shoppin.customer.R;

import java.util.ArrayList;

public class SelectionAdapter<T> extends BaseAdapter {

    private static final String TAG = SelectionAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<T> filterArrayList;
    private IBindAdapterValues<T> bindAdapterValues;

    public SelectionAdapter(Context context, ArrayList<T> filterArrayList) {
        this.context = context;
        this.filterArrayList = filterArrayList;
    }

    @Override
    public int getCount() {
        return filterArrayList == null ? 0 : filterArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return filterArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.cell_selection, null);
            holder.txtSelectionValue = (CheckBox) convertView.findViewById(R.id.txtSelectionValue);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // holder.txtSelectionValue.setText(filterArrayList.get(position).name);
        // holder.txtSelectionValue.setChecked(filterArrayList.get(position).selected);
        // holder.txtSelectionValue.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View arg0) {
        // // TODO Auto-generated method stub
        // if (filterArrayList.get(position).selected) {
        // filterArrayList.get(position).selected = false;
        // } else {
        // filterArrayList.get(position).selected = true;
        // }
        // notifyDataSetChanged();
        // }
        // });
        bindAdapterValues.bindValues(holder, position);
        return convertView;
    }

    public void setBindAdapterInterface(IBindAdapterValues<T> bindAdapterValues) {
        this.bindAdapterValues = bindAdapterValues;
    }

    public interface IBindAdapterValues<T> {
        // public void bindValues(Holder holder, T object);
        public void bindValues(Holder holder, int position);
    }

    public static class Holder {
        public CheckBox txtSelectionValue;
    }
}
