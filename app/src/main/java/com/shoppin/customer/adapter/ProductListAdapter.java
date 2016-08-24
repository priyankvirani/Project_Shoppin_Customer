package com.shoppin.customer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shoppin.customer.R;
import com.shoppin.customer.model.Product;
import com.shoppin.customer.utils.Utils;

import java.util.ArrayList;

public class ProductListAdapter extends BaseAdapter {

    private static final String TAG = ProductListAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Product> productArrayList;

    public ProductListAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @Override
    public int getCount() {
        return productArrayList == null ? 0 : productArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return productArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.cell_product, null);
            holder = new ViewHolder();
            holder.lltCell = (LinearLayout) convertView.findViewById(R.id.lltCell);
            holder.txtProductName = (TextView) convertView.findViewById(R.id.txtProductName);
            holder.txtProductUnit = (TextView) convertView.findViewById(R.id.txtProductUnit);
            holder.txtProductPrice = (TextView) convertView.findViewById(R.id.txtProductPrice);
            holder.imgProduct = (ImageView) convertView.findViewById(R.id.imgProduct);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.e(TAG, "product_id = " + productArrayList.get(position).product_id);

        holder.txtProductName.setText(productArrayList.get(position).product_name);

        holder.txtProductUnit.setText(productArrayList.get(position).productVariantArrayList.get(0).variant_name);
        holder.txtProductPrice.setText("$ " + productArrayList.get(position).productVariantArrayList.get(0).variant_price);

        Glide.with(context)
                .load(productArrayList.get(position).productVariantArrayList.get(0).variant_images)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.imgProduct);
        return convertView;

    }

    class ViewHolder {
        public LinearLayout lltCell;
        public TextView txtProductName, txtProductUnit, txtProductPrice;
        public ImageView imgProduct;
    }

    private class OnItemClickListener implements View.OnClickListener {
        private int position;

        OnItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View arg0) {
            Utils.showToastShort(context, "Under Development : " + productArrayList.get(position).product_name);
        }
    }


}
