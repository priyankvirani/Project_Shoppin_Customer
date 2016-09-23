package com.shoppin.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shoppin.customer.R;
import com.shoppin.customer.model.Order;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder> {

    private static final String TAG = MyOrderAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Order> orderArrayList;
    private OnItemClickListener itemClickListener;

    public MyOrderAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @Override
    public int getItemCount() {
        return orderArrayList == null ? 0 : orderArrayList.size();
    }

    @Override
    public MyOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_order, parent, false);
        return new MyOrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyOrderAdapter.MyViewHolder holder, final int position) {
        Log.e(TAG, "productId = " + orderArrayList.get(position).orderDeliveryDate);

        holder.txtOrderDeliveryDate.setText(orderArrayList.get(position).orderDeliveryDate);
        holder.txtOrderDeliveryTime.setText(orderArrayList.get(position).orderDeliveryTime);
        holder.txtOrderNumber.setText(orderArrayList.get(position).orderNumber);
        holder.txtOrderTotal.setText("$ " + orderArrayList.get(position).total);
        holder.txtOrderItemCount.setText(orderArrayList.get(position).itemCount);
        holder.txtOrderStatus.setText(orderArrayList.get(position).statusLabel);

        holder.cellRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(view, position);
                }
            }
        });
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cellRoot)
        View cellRoot;

        @BindView(R.id.txtOrderDeliveryDate)
        TextView txtOrderDeliveryDate;

        @BindView(R.id.txtOrderDeliveryTime)
        TextView txtOrderDeliveryTime;

        @BindView(R.id.txtOrderNumber)
        TextView txtOrderNumber;

        @BindView(R.id.txtOrderTotal)
        TextView txtOrderTotal;

        @BindView(R.id.txtOrderItemCount)
        TextView txtOrderItemCount;

        @BindView(R.id.txtOrderStatus)
        TextView txtOrderStatus;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
