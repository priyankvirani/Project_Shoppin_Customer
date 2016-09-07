package com.shoppin.customer.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shoppin.customer.R;
import com.shoppin.customer.activity.NavigationDrawerActivity;
import com.shoppin.customer.fragment.CategoryFragment;
import com.shoppin.customer.model.Category;
import com.shoppin.customer.model.SubCategory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.MyViewHolder> {

    private static final String TAG = CategoryHomeAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Category> categoryArrayList;
    private ArrayList<SubCategory> subCategoryArrayList;
    private SubCategoryAdapter subCategoryHorizontalAdapter;

    public CategoryHomeAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    @Override
    public int getItemCount() {
        return categoryArrayList == null ? 0 : categoryArrayList.size();
    }

    @Override
    public CategoryHomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_category_home_fragment, parent, false);
        return new CategoryHomeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoryHomeAdapter.MyViewHolder holder, final int position) {
        Log.e(TAG, "categoryId = " + categoryArrayList.get(position).categoryId);

        holder.txtCategory.setText(categoryArrayList.get(position).categoryName);

        subCategoryArrayList = categoryArrayList.get(position).subCategoryArrayList;
        subCategoryHorizontalAdapter = new SubCategoryAdapter(context, subCategoryArrayList);
        holder.recyclerHorizontalListSubCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerHorizontalListSubCategory.setAdapter(subCategoryHorizontalAdapter);

        if (categoryArrayList.get(position).isCategoryExpand()) {
            holder.recyclerHorizontalListSubCategory.setVisibility(View.VISIBLE);

        } else {
            holder.recyclerHorizontalListSubCategory.setVisibility(View.GONE);
        }

        holder.rlvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Category : " + categoryArrayList.get(position).categoryName);
                if (categoryArrayList.get(position).isCategoryExpand()) {
                    categoryArrayList.get(position).setCategoryExpand(false);
                } else {
                    categoryArrayList.get(position).setCategoryExpand(true);
                }
                notifyDataSetChanged();
            }
        });

        holder.imgViewAllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Size : " + categoryArrayList.size());
                if (context != null && context instanceof NavigationDrawerActivity) {
                    NavigationDrawerActivity navigationDrawerActivity = (NavigationDrawerActivity) context;
                    navigationDrawerActivity.switchContent(CategoryFragment.newInstance(position, categoryArrayList), false);
                }
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rlvCategory)
        RelativeLayout rlvCategory;

        @BindView(R.id.txtCategory)
        TextView txtCategory;

        @BindView(R.id.recyclerHorizontalListSubCategory)
        RecyclerView recyclerHorizontalListSubCategory;

        @BindView(R.id.imgViewAllCategory)
        ImageView imgViewAllCategory;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
