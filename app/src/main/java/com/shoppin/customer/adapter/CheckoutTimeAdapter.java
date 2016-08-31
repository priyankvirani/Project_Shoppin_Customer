package com.shoppin.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shoppin.customer.R;
import com.shoppin.customer.model.CheckoutTime;

import java.util.ArrayList;

/**
 * Created by ubuntu on 8/8/16.
 */

public class CheckoutTimeAdapter extends RecyclerView.Adapter<CheckoutTimeAdapter.MyViewHolder> {

    private ArrayList<CheckoutTime> checkoutTimeArrayList;

    private String TAG = CheckoutTimeAdapter.class.getSimpleName();
    private Context mContext;
    private CheckoutTime checkoutTimeTempValues = null;
    private boolean isDateSelected = true;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTime;

        public MyViewHolder(View vi) {
            super(vi);

            txtTime = (TextView) vi
                    .findViewById(R.id.txttime);


        }
    }


    public CheckoutTimeAdapter(Context context, ArrayList<CheckoutTime> dataListModelSubCategories, boolean isdateselected) {
        mContext = context;
        checkoutTimeArrayList = dataListModelSubCategories;
        isDateSelected = isdateselected;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_checkout_time, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        checkoutTimeTempValues = null;
        checkoutTimeTempValues = checkoutTimeArrayList.get(position);

        holder.txtTime.setText(checkoutTimeTempValues.getTime());

        Log.e(TAG, "isSelected : -" + checkoutTimeTempValues.isSelected() + ",Positoion : -" + position);
        if (!checkoutTimeTempValues.isSelected()) {
            holder.txtTime.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            if (isDateSelected) {
                isDateSelected = false;
                holder.txtTime.setBackgroundColor(mContext.getResources().getColor(R.color.app_theme_1));
            }
        }
        holder.txtTime.setOnClickListener(new OnItemClickListener(checkoutTimeTempValues, position, holder.txtTime));

    }

    @Override
    public int getItemCount() {
        return checkoutTimeArrayList.size();
    }

    private class OnItemClickListener implements View.OnClickListener {
        private CheckoutTime tempValues1;
        private int mposition;
        private TextView txtTime;

        OnItemClickListener(CheckoutTime checkoutTime, int position, TextView txttime) {
            tempValues1 = checkoutTime;
            mposition = position;
            txtTime = txttime;
        }

        @Override
        public void onClick(View arg0) {
            txtTime.setBackgroundColor(mContext.getResources().getColor(R.color.app_theme_1));
            Toast.makeText(

                    mContext,
                    tempValues1.getDate() + "," + tempValues1.getTime(), Toast.LENGTH_LONG)
                    .show();

            for (int i = 0; i < checkoutTimeArrayList.size(); i++) {
                checkoutTimeTempValues = checkoutTimeArrayList.get(i);
                if (i != mposition) {
                    checkoutTimeTempValues.setSelected(false);
                } else {
                    checkoutTimeTempValues.setSelected(true);
                }
            }
            notifyDataSetChanged();

            //Log.e(TAG, " :    " + tempValues1.getItemCellCategoryName() + "," + tempValues1.getSubcat_name());
        }
    }
}
