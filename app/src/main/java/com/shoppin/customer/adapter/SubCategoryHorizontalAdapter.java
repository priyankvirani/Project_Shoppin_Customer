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
import com.shoppin.customer.model.SubCategory;
import com.shoppin.customer.utils.Utils;

import java.util.ArrayList;

/**
 * Created by ubuntu on 8/8/16.
 */

public class SubCategoryHorizontalAdapter extends RecyclerView.Adapter<SubCategoryHorizontalAdapter.MyViewHolder> {
    private static final String TAG = SubCategoryHorizontalAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<SubCategory> subCategoryArrayList;
    private String cat_id;
    private int cellWidth = 100;

    public SubCategoryHorizontalAdapter(Context context, ArrayList<SubCategory> subCategoryArrayList, String cat_id) {
        this.context = context;
        this.subCategoryArrayList = subCategoryArrayList;
        this.cat_id = cat_id;
        this.cellWidth = Utils.getDeviceWidth(context) / 3;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_subcategory_home, parent, false);
//        Log.d(TAG, "cellWidth = " + cellWidth);
        itemView.setLayoutParams(new GridView.LayoutParams(cellWidth, cellWidth));
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtsubCategory.setText(subCategoryArrayList.get(position).subcat_name);
        Glide.with(context)
                .load(subCategoryArrayList.get(position).subcat_image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.imgSubcategory);
        holder.imgSubcategory.setOnClickListener(new OnItemClickListener(subCategoryArrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return subCategoryArrayList == null ? 0 : subCategoryArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtsubCategory;
        ImageView imgSubcategory;

        MyViewHolder(View vi) {
            super(vi);
            txtsubCategory = (TextView) vi
                    .findViewById(R.id.txtSubcategory);
            imgSubcategory = (ImageView) vi.findViewById(R.id.imgSubcategory);
        }
    }

    private class OnItemClickListener implements View.OnClickListener {
        private SubCategory subCategory;

        OnItemClickListener(SubCategory subCategory) {
            this.subCategory = subCategory;
        }

        @Override
        public void onClick(View arg0) {
            Utils.showToastShort(context, "Under Development : " + subCategory.subcat_name);
        }
    }
}
