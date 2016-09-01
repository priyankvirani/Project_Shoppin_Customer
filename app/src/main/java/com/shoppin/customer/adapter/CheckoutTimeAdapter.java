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

import java.util.ArrayList;

/**
 * Created by ubuntu on 8/8/16.
 */

public class CheckoutTimeAdapter extends RecyclerView.Adapter<CheckoutTimeAdapter.MyViewHolder> {
    private static final String TAG = CheckoutTimeAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<CheckoutTime> checkoutTimeArrayList;
//    private boolean isDateSelected1 = true;

//    public CheckoutTimeAdapter(Context context, ArrayList<CheckoutTime> checkoutTimeArrayList, boolean isDateSelected) {
//        this.context = context;
//        this.checkoutTimeArrayList = checkoutTimeArrayList;
//        this.isDateSelected = isDateSelected;
//    }

    public CheckoutTimeAdapter(Context context, ArrayList<CheckoutTime> checkoutTimeArrayList) {
        this.context = context;
        this.checkoutTimeArrayList = checkoutTimeArrayList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_checkout_time, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.txtTime.setText(checkoutTimeArrayList.get(position).getTime());

        Log.e(TAG, "isSelected = " + checkoutTimeArrayList.get(position).isSelected() + ",position =" + position);
        if (checkoutTimeArrayList.get(position).isSelected()) {
            holder.txtTime.setBackgroundColor(context.getResources().getColor(R.color.app_theme_1));
        } else {
            holder.txtTime.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        holder.txtTime.setOnClickListener(new OnItemClickListener(checkoutTimeArrayList.get(position), position, holder.txtTime));
    }

    @Override
    public int getItemCount() {
        return checkoutTimeArrayList == null ? 0 : checkoutTimeArrayList.size();
    }

    protected void setCheckoutTimeArrayList(ArrayList<CheckoutTime> tmpCheckoutTimeArrayList) {
//        this.checkoutTimeArrayList = null;
        checkoutTimeArrayList = tmpCheckoutTimeArrayList;
        for (int i = 0; i < checkoutTimeArrayList.size(); i++) {
            checkoutTimeArrayList.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTime;

        public MyViewHolder(View vi) {
            super(vi);
            txtTime = (TextView) vi.findViewById(R.id.txtTime);
        }
    }

    private class OnItemClickListener implements View.OnClickListener {
        private CheckoutTime checkoutTime;
        private int position;
        private TextView txtTime;

        OnItemClickListener(CheckoutTime checkoutTime, int position, TextView txtTime) {
            this.checkoutTime = checkoutTime;
            this.position = position;
            this.txtTime = txtTime;
        }

        @Override
        public void onClick(View arg0) {
            txtTime.setBackgroundColor(context.getResources().getColor(R.color.app_theme_1));
            for (int i = 0; i < checkoutTimeArrayList.size(); i++) {
                if (i != position) {
                    checkoutTimeArrayList.get(i).setSelected(false);
                } else {
                    checkoutTimeArrayList.get(i).setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
    }
}
