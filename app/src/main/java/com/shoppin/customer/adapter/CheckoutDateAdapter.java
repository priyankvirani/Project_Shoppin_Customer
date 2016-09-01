package com.shoppin.customer.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shoppin.customer.R;
import com.shoppin.customer.model.CheckoutDate;

import java.util.ArrayList;

/**
 * Created by ubuntu on 8/8/16.
 */

public class CheckoutDateAdapter extends RecyclerView.Adapter<CheckoutDateAdapter.MyViewHolder> {
    private static final String TAG = CheckoutDateAdapter.class.getSimpleName();

    public RecyclerView timeRecyclerView;
    private ArrayList<CheckoutDate> checkoutDateArrayList;
    private Context context;
    private CheckoutTimeAdapter checkoutTimeAdapter;

    public CheckoutDateAdapter(Context context, ArrayList<CheckoutDate> checkoutDateArrayList
            , RecyclerView timeRecyclerView) {
        this.context = context;
        this.checkoutDateArrayList = checkoutDateArrayList;
        this.timeRecyclerView = timeRecyclerView;

        checkoutTimeAdapter = new CheckoutTimeAdapter(context, null);
        LinearLayoutManager horizontalLayoutManagaerdate
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        this.timeRecyclerView.setLayoutManager(horizontalLayoutManagaerdate);
        this.timeRecyclerView.setAdapter(checkoutTimeAdapter);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_checkout_date, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.txtDate.setText(checkoutDateArrayList.get(position).getDate());

        //Log.e(TAG, "isSelected : = " + checkoutDateTempValues.isSelected());
        Log.e(TAG, "isSelected = " + checkoutDateArrayList.get(position).isSelected() + ",position =" + position);
        if (checkoutDateArrayList.get(position).isSelected()) {
            holder.txtDate.setBackgroundColor(context.getResources().getColor(R.color.app_theme_1));
            checkoutTimeAdapter.setCheckoutTimeArrayList(checkoutDateArrayList.get(position).getCheckoutTimesArrayList());
        } else {
            holder.txtDate.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        holder.txtDate.setOnClickListener(new OnItemClickListener(checkoutDateArrayList.get(position), position, holder.txtDate));
    }

    @Override
    public int getItemCount() {
        return checkoutDateArrayList == null ? 0 : checkoutDateArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtDate;

        public MyViewHolder(View vi) {
            super(vi);
            txtDate = (TextView) vi.findViewById(R.id.txtDate);
        }
    }

    private class OnItemClickListener implements View.OnClickListener {
        private CheckoutDate checkoutDate;
        private int position;
        private TextView txtDate;

        OnItemClickListener(CheckoutDate checkoutDate, int position, TextView txtDate) {
            this.checkoutDate = checkoutDate;
            this.position = position;
            this.txtDate = txtDate;
        }

        @Override
        public void onClick(View arg0) {
//            txtDate.setBackgroundColor(context.getResources().getColor(R.color.app_theme_1));
//            checkoutTimeAdapter = new CheckoutTimeAdapter(context, checkoutDateArrayList.get(position).getCheckoutTimesArrayList(), false);
//            LinearLayoutManager horizontalLayoutManagaerdate
//                    = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//            timeRecyclerView.setLayoutManager(horizontalLayoutManagaerdate);
//            timeRecyclerView.setAdapter(checkoutTimeAdapter);
            for (int i = 0; i < checkoutDateArrayList.size(); i++) {
                if (i != position) {
                    checkoutDateArrayList.get(i).setSelected(false);
                } else {
                    checkoutDateArrayList.get(i).setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
    }
}
