package com.shoppin.customer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.shoppin.customer.R;
import com.shoppin.customer.activity.NavigationDrawerActivity;
import com.shoppin.customer.adapter.ProductListAdapter;
import com.shoppin.customer.model.Product;
import com.shoppin.customer.model.SubCategory;
import com.shoppin.customer.network.DataRequest;
import com.shoppin.customer.network.IWebService;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shoppin.customer.fragment.ProductListFragment.SUB_CATEGORY_ARRAYLIST;
import static com.shoppin.customer.fragment.ProductListFragment.SUB_CATEGORY_POSITION;

/**
 * Created by ubuntu on 30/7/16.
 */
public class ProductListNestedFragment extends BaseFragment {
    private static final String TAG = ProductListNestedFragment.class.getSimpleName();

    @BindView(R.id.rlvGlobalProgressbar)
    View rlvGlobalProgressbar;

    @BindView(R.id.listProduct)
    ListView listProduct;

    private ArrayList<SubCategory> subCategoryArrayList;
    private int subCategoryPosition = -1;
    private int position;
    private ArrayList<Product> productArrayList;
    private ProductListAdapter productListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_nested_product_list, container, false);
        ButterKnife.bind(this, layoutView);

        if (getArguments() != null) {
            subCategoryArrayList = (ArrayList<SubCategory>) getArguments().getSerializable(SUB_CATEGORY_ARRAYLIST);
            subCategoryPosition = getArguments().getInt(SUB_CATEGORY_POSITION, -1);
        }

        productArrayList = new ArrayList<>();
        productListAdapter = new ProductListAdapter(getActivity(), productArrayList);
        listProduct.setAdapter(productListAdapter);
        listProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NavigationDrawerActivity navigationDrawerActivity = (NavigationDrawerActivity) getActivity();
                if (navigationDrawerActivity != null) {
                    navigationDrawerActivity.switchContent(ProductDetailFragment.newInstance(productArrayList.get(i).productId), false);
                }
            }
        });

//        initAdapter();
        getProductBySubCategory();
        return layoutView;
    }


//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        position = FragmentPagerItem.getPosition(getArguments());
//        Log.e(TAG, "Position :  -  " + position);
//        productListAdapter = new SubCategoryAdapter(getActivity(), productArrayList.get(position).productArrayList);
//        listProduct.setAdapter(productListAdapter);
//        //productListAdapter.notifyDataSetChanged();
//        Log.e(TAG, "onViewCreated");
//
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initAdapter();
        Log.e(TAG, "onViewCreated");

    }

//    private void initAdapter() {
//        position = FragmentPagerItem.getPosition(getArguments());
//        Log.e(TAG, "Position :  -  " + position);
//        productListAdapter = new SubCategoryNestedAdapter(getActivity(), productArrayList.get(position).productOptionValueArrayList);
//        listProduct.setAdapter(productListAdapter);
//    }

    private void getProductBySubCategory() {
        try {
            position = FragmentPagerItem.getPosition(getArguments());
            JSONObject loginParam = new JSONObject();
            loginParam.put(IWebService.KEY_REQ_SUB_CATEGORY_ID, subCategoryArrayList.get(position).subcategoryId);
            DataRequest getSuburbsDataRequest = new DataRequest(getActivity());
            getSuburbsDataRequest.execute(IWebService.GET_PRODUCT_BY_SUB_CATEGORY, loginParam.toString(), new DataRequest.CallBack() {
                public void onPreExecute() {
                    rlvGlobalProgressbar.setVisibility(View.VISIBLE);
                }

                public void onPostExecute(String response) {
                    try {
                        rlvGlobalProgressbar.setVisibility(View.GONE);
                        if (!DataRequest.hasError(getActivity(), response, true)) {
                            JSONObject dataJObject = DataRequest.getJObjWebdata(response);
                            Gson gson = new Gson();
                            ArrayList<Product> tmpProductArrayList = gson.fromJson(
                                    dataJObject.getJSONArray(IWebService.KEY_RES_PRODUCT_LIST).toString(),
                                    new TypeToken<ArrayList<Product>>() {
                                    }.getType());

                            if (tmpProductArrayList != null) {
                                productArrayList.clear();
                                productArrayList.addAll(tmpProductArrayList);
                                productListAdapter.notifyDataSetChanged();
                                Log.d(TAG, "productArrayList = " + productArrayList.size());
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
