package com.shoppin.customer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shoppin.customer.R;
import com.shoppin.customer.model.Address;

import java.util.ArrayList;

/**
 * Created by ubuntu on 25/8/16.
 */

public class AddressAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<Address> addressArrayList;

    public AddressAdapter(Context context, ArrayList<Address> addressArrayList) {
        this.context = context;
        this.addressArrayList = addressArrayList;
    }

    @Override
    public int getCount() {
        return addressArrayList == null ? 0 : addressArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return addressArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.cell_address, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.txtCustomerName);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
            holder.txtStreet = (TextView) convertView.findViewById(R.id.txtStreet);
            holder.txtSuburb = (TextView) convertView.findViewById(R.id.txtSuburb);
//            holder.txtTown = (TextView) convertView.findViewById(R.id.txtTown);
            holder.txtPostCode = (TextView) convertView.findViewById(R.id.txtPostCode);
//            holder.txtEdit = (TextView) convertView.findViewById(R.id.txtEdit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(addressArrayList.get(position).name);
        holder.txtPhone.setText(addressArrayList.get(position).phoneNumber);
        holder.txtStreet.setText(addressArrayList.get(position).street);
        holder.txtSuburb.setText(addressArrayList.get(position).suburbId);
//        holder.txtTown.setText(addressArrayList.get(position).town);
        holder.txtPostCode.setText(addressArrayList.get(position).postCode);

        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        return convertView;
    }

    class ViewHolder {
        private TextView txtName, txtEdit, txtPhone, txtStreet, txtSuburb, txtTown, txtPostCode;
    }


}

