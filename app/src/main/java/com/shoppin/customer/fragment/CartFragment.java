package com.shoppin.customer.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shoppin.customer.R;
import com.shoppin.customer.activity.CheckOutActivity;
import com.shoppin.customer.activity.NavigationDrawerActivity;
import com.shoppin.customer.activity.SigninActivity;
import com.shoppin.customer.adapter.CartProductListAdapter;
import com.shoppin.customer.database.DBAdapter;
import com.shoppin.customer.database.IDatabase;
import com.shoppin.customer.model.Product;
import com.shoppin.customer.utils.Cart;

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

    @BindView(R.id.recyclerListProduct)
    RecyclerView recyclerListProduct;

    @BindView(R.id.txtCartSalePriceTotal)
    TextView txtCartSalePriceTotal;

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
        productListAdapter.setOnItemClickListener(new CartProductListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NavigationDrawerActivity navigationDrawerActivity = (NavigationDrawerActivity) getActivity();
                if (navigationDrawerActivity != null) {
                    navigationDrawerActivity.switchContent(ProductDetailFragment.newInstance(productArrayList.get(position).productId), false);
                }
            }
        });
        productListAdapter.setOnItemLongClickListener(new CartProductListAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        productListAdapter.setOnCartChangeListener(new CartProductListAdapter.OnCartChangeListener() {
            @Override
            public void onCartChange(View view, int position, boolean isProductRemove) {
                if (isProductRemove) {
                    if (productArrayList != null) {
                        productArrayList.clear();
                        productArrayList.addAll(DBAdapter.getAllProductFromCart(getActivity()));
                        productListAdapter.notifyDataSetChanged();
                    }
                }
                updateCartTotal();
            }
        });
        recyclerListProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerListProduct.setAdapter(productListAdapter);


        updateCartTotal();


        return layoutView;
    }

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

    private void updateCartTotal() {
        txtCartSalePriceTotal.setText("Total : $ " + Cart.getCartSalePriceTotal(productArrayList));
    }


    private void showAlertForDeleteProduct(final String productName, final int position) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(getActivity().getString(R.string.alert_message_remove_product, productName));
        alertDialogBuilder.setPositiveButton(R.string.delete,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        productArrayList.get(position).productQuantity = 0;
                        DBAdapter.insertUpdateDeleteCart(getActivity(), productArrayList.get(position), false);
                    }
                });
        alertDialogBuilder.setNegativeButton(R.string.cancel, null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
