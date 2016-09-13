package com.shoppin.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shoppin.customer.R;
import com.shoppin.customer.model.CheckoutTime;
import com.shoppin.customer.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ubuntu on 8/8/16.
 */

public class CheckoutTimeAdapter extends RecyclerView.Adapter<CheckoutTimeAdapter.MyViewHolder> {
    private static final String TAG = CheckoutTimeAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<CheckoutTime> checkoutTimeArrayList;

    public CheckoutTimeAdapter(Context context, ArrayList<CheckoutTime> checkoutTimeArrayList) {
        this.context = context;
        this.checkoutTimeArrayList = checkoutTimeArrayList;
    }

    @Override
    public int getItemCount() {
        return checkoutTimeArrayList == null ? 0 : checkoutTimeArrayList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_checkout_date_time, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.txtTime.setText(checkoutTimeArrayList.get(position).time);
        Log.e(TAG, "isSelected = " + checkoutTimeArrayList.get(position).isSelected + ",position =" + position);
        if (checkoutTimeArrayList.get(position).isSelected) {
            holder.txtTime.setTextColor(context.getResources().getColor(R.color.white));
            holder.txtTime.setBackground(Utils.getDrawable(context, R.drawable.bg_date_time_selected));
        } else {
            holder.txtTime.setTextColor(context.getResources().getColor(R.color.text_black));
            holder.txtTime.setBackground(Utils.getDrawable(context, R.drawable.bg_date_time_non_selected));
        }
//        holder.txtTime.setOnClickListener(new OnItemClickListener(checkoutTimeArrayList.get(position), position, holder.txtTime));
        holder.txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txtTime.setBackgroundColor(context.getResources().getColor(R.color.app_theme_1));
                for (int i = 0; i < checkoutTimeArrayList.size(); i++) {
                    if (i != holder.getAdapterPosition()) {
                        checkoutTimeArrayList.get(i).isSelected = false;
                    } else {
                        checkoutTimeArrayList.get(i).isSelected = true;
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    protected void setCheckoutTimeArrayList(ArrayList<CheckoutTime> tmpCheckoutTimeArrayList) {
        checkoutTimeArrayList = tmpCheckoutTimeArrayList;
        for (int i = 0; i < checkoutTimeArrayList.size(); i++) {
            checkoutTimeArrayList.get(i).isSelected = false;
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDateTime)
        TextView txtTime;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
