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
import com.shoppin.customer.model.Category;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends BaseFragment {
    public static final String CATEGORY_POSITION = "category_position";
    public static final String CATEGORY_ARRAYLIST = "category_arraylist";

    private static final String TAG = CategoryFragment.class.getSimpleName();

    @BindView(R.id.categoryViewPager)
    ViewPager categoryViewPager;
    @BindView(R.id.categorySmartTabLayout)
    SmartTabLayout categorySmartTabLayout;

    private ArrayList<Category> categoryArrayList;
    private int categoryPosition = -1;
    private ArrayList<String> categoryNameArrayList;
    private FragmentPagerItems fragmentPagerItems;
    private FragmentPagerItemAdapter fragmentPagerItemAdapter;

    public static CategoryFragment newInstance(int categoryPosition, ArrayList<Category> categoryArrayList) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CATEGORY_POSITION, categoryPosition);
        bundle.putSerializable(CATEGORY_ARRAYLIST, categoryArrayList);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, layoutView);

        if (getArguments() != null) {
            categoryArrayList = (ArrayList<Category>) getArguments().getSerializable(CATEGORY_ARRAYLIST);
            categoryPosition = getArguments().getInt(CATEGORY_POSITION, -1);
        }

        fragmentPagerItems = new FragmentPagerItems(getActivity());
        loadTabs();
        setCurrentItem(categoryPosition);

        return layoutView;
    }


    private ArrayList<String> getTabName() {
        categoryNameArrayList = new ArrayList<>();
        for (int i = 0; i < categoryArrayList.size(); i++) {
            categoryNameArrayList.add(categoryArrayList.get(i).category_name);
        }
        return categoryNameArrayList;
    }


    private void loadTabs() {
        categoryNameArrayList = getTabName();

        for (int i = 0; i < categoryNameArrayList.size(); i++) {
            Bundle args = new Bundle();
            args.putSerializable(CategoryFragment.CATEGORY_ARRAYLIST, categoryArrayList);
            args.putInt(CategoryFragment.CATEGORY_POSITION, categoryPosition);
            fragmentPagerItems.add(FragmentPagerItem.of(categoryNameArrayList.get(i), SubCategoryNestedFragment.class, args));
        }

//        fragmentPagerItemAdapter = new FragmentPagerItemAdapter(getActivity().getSupportFragmentManager(), fragmentPagerItems);
        fragmentPagerItemAdapter = new FragmentPagerItemAdapter(getChildFragmentManager(), fragmentPagerItems);
        categoryViewPager.setAdapter(fragmentPagerItemAdapter);
        categorySmartTabLayout.setViewPager(categoryViewPager);

        categoryViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Fragment currentFragment = getCurrentPagerFragment(categoryViewPager.getCurrentItem());
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
        categorySmartTabLayout.setOnTabClickListener(new SmartTabLayout.OnTabClickListener() {
            @Override
            public void onTabClicked(int position) {
                //Log.e(TAG, "onTabClicked   : " + position);
                categoryViewPager.setCurrentItem(position);

            }
        });

    }


    public Fragment getCurrentPagerFragment(int position) {
        FragmentPagerItemAdapter a = (FragmentPagerItemAdapter) categoryViewPager.getAdapter();
        return (Fragment) a.instantiateItem(categoryViewPager, position);
    }

    private void setCurrentItem(int i) {
        categoryViewPager.setCurrentItem(i);
    }

}
