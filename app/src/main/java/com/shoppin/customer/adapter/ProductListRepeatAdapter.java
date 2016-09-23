package com.shoppin.customer.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shoppin.customer.R;
import com.shoppin.customer.model.Product;
import com.shoppin.customer.network.IWebService;
import com.shoppin.customer.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListRepeatAdapter extends RecyclerView.Adapter<ProductListRepeatAdapter.MyViewHolder> {

    private static final String TAG = ProductListRepeatAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Product> productArrayList;
    private OnItemClickListener itemClickListener;

    public ProductListRepeatAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @Override
    public int getItemCount() {
        return productArrayList == null ? 0 : productArrayList.size();
    }

    @Override
    public ProductListRepeatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_product_repeat_order, parent, false);
        return new ProductListRepeatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductListRepeatAdapter.MyViewHolder holder, final int position) {
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

        if (productArrayList.get(position).isRepeat) {
            holder.imgSelected.setImageDrawable(Utils.getDrawable(context, R.drawable.plus_round_green));
        } else {
            holder.imgSelected.setImageDrawable(Utils.getDrawable(context, R.drawable.plus_round_black));
        }

        holder.imgSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Utils.showToastShort(context, "Under Development : " + productArrayList.get(position).productName);
//                NavigationDrawerActivity navigationDrawerActivity = (NavigationDrawerActivity) context;
//                if (navigationDrawerActivity != null) {
//                    // Add to cart
//                    if (productArrayList.get(position).isRepeat) {
//                        DBAdapter.insertUpdateDeleteCart(context, productArrayList.get(position), true);
//                    }
//                    // Remove from cart
//                    else {
//                        Product tmpProduct = productArrayList.get(position);
//                        tmpProduct.productQuantity = 0;
//                        DBAdapter.insertUpdateDeleteCart(context, tmpProduct, false);
//                    }
//                    navigationDrawerActivity.updateCartCount();
//                }

                if (productArrayList.get(position).isRepeat) {
                    productArrayList.get(position).isRepeat = false;
                } else {
                    productArrayList.get(position).isRepeat = true;
                }
                notifyDataSetChanged();
            }
        });

        if (IWebService.KEY_RES_PRODUCT_STATUS_AVAILABLE == productArrayList.get(position).availability) {
            holder.txtProductAvailability.setVisibility(View.VISIBLE);
            holder.txtProductAvailability.setText(context.getString(R.string.product_available));
            holder.txtProductAvailability.setTextColor(Utils.getColor(context, R.color.app_theme_1));
        } else if (IWebService.KEY_RES_PRODUCT_STATUS_NOT_AVAILABLE == productArrayList.get(position).availability) {
            holder.txtProductAvailability.setVisibility(View.VISIBLE);
            holder.txtProductAvailability.setText(context.getString(R.string.product_not_available));
            holder.txtProductAvailability.setTextColor(Utils.getColor(context, R.color.red));
        } else {
            holder.txtProductAvailability.setVisibility(View.GONE);
        }


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

        @BindView(R.id.txtProductName)
        TextView txtProductName;

        @BindView(R.id.txtProductPrice)
        TextView txtProductPrice;

        @BindView(R.id.txtProductSalePrice)
        TextView txtProductSalePrice;

        @BindView(R.id.txtProductAvailability)
        TextView txtProductAvailability;

        @BindView(R.id.imgProduct)
        ImageView imgProduct;

        @BindView(R.id.imgSelected)
        ImageView imgSelected;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
