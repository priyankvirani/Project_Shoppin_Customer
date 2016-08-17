package com.shoppin.customer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class SubCategoryNestedAdapter extends BaseAdapter {

    private static final String TAG = SubCategoryNestedAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<SubCategory> subCategoryArrayList;
    private int cellWidth = 100;

    public SubCategoryNestedAdapter(Context context, ArrayList<SubCategory> subCategoryArrayList) {
        this.context = context;
        this.subCategoryArrayList = subCategoryArrayList;
        this.cellWidth = Utils.getDeviceWidth(context) / 3;
    }

    @Override
    public int getCount() {
        return subCategoryArrayList == null ? 0 : subCategoryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return subCategoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.cell_subcategory_home, null);
            holder = new ViewHolder();
            holder.txtSubcategory = (TextView) convertView.findViewById(R.id.txtSubcategory);
            holder.imgSubcategory = (ImageView) convertView.findViewById(R.id.imgSubcategory);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtSubcategory.setText(subCategoryArrayList.get(position).subcat_name);
        Glide.with(context)
                .load(subCategoryArrayList.get(position).subcat_image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.imgSubcategory);
        holder.imgSubcategory.setOnClickListener(new OnItemClickListener(subCategoryArrayList.get(position)));

//        convertView.setLayoutParams(new GridView.LayoutParams(
//                GridView.AUTO_FIT, Utils.dpToPx(cellWidth)));
//        Log.d(TAG, "cellWidth = " + cellWidth);
        convertView.setLayoutParams(new GridView.LayoutParams(cellWidth, cellWidth));
        return convertView;
    }

    public static class ViewHolder {
        public TextView txtSubcategory;
        public ImageView imgSubcategory;
    }

    private class OnItemClickListener implements View.OnClickListener {
        private SubCategory listModelSubCategoryArr;

        OnItemClickListener(SubCategory listModelSubCategory) {
            listModelSubCategoryArr = listModelSubCategory;
        }

        @Override
        public void onClick(View arg0) {
            Utils.showToastShort(context, "Under Development : " + listModelSubCategoryArr.subcat_name);
        }
    }
}
