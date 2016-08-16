package com.shoppin.customer.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shoppin.customer.R;
import com.shoppin.customer.activity.SignupActivity;
import com.shoppin.customer.adapter.CategoryAdapter;
import com.shoppin.customer.adapter.ViewImageAdapter;
import com.shoppin.customer.model.Category;
import com.shoppin.customer.model.Offer;
import com.shoppin.customer.network.DataRequest;
import com.shoppin.customer.network.IWebService;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ubuntu on 15/8/16.
 */

public class HomeFragment extends BaseFragment {

    private static final String TAG = SignupActivity.class.getSimpleName();

    private int position = 0;
    private Handler handler;
    private Runnable runnable;


    @BindView(R.id.rlvGlobalProgressbar)
    public View rlvGlobalProgressbar;

    @BindView(R.id.lstCategory)
    ListView lstCategory;

    @BindView(R.id.pager)
    ViewPager viewPager;

    private ArrayList<Category> categoryArrayList;
    private CategoryAdapter categoryAdapter;

    private ViewImageAdapter viewImageAdapter;
    private ArrayList<String> offerArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        layoutView = inflater.inflate(R.layout.fragment_home, null);
        layoutView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, layoutView);

        categoryArrayList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getActivity(), categoryArrayList);
        lstCategory.setAdapter(categoryAdapter);

        getCategory();

        if (offerArrayList != null) {
            viewImageAdapter = new ViewImageAdapter(getActivity(), offerArrayList);
            viewPager.setAdapter(viewImageAdapter);
            viewPager.setCurrentItem(0);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int arg) {
                    position = arg;

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
                    viewPager.setCurrentItem(position);
                    handler.postDelayed(runnable, 5000);
                }
            };
        }
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
                                categoryAdapter.notifyDataSetChanged();
                                Log.d(TAG, "tmpCategoryArrayList = " + tmpCategoryArrayList.size());
                            }

//                            Type listType = new TypeToken<List<String>>() {
//                            }.getType();
//                            List<String> yourList = gson.fromJson(String.valueOf(dataJObject.get(IWebService.KEY_RES_OFFER_LIST)), listType);
//                            Log.d(TAG, "tmpOfferArrayList = " + yourList.size());
                            ArrayList<String> tmpOfferArrayList = gson.fromJson
                                    (dataJObject.getJSONArray(IWebService.KEY_RES_OFFER_LIST).toString(),
                                            new TypeToken<ArrayList<String>>() {
                                            }.getType());
//                            Log.d(TAG, "tmpOfferArrayList = " + tmpOfferArrayList.size());
                            if (tmpOfferArrayList != null) {
//                                offerArrayList.addAll(tmpOfferArrayList);
                                offerArrayList.addAll(tmpOfferArrayList);
                                viewImageAdapter.notifyDataSetChanged();
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
}
