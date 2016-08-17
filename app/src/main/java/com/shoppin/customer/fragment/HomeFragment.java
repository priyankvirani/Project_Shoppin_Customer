package com.shoppin.customer.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shoppin.customer.R;
import com.shoppin.customer.activity.SignupActivity;
import com.shoppin.customer.adapter.CategoryHomeAdapter;
import com.shoppin.customer.adapter.OfferHomeAdapter;
import com.shoppin.customer.model.Category;
import com.shoppin.customer.network.DataRequest;
import com.shoppin.customer.network.IWebService;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ubuntu on 15/8/16.
 */

public class HomeFragment extends BaseFragment {

    private static final String TAG = SignupActivity.class.getSimpleName();


    @BindView(R.id.rlvGlobalProgressbar)
    View rlvGlobalProgressbar;
    ViewPager offerViewPager;
    LinearLayout offerViewPagerCount;
    @BindView(R.id.lstCategory)
    ListView lstCategory;
    private int position = 0;
    private Handler handler;
    private Runnable runnable;
    private int dotsCount;


    //    @BindView(R.id.pager)
//    ViewPager offerViewPager;
//
//    @BindView(R.id.viewPagerCountDots)
//    LinearLayout offerViewPagerCount;
    private ImageView[] dots;
    private OfferHomeAdapter offerHomeAdapter;
    private ArrayList<String> offerArrayList;
    private ArrayList<Category> categoryArrayList;
    private CategoryHomeAdapter categoryHomeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        layoutView = inflater.inflate(R.layout.fragment_home, null);
        layoutView = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, layoutView);
        initView();

        initOffers();

        categoryArrayList = new ArrayList<>();
        categoryHomeAdapter = new CategoryHomeAdapter(getActivity(), categoryArrayList);
        lstCategory.setAdapter(categoryHomeAdapter);

        getCategory();

        return layoutView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onResume() {
        super.onResume(); // Always call the superclass method first
        handler.postDelayed(runnable, 3000);
    }

    private void initView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        ViewGroup headerView = (ViewGroup) inflater.inflate(
                R.layout.fragment_home_header, null);
        offerViewPager = (ViewPager) headerView.findViewById(R.id.offerViewPager);
        offerViewPagerCount = (LinearLayout) headerView.findViewById(R.id.offerViewPagerCount);
        lstCategory.addHeaderView(headerView);
    }

    private void getCategory() {
        try {
            JSONObject loginParam = new JSONObject();
            loginParam.put(IWebService.KEY_REQ_IS_HOME, true);
            DataRequest getSuburbsDataRequest = new DataRequest(getActivity());
            getSuburbsDataRequest.execute(IWebService.GET_CATEGORY, loginParam.toString(), new DataRequest.CallBack() {
                public void onPreExecute() {
                    rlvGlobalProgressbar.setVisibility(View.VISIBLE);
                }

                public void onPostExecute(String response) {
                    try {
                        rlvGlobalProgressbar.setVisibility(View.GONE);
                        if (!DataRequest.hasError(getActivity(), response, true)) {
                            JSONObject dataJObject = DataRequest.getJObjWebdata(response);
                            Gson gson = new Gson();
                            ArrayList<Category> tmpCategoryArrayList = gson.fromJson(
                                    dataJObject.getJSONArray(IWebService.KEY_RES_CATEGORY_LIST).toString(),
                                    new TypeToken<ArrayList<Category>>() {
                                    }.getType());

                            if (tmpCategoryArrayList != null) {
                                categoryArrayList.addAll(tmpCategoryArrayList);
                                categoryHomeAdapter.notifyDataSetChanged();
                                Log.d(TAG, "tmpCategoryArrayList = " + tmpCategoryArrayList.size());

                                if (categoryArrayList != null && categoryArrayList.size() >= 2) {
                                    categoryArrayList.get(0).setCategoryExpand(true);
                                    categoryArrayList.get(1).setCategoryExpand(true);
                                } else if (categoryArrayList != null && categoryArrayList.size() >= 1) {
                                    categoryArrayList.get(0).setCategoryExpand(true);
                                }
                            }

                            ArrayList<String> tmpOfferArrayList = gson.fromJson
                                    (dataJObject.getJSONArray(IWebService.KEY_RES_OFFER_LIST).toString(),
                                            new TypeToken<ArrayList<String>>() {
                                            }.getType());
//                            Log.d(TAG, "tmpOfferArrayList = " + tmpOfferArrayList.size());
                            if (tmpOfferArrayList != null) {
                                offerArrayList.clear();
                                offerArrayList.addAll(tmpOfferArrayList);
                                offerHomeAdapter.notifyDataSetChanged();
                                setUiPageViewController();
                                Log.d(TAG, "tmpOfferArrayList = " + tmpOfferArrayList.size());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initOffers() {
        offerArrayList = new ArrayList<>();
        offerHomeAdapter = new OfferHomeAdapter(getActivity(), offerArrayList);

        offerViewPager.setAdapter(offerHomeAdapter);
        offerViewPager.setCurrentItem(0);
        offerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int arg) {
                position = arg;

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_selected_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selected_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (position >= (categoryArrayList.size() - 1)) {
                    position = 0;
                } else {
                    position = position + 1;
                }
                offerViewPager.setCurrentItem(position);
                handler.postDelayed(runnable, 5000);
            }
        };
    }

    private void setUiPageViewController() {

        dotsCount = offerArrayList.size();
        Log.d(TAG, "dotsCount = " + offerArrayList.size());
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_selected_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            offerViewPagerCount.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selected_dot));
    }
}
