package com.shoppin.customer.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shoppin.customer.R;
import com.shoppin.customer.activity.NavigationDrawerActivity;
import com.shoppin.customer.fragment.CategoryFragment;
import com.shoppin.customer.model.Category;
import com.shoppin.customer.model.SubCategory;

import java.util.ArrayList;

public class CategoryHomeAdapter extends BaseAdapter {

    private static final String TAG = CategoryHomeAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Category> categoryArrayList;
    private ArrayList<SubCategory> subCategoryArrayList;
    private SubCategoryHorizontalAdapter subCategoryHorizontalAdapter;

    public CategoryHomeAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    @Override
    public int getCount() {
        return categoryArrayList == null ? 0 : categoryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.cell_category_home_fragment, null);
            holder = new ViewHolder();
            holder.rlvCategory = (RelativeLayout) convertView.findViewById(R.id.rlvCategory);
            holder.txtCategory = (TextView) convertView.findViewById(R.id.txtCategory);
            holder.imgViewAllCategory = (ImageView) convertView.findViewById(R.id.imgViewAllCategory);
            holder.rclHorizontalSubCategoty = (RecyclerView) convertView.findViewById(R.id.recycler_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.e(TAG, "categoryId = " + categoryArrayList.get(position).categoryId);

        holder.txtCategory.setText(categoryArrayList.get(position).categoryName);

        subCategoryArrayList = categoryArrayList.get(position).subCategoryArrayList;
        subCategoryHorizontalAdapter = new SubCategoryHorizontalAdapter(context, subCategoryArrayList, categoryArrayList.get(position).categoryId);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rclHorizontalSubCategoty.setLayoutManager(horizontalLayoutManager);
        holder.rclHorizontalSubCategoty.setAdapter(subCategoryHorizontalAdapter);

        if (categoryArrayList.get(position).isCategoryExpand()) {
            holder.rclHorizontalSubCategoty.setVisibility(View.VISIBLE);

        } else {
            holder.rclHorizontalSubCategoty.setVisibility(View.GONE);
        }

        holder.rlvCategory.setOnClickListener(new OnItemClickListener(position));
        holder.imgViewAllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Size : " + categoryArrayList.size());
                if(context!=null && context instanceof  NavigationDrawerActivity) {
                    NavigationDrawerActivity navigationDrawerActivity = (NavigationDrawerActivity) context;
                    navigationDrawerActivity.switchContent(CategoryFragment.newInstance(position, categoryArrayList), false);
                }
            }
        });
        return convertView;

    }

    class ViewHolder {
        public RelativeLayout rlvCategory;
        public TextView txtCategory;
        public RecyclerView rclHorizontalSubCategoty;
        public ImageView imgViewAllCategory;
    }

    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            Log.d(TAG, "Category : " + categoryArrayList.get(mPosition).categoryName);
            if (categoryArrayList.get(mPosition).isCategoryExpand()) {
                categoryArrayList.get(mPosition).setCategoryExpand(false);
            } else {
                categoryArrayList.get(mPosition).setCategoryExpand(true);
            }
            notifyDataSetChanged();
        }
    }


}
