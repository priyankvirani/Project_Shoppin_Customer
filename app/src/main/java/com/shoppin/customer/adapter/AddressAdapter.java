package com.shoppin.customer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoppin.customer.R;
import com.shoppin.customer.activity.AddressEditActivity;
import com.shoppin.customer.fragment.MyAccountFragment;
import com.shoppin.customer.model.Address;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ubuntu on 8/8/16.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {
    private static final String TAG = AddressAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Address> addressArrayList;
    private MyAccountFragment myAccountFragment;
    private OnItemClickListener itemClickListener;

    public AddressAdapter(Context context, ArrayList<Address> addressArrayList, MyAccountFragment myAccountFragment) {
        this.context = context;
        this.addressArrayList = addressArrayList;
        this.myAccountFragment = myAccountFragment;
    }

    @Override
    public int getItemCount() {
        return addressArrayList == null ? 0 : addressArrayList.size();
    }

    @Override
    public AddressAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_address, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AddressAdapter.MyViewHolder holder, final int position) {
        holder.txtName.setText(addressArrayList.get(position).name);
        holder.txtPhone.setText(addressArrayList.get(position).phoneNumber);
        holder.txtStreet.setText(addressArrayList.get(position).street);
        holder.txtSuburb.setText(addressArrayList.get(position).suburbName);
        holder.txtPostCode.setText(addressArrayList.get(position).postCode);
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddressEditActivity.class);
                intent.putExtra(AddressEditActivity.KEY_ADDRESS, addressArrayList.get(position));
                if (myAccountFragment instanceof MyAccountFragment) {
                    myAccountFragment.startActivityForResult(intent, AddressEditActivity.REQUEST_CODE_ADDRESS);
                } else {
                    ((Activity) context).startActivityForResult(intent, AddressEditActivity.REQUEST_CODE_ADDRESS);
                }
            }
        });
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
        public void onItemClick(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cellRoot)
        View cellRoot;

        @BindView(R.id.txtCustomerName)
        TextView txtName;

        @BindView(R.id.txtPhone)
        TextView txtPhone;

        @BindView(R.id.txtStreet)
        TextView txtStreet;

        @BindView(R.id.txtSuburb)
        TextView txtSuburb;

        @BindView(R.id.txtPostCode)
        TextView txtPostCode;

        @BindView(R.id.imgEditAddress)
        ImageView imgEdit;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
