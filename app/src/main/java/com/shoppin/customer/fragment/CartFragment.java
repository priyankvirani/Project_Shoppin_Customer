package com.shoppin.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.shoppin.customer.R;
import com.shoppin.customer.activity.CheckOutActivity;
import com.shoppin.customer.activity.NavigationDrawerActivity;
import com.shoppin.customer.activity.SigninActivity;
import com.shoppin.customer.adapter.CartProductListAdapter;
import com.shoppin.customer.database.DBAdapter;
import com.shoppin.customer.database.IDatabase;
import com.shoppin.customer.model.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ubuntu on 30/7/16.
 */
public class CartFragment extends BaseFragment {
    private static final String TAG = CartFragment.class.getSimpleName();

    @BindView(R.id.rlvGlobalProgressbar)
    View rlvGlobalProgressbar;

    @BindView(R.id.listProduct)
    ListView listProduct;

    @BindView(R.id.txtCartSalePriceTotal)
    TextView txtCartSalePriceTotal;

//    ImageView imgCart;
//    TextView txtCartCount;

    private ArrayList<Product> productArrayList;
    private CartProductListAdapter productListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, layoutView);

        productArrayList = new ArrayList<>();
        productArrayList.addAll(DBAdapter.getAllProductFromCart(getActivity()));
        productListAdapter = new CartProductListAdapter(getActivity(), productArrayList);
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
//        initView();
        setCartSalePriceTotal();
        return layoutView;
    }

//    private void initView() {
//        imgCart = (ImageView) getActivity().findViewById(imgCart);
//        txtCartCount = (TextView) getActivity().findViewById(R.id.txtCartCount);
//    }

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

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_fragment_cart, menu);
//        imgCart.setVisibility(View.GONE);
//        txtCartCount.setVisibility(View.GONE);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//        Log.e(TAG, "onOptionsItemSelected");
//
//        int id = item.getItemId();
//        if (id == R.id.action_checkout) {
//            checkOut();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @OnClick(R.id.txtCheckOut)
    void checkOut() {
        if (DBAdapter.getMapKeyValueBoolean(getActivity(), IDatabase.IMap.IS_LOGIN)) {
            Intent intent = new Intent(getActivity(), CheckOutActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), SigninActivity.class);
            startActivity(intent);
        }
    }

    private void setCartSalePriceTotal() {
        double cartSalePriceTotal = 0;
        if (productArrayList != null) {
            for (int i = 0; i < productArrayList.size(); i++) {
                cartSalePriceTotal += productArrayList.get(i).getPriceAsPerSelection();
            }
        }
        txtCartSalePriceTotal.setText("Total : $ " + cartSalePriceTotal);
    }

}
