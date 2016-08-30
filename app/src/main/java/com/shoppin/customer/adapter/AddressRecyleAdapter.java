package com.shoppin.customer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoppin.customer.R;
import com.shoppin.customer.activity.AddressEditActivity;
import com.shoppin.customer.activity.NavigationDrawerActivity;
import com.shoppin.customer.fragment.MyAccountFragment;
import com.shoppin.customer.model.Address;
import com.shoppin.customer.utils.IConstants;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ubuntu on 8/8/16.
 */

public class AddressRecyleAdapter extends RecyclerView.Adapter<AddressRecyleAdapter.MyViewHolder> {
    private static final String TAG = AddressRecyleAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Address> addressArrayList;
    private MyAccountFragment myAccountFragment;

    public AddressRecyleAdapter(Context context, ArrayList<Address> addressArrayList, MyAccountFragment myAccountFragment) {
        Log.d(TAG, "in constructor =" + addressArrayList.size());
        this.context = context;
        this.addressArrayList = addressArrayList;
        this.myAccountFragment = myAccountFragment;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_address, parent, false);
//        Log.d(TAG, "cellWidth = " + cellWidth);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Log.d(TAG, "in BinderView = " + addressArrayList.size());
        holder.txtName.setText(addressArrayList.get(position).name);
        holder.txtPhone.setText(addressArrayList.get(position).phoneNumber);
        holder.txtStreet.setText(addressArrayList.get(position).street);
        holder.txtSuburb.setText(addressArrayList.get(position).suburbName);
//        holder.txtTown.setText(addressArrayList.get(position).town);
        holder.txtPostCode.setText(addressArrayList.get(position).postCode);

        holder.imgEdit.setOnClickListener(new OnItemClickListener(position));
    }

    @Override
    public int getItemCount() {
        return addressArrayList == null ? 0 : addressArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        //        @Nullable
        @BindView(R.id.txtName)
        TextView txtName;
        //        @Nullable
        @BindView(R.id.txtPhone)
        TextView txtPhone;
        //        @Nullable
        @BindView(R.id.txtStreet)
        TextView txtStreet;
        //        @Nullable
        @BindView(R.id.txtSuburb)
        TextView txtSuburb;
        //        @Nullable
        @BindView(R.id.txtPostCode)
        TextView txtPostCode;

        @BindView(R.id.imgEdit)
        ImageView imgEdit;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
//            txtName = (TextView) view.findViewById(R.id.txtName);

        }
    }

    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {

            Intent intent = new Intent(context, AddressEditActivity.class);
            intent.putExtra(IConstants.IRequestCode.KEY_ADDRESS_LIST, (Serializable) addressArrayList);
            intent.putExtra(IConstants.IRequestCode.KEY_ADDRESS_POSITION, mPosition);
            myAccountFragment.startActivityForResult(intent, IConstants.IRequestCode.REQUEST_CODE_ADDRESS);

        }
    }


}
