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
import com.shoppin.customer.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartProductListAdapter extends RecyclerView.Adapter<CartProductListAdapter.MyViewHolder> {

    private static final String TAG = CartProductListAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Product> productArrayList;
    private OnItemClickListener onItemClickListener;
    private OnCartChangeListener onCartChangeListener;

    public CartProductListAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }


    @Override
    public int getItemCount() {
        return productArrayList == null ? 0 : productArrayList.size();
    }

    @Override
    public CartProductListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_cart_product, parent, false);
        return new CartProductListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartProductListAdapter.MyViewHolder holder, final int position) {
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

        holder.imgDecrementProductCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationDrawerActivity navigationDrawerActivity = (NavigationDrawerActivity) context;
                if (navigationDrawerActivity != null) {
                    DBAdapter.insertUpdateDeleteCart(context, productArrayList.get(position), false);
                    notifyDataSetChanged();
                    navigationDrawerActivity.updateCartCount();
//                    holder.txtProductCartCount.setText("" + productArrayList.get(position).productQuantity);
                    Log.d(TAG, "productArrayList.get(position).productQuantity = " + productArrayList.get(position).productQuantity);
                    if (onCartChangeListener != null) {
                        if (productArrayList.get(position).productQuantity <= 0) {
                            onCartChangeListener.onCartChange(view, position, true);
                        } else {
                            onCartChangeListener.onCartChange(view, position, false);
                        }
                    }
                }
            }
        });


        holder.imgIncrementProductCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationDrawerActivity navigationDrawerActivity = (NavigationDrawerActivity) context;
                if (navigationDrawerActivity != null) {
                    DBAdapter.insertUpdateDeleteCart(context, productArrayList.get(position), true);
                    notifyDataSetChanged();
                    navigationDrawerActivity.updateCartCount();
//                    holder.txtProductCartCount.setText("" + productArrayList.get(position).productQuantity);
                    if (onCartChangeListener != null) {
                        onCartChangeListener.onCartChange(view, position, false);
                    }
                }
            }
        });

        holder.cellRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position);
                }
            }
        });
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnCartChangeListener(final OnCartChangeListener onCartChangeListener) {
        this.onCartChangeListener = onCartChangeListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public interface OnCartChangeListener {
        public void onCartChange(View view, int position, boolean isProductRemove);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cellRoot)
        View cellRoot;

        @BindView(R.id.txtProductName)
        TextView txtProductName;

        @BindView(R.id.txtProductOptionSelection)
        TextView txtProductOptionSelection;

        @BindView(R.id.txtProductPrice)
        TextView txtProductPrice;

        @BindView(R.id.txtProductSalePrice)
        TextView txtProductSalePrice;

        @BindView(R.id.txtProductCartCount)
        TextView txtProductCartCount;

        @BindView(R.id.imgProduct)
        ImageView imgProduct;

        @BindView(R.id.imgDecrementProductCart)
        ImageView imgDecrementProductCart;

        @BindView(R.id.imgIncrementProductCart)
        ImageView imgIncrementProductCart;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
