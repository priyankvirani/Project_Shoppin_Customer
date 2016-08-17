package com.shoppin.customer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.shoppin.customer.R;
import com.shoppin.customer.model.SubCategory;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListFragment extends BaseFragment {
    public static final String SUB_CATEGORY_POSITION = "sub_category_position";
    public static final String SUB_CATEGORY_ARRAYLIST = "sub_category_arraylist";

    private static final String TAG = ProductListFragment.class.getSimpleName();

    @BindView(R.id.suCategoryViewPager)
    ViewPager suCategoryViewPager;
    @BindView(R.id.subCategorySmartTabLayout)
    SmartTabLayout subCategorySmartTabLayout;

    private ArrayList<SubCategory> subCategoryArrayList;
    private int subCategoryPosition = -1;
    private ArrayList<String> subCategoryNameArrayList;
    private FragmentPagerItems fragmentPagerItems;
    private FragmentPagerItemAdapter fragmentPagerItemAdapter;

    public static ProductListFragment newInstance(int subCategoryPosition, ArrayList<SubCategory> categoryArrayList) {
        ProductListFragment categoryFragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SUB_CATEGORY_POSITION, subCategoryPosition);
        bundle.putSerializable(SUB_CATEGORY_ARRAYLIST, categoryArrayList);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_product_list, container, false);
        ButterKnife.bind(this, layoutView);

        if (getArguments() != null) {
            subCategoryArrayList = (ArrayList<SubCategory>) getArguments().getSerializable(SUB_CATEGORY_ARRAYLIST);
            subCategoryPosition = getArguments().getInt(SUB_CATEGORY_POSITION, -1);
            Log.d(TAG, "subCategoryPosition = " + subCategoryPosition);
        }

        fragmentPagerItems = new FragmentPagerItems(getActivity());
        loadTabs();
        setCurrentItem(subCategoryPosition);

        return layoutView;
    }


    private ArrayList<String> getTabName() {
        subCategoryNameArrayList = new ArrayList<>();
        for (int i = 0; i < subCategoryArrayList.size(); i++) {
            subCategoryNameArrayList.add(subCategoryArrayList.get(i).subcat_name);
        }
        return subCategoryNameArrayList;
    }


    private void loadTabs() {
        subCategoryNameArrayList = getTabName();

        for (int i = 0; i < subCategoryNameArrayList.size(); i++) {
            Bundle args = new Bundle();
            args.putSerializable(ProductListFragment.SUB_CATEGORY_ARRAYLIST, (Serializable) subCategoryArrayList);
            args.putInt(ProductListFragment.SUB_CATEGORY_POSITION, subCategoryPosition);
            fragmentPagerItems.add(FragmentPagerItem.of(subCategoryNameArrayList.get(i), ProductListNestedFragment.class, args));
        }

//        fragmentPagerItemAdapter = new FragmentPagerItemAdapter(getActivity().getSupportFragmentManager(), fragmentPagerItems);
        fragmentPagerItemAdapter = new FragmentPagerItemAdapter(getChildFragmentManager(), fragmentPagerItems);
        suCategoryViewPager.setAdapter(fragmentPagerItemAdapter);
        subCategorySmartTabLayout.setViewPager(suCategoryViewPager);

        suCategoryViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Fragment currentFragment = getCurrentPagerFragment(suCategoryViewPager.getCurrentItem());
//                if (currentFragment != null) {
//                    Log.e(TAG, "onTabSelected not null");
//
//                } else {
//                    Log.e(TAG, "onTabSelected null");
//                }

                //fragmentTransaction.addToBackStack(null);
                //fragmentTransaction.commit();
//                if (currentFragment instanceof UpdateableFragment) {
//                    ((UpdateableFragment) currentFragment).update();
//                    Log.e(TAG, "UpdateableFragment");
//                } else {
//                    Log.e(TAG, "non UpdateableFragment");
//                }
                Log.d(TAG, "onPageScrolled position = " + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected position = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        subCategorySmartTabLayout.setOnTabClickListener(new SmartTabLayout.OnTabClickListener() {
            @Override
            public void onTabClicked(int position) {
                //Log.e(TAG, "onTabClicked   : " + position);
                suCategoryViewPager.setCurrentItem(position);

            }
        });

    }


    public Fragment getCurrentPagerFragment(int position) {
        FragmentPagerItemAdapter a = (FragmentPagerItemAdapter) suCategoryViewPager.getAdapter();
        return (Fragment) a.instantiateItem(suCategoryViewPager, position);
    }

    private void setCurrentItem(int i) {
        suCategoryViewPager.setCurrentItem(i);
        Log.d(TAG, "setCurrentItem subCategoryPosition = " + subCategoryPosition);
    }

}
