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
import com.shoppin.customer.model.Product;
import com.shoppin.customer.utils.Utils;

import java.util.ArrayList;

import static com.shoppin.customer.R.id.txtProductCartCount;

public class CartProductListAdapter extends BaseAdapter {

    private static final String TAG = CartProductListAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Product> productArrayList;

    public CartProductListAdapter(Context context, ArrayList<Product> productArrayList) {
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
            convertView = View.inflate(context, R.layout.cell_cart_product, null);
            holder = new ViewHolder();
            holder.lltCell = (LinearLayout) convertView.findViewById(R.id.lltCell);
            holder.txtProductName = (TextView) convertView.findViewById(R.id.txtProductName);
            holder.txtProductOptionSelection = (TextView) convertView.findViewById(R.id.txtProductOptionSelection);
            holder.txtProductPrice = (TextView) convertView.findViewById(R.id.txtProductPrice);
            holder.txtProductSalePrice = (TextView) convertView.findViewById(R.id.txtProductSalePrice);
            holder.txtProductCartCount = (TextView) convertView.findViewById(txtProductCartCount);
            holder.imgProduct = (ImageView) convertView.findViewById(R.id.imgProduct);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.e(TAG, "productId = " + productArrayList.get(position).productId);

        holder.txtProductName.setText(productArrayList.get(position).productName);

        if (Utils.isNullOrEmpty(productArrayList.get(position).getSelectedOptions())) {
            holder.txtProductOptionSelection.setVisibility(View.GONE);
        } else {
            holder.txtProductOptionSelection.setVisibility(View.VISIBLE);
            holder.txtProductOptionSelection.setText(productArrayList.get(position).getSelectedOptions());
        }
        holder.txtProductPrice.setText("$ " + String.valueOf(productArrayList.get(position).productPrice));
        holder.txtProductPrice.setPaintFlags(holder.txtProductPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txtProductSalePrice.setText("$ " + String.valueOf(productArrayList.get(position).getPriceAsPerSelection()));
        holder.txtProductCartCount.setText("" + String.valueOf(productArrayList.get(position).productQuantity));

        if (productArrayList.get(position).productImages != null && productArrayList.get(position).productImages.size() > 0) {
            Glide.with(context)
                    .load(productArrayList.get(position).productImages.get(0))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.imgProduct);
        }

        return convertView;

    }

    class ViewHolder {
        public LinearLayout lltCell;
        public TextView txtProductName, txtProductOptionSelection, txtProductPrice, txtProductSalePrice, txtProductCartCount;
        public ImageView imgProduct;
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
