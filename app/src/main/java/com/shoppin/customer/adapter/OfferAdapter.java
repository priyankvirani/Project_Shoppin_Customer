package com.shoppin.customer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shoppin.customer.R;
import com.shoppin.customer.model.Offer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ubuntu on 24/8/16.
 */

public class OfferAdapter extends BaseAdapter {

    private static final String TAG = OfferAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Offer> offerArrayList;

    public OfferAdapter(Context context, ArrayList<Offer> offerArrayList) {
        this.context = context;
        this.offerArrayList = offerArrayList;
    }

    @Override
    public int getCount() {
        return offerArrayList == null ? 0 : offerArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return offerArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.cell_offer, null);
            holder = new ViewHolder();
            holder.txtOffers = (TextView) convertView.findViewById(R.id.txtOffers);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtOffers.setText(offerArrayList.get(position).offer_detail);
        return convertView;
    }

    class ViewHolder {
        public TextView txtOffers;
    }
}
