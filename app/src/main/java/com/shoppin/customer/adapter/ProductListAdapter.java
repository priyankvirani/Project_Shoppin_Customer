package com.shoppin.customer.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shoppin.customer.R;
import com.shoppin.customer.activity.NavigationDrawerActivity;
import com.shoppin.customer.database.DBAdapter;
import com.shoppin.customer.model.Product;
import com.shoppin.customer.utils.Utils;

import java.util.ArrayList;

import static com.shoppin.customer.R.id.imgAddToCart;

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
            holder.txtProductPrice = (TextView) convertView.findViewById(R.id.txtProductPrice);
            holder.txtProductSalePrice = (TextView) convertView.findViewById(R.id.txtProductSalePrice);
            holder.imgProduct = (ImageView) convertView.findViewById(R.id.imgProduct);
            holder.imgAddToCart = (ImageView) convertView.findViewById(imgAddToCart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.e(TAG, "productId = " + productArrayList.get(position).productId);

        holder.txtProductName.setText(productArrayList.get(position).productName);

        holder.txtProductPrice.setText("$ " + String.valueOf(productArrayList.get(position).productPrice));
        holder.txtProductPrice.setPaintFlags(holder.txtProductPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txtProductSalePrice.setText("$ " + String.valueOf(productArrayList.get(position).productSalePrice));

        if (productArrayList.get(position).productImages != null && productArrayList.get(position).productImages.size() > 0) {
            Glide.with(context)
                    .load(productArrayList.get(position).productImages.get(0))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.imgProduct);
        }
        if (productArrayList.get(position).productHasOption) {
            holder.imgAddToCart.setVisibility(View.INVISIBLE);
        } else {
            holder.imgAddToCart.setVisibility(View.VISIBLE);
            holder.imgAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Utils.showToastShort(context, "Under Development : " + productArrayList.get(position).productName);
                    NavigationDrawerActivity navigationDrawerActivity = (NavigationDrawerActivity) context;
                    if (navigationDrawerActivity != null) {
                        DBAdapter.insertUpdateDeleteCart(context, productArrayList.get(position), true);
                        navigationDrawerActivity.updateCartCount();
                    }
                }
            });
        }
        return convertView;

    }

    class ViewHolder {
        public LinearLayout lltCell;
        public TextView txtProductName, txtProductPrice, txtProductSalePrice;
        public ImageView imgProduct, imgAddToCart;
    }

    private class OnItemClickListener implements View.OnClickListener {
        private int position;

        OnItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View arg0) {
            Utils.showToastShort(context, "Under Development : " + productArrayList.get(position).productName);
        }
    }


}
