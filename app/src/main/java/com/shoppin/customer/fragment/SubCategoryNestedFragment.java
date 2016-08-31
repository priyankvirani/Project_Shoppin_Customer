package com.shoppin.customer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.shoppin.customer.R;
import com.shoppin.customer.adapter.SubCategoryNestedAdapter;
import com.shoppin.customer.model.Category;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shoppin.customer.fragment.CategoryFragment.CATEGORY_ARRAYLIST;
import static com.shoppin.customer.fragment.CategoryFragment.CATEGORY_POSITION;

/**
 * Created by ubuntu on 30/7/16.
 */
public class SubCategoryNestedFragment extends BaseFragment {
    private static final String TAG = SubCategoryNestedFragment.class.getSimpleName();

    @BindView(R.id.grdSubCategory)
    GridView grdSubCategory;


    private int categoryPosition = -1;
    private int position;
    private ArrayList<Category> categoryArrayList;
    private SubCategoryNestedAdapter subCategoryNestedAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_nested_sub_category, container, false);
        ButterKnife.bind(this, layoutView);

        if (getArguments() != null) {
            categoryArrayList = (ArrayList<Category>) getArguments().getSerializable(CATEGORY_ARRAYLIST);
            categoryPosition = getArguments().getInt(CATEGORY_POSITION, -1);
            Log.e(TAG, "Size : " + categoryArrayList.size());
        }

        //Category listModelCategory = categoryArrayList.get(0);
        //subCategoryNestedAdapter = new SubCategoryAdapter(getActivity(), listModelCategory.getSub_category());
        //listProduct.setAdapter(subCategoryNestedAdapter);

//        initAdapter();

        return layoutView;
    }


//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        position = FragmentPagerItem.getPosition(getArguments());
//        Log.e(TAG, "Position :  -  " + position);
//        subCategoryNestedAdapter = new SubCategoryAdapter(getActivity(), categoryArrayList.get(position).productOptionValueArrayList);
//        listProduct.setAdapter(subCategoryNestedAdapter);
//        //subCategoryNestedAdapter.notifyDataSetChanged();
//        Log.e(TAG, "onViewCreated");
//
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        Log.e(TAG, "onViewCreated");

    }

    private void initAdapter() {
        position = FragmentPagerItem.getPosition(getArguments());
        Log.e(TAG, "Position :  -  " + position);
        subCategoryNestedAdapter = new SubCategoryNestedAdapter(getActivity(), categoryArrayList.get(position).subCategoryArrayList);
        grdSubCategory.setAdapter(subCategoryNestedAdapter);
    }
}
