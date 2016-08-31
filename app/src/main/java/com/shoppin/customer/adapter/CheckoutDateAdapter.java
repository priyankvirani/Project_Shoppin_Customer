package com.shoppin.customer.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shoppin.customer.R;
import com.shoppin.customer.model.CheckoutDate;

import java.util.ArrayList;

/**
 * Created by ubuntu on 8/8/16.
 */

public class CheckoutDateAdapter extends RecyclerView.Adapter<CheckoutDateAdapter.MyViewHolder> {

    public RecyclerView timeRecyclerView;
    private ArrayList<CheckoutDate> checkoutDateArrayList;
    private String TAG = CheckoutDateAdapter.class.getSimpleName();
    private Context mContext;
    private CheckoutDate checkoutDateTempValues = null;
    private CheckoutTimeAdapter checkoutTimeAdapter;


    public CheckoutDateAdapter(Context context, ArrayList<CheckoutDate> checkoutDatearrayList
            , RecyclerView recyclerView) {
        mContext = context;
        checkoutDateArrayList = checkoutDatearrayList;
        timeRecyclerView = recyclerView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_checkout_date, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        checkoutDateTempValues = null;
        checkoutDateTempValues = checkoutDateArrayList.get(position);

        holder.txtDate.setText(checkoutDateTempValues.getDate());

        //Log.e(TAG, "isSelected : = " + checkoutDateTempValues.isSelected());
        if (!checkoutDateTempValues.isSelected()) {
            holder.txtDate.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.txtDate.setBackgroundColor(mContext.getResources().getColor(R.color.app_theme_1));
        }

        holder.txtDate.setOnClickListener(new OnItemClickListener(checkoutDateTempValues, position, holder.txtDate));


    }

    @Override
    public int getItemCount() {
        return checkoutDateArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtDate;

        public MyViewHolder(View vi) {
            super(vi);
            txtDate = (TextView) vi
                    .findViewById(R.id.txtdate);
        }
    }

    private class OnItemClickListener implements View.OnClickListener {
        private CheckoutDate tempValues1;
        private int mposition;
        private TextView txtDate;

        OnItemClickListener(CheckoutDate checkoutDate, int position, TextView txtdate) {
            tempValues1 = checkoutDate;
            mposition = position;
            txtDate = txtdate;
        }

        @Override
        public void onClick(View arg0) {

            txtDate.setBackgroundColor(mContext.getResources().getColor(R.color.app_theme_1));
            checkoutDateTempValues = checkoutDateArrayList.get(mposition);

            checkoutTimeAdapter = new CheckoutTimeAdapter(mContext, checkoutDateTempValues.getCheckoutTimesArrayList(), false);
            LinearLayoutManager horizontalLayoutManagaerdate
                    = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            timeRecyclerView.setLayoutManager(horizontalLayoutManagaerdate);
            timeRecyclerView.setAdapter(checkoutTimeAdapter);

            for (int i = 0; i < checkoutDateArrayList.size(); i++) {
                checkoutDateTempValues = checkoutDateArrayList.get(i);
                if (i != mposition) {
                    checkoutDateTempValues.setSelected(false);
                } else {
                    checkoutDateTempValues.setSelected(true);
                }

            }
            notifyDataSetChanged();

            Toast.makeText(

                    mContext,
                    "Click : " + tempValues1.getDate(), Toast.LENGTH_LONG)
                    .show();


            //Log.e(TAG, " :    " + tempValues1.getItemCellCategoryName() + "," + tempValues1.getSubcat_name());
        }
    }
}
