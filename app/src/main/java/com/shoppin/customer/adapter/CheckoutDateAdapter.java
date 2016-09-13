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
import com.shoppin.customer.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ubuntu on 8/8/16.
 */

public class CheckoutDateAdapter extends RecyclerView.Adapter<CheckoutDateAdapter.MyViewHolder> {
    private static final String TAG = CheckoutDateAdapter.class.getSimpleName();
    public RecyclerView timeRecyclerView;
    private Context context;
    private ArrayList<CheckoutDate> checkoutDateArrayList;
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
    public int getItemCount() {
        return checkoutDateArrayList == null ? 0 : checkoutDateArrayList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_checkout_date_time, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.txtDate.setText(checkoutDateArrayList.get(position).date);
        Log.e(TAG, "isSelected = " + checkoutDateArrayList.get(position).isSelected + ",position =" + position);
        if (checkoutDateArrayList.get(position).isSelected) {
            holder.txtDate.setTextColor(context.getResources().getColor(R.color.white));
            holder.txtDate.setBackground(Utils.getDrawable(context, R.drawable.bg_date_time_selected));
            checkoutTimeAdapter.setCheckoutTimeArrayList(checkoutDateArrayList.get(position).checkoutTimesArrayList);
        } else {
            holder.txtDate.setTextColor(context.getResources().getColor(R.color.text_black));
            holder.txtDate.setBackground(Utils.getDrawable(context, R.drawable.bg_date_time_non_selected));
        }

        holder.txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < checkoutDateArrayList.size(); i++) {
                    if (i != holder.getAdapterPosition()) {
                        checkoutDateArrayList.get(i).isSelected = false;
                    } else {
                        checkoutDateArrayList.get(i).isSelected = true;
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtDateTime)
        TextView txtDate;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
