package com.shoppin.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shoppin.customer.R;
import com.shoppin.customer.activity.NavigationDrawerActivity;
import com.shoppin.customer.fragment.ProductListFragment;
import com.shoppin.customer.model.SubCategory;
import com.shoppin.customer.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ubuntu on 8/8/16.
 */

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {
    private static final String TAG = SubCategoryAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<SubCategory> subCategoryArrayList;
    private int cellWidth = 100;

    public SubCategoryAdapter(Context context, ArrayList<SubCategory> subCategoryArrayList) {
        this.context = context;
        this.subCategoryArrayList = subCategoryArrayList;
        this.cellWidth = Utils.getDeviceWidth(context) / 3;
    }

    @Override
    public int getItemCount() {
        return subCategoryArrayList == null ? 0 : subCategoryArrayList.size();
    }

    @Override
    public SubCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_subcategory_home, parent, false);
//        Log.d(TAG, "cellWidth = " + cellWidth);
        itemView.setLayoutParams(new GridView.LayoutParams(cellWidth, cellWidth));
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SubCategoryAdapter.MyViewHolder holder, final int position) {
        holder.txtSubCategory.setText(subCategoryArrayList.get(position).subcategoryName);
        Glide.with(context)
                .load(subCategoryArrayList.get(position).subcategoryImage)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.imgSubcategory);
        holder.imgSubcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationDrawerActivity navigationDrawerActivity = (NavigationDrawerActivity) context;
                if (navigationDrawerActivity != null) {
                    navigationDrawerActivity.switchContent(ProductListFragment.newInstance(position, subCategoryArrayList), false);
                }
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtSubcategory)
        TextView txtSubCategory;

        @BindView(R.id.imgSubcategory)
        ImageView imgSubcategory;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
