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
import com.shoppin.customer.activity.NavigationDrawerActivity;
import com.shoppin.customer.database.DBAdapter;
import com.shoppin.customer.model.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private static final String TAG = ProductListAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Product> productArrayList;
    private OnItemClickListener itemClickListener;

    public ProductListAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @Override
    public int getItemCount() {
        return productArrayList == null ? 0 : productArrayList.size();
    }

    @Override
    public ProductListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_product, parent, false);
        return new ProductListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductListAdapter.MyViewHolder holder, final int position) {
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

        @BindView(R.id.imgProduct)
        ImageView imgProduct;

        @BindView(R.id.imgAddToCart)
        ImageView imgAddToCart;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
