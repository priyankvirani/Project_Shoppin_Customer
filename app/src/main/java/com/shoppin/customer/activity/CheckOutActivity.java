package com.shoppin.customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import com.shoppin.customer.R;
import com.shoppin.customer.adapter.CheckoutDateAdapter;
import com.shoppin.customer.model.CheckoutDate;
import com.shoppin.customer.model.CheckoutTime;
import com.shoppin.customer.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckOutActivity extends AppCompatActivity {
    public static final int REQUEST_PAYMENT = 1001;
    public static final String RESPONSE_PAYMENT_ID = "payment_id";
    private static final String TAG = CheckOutActivity.class.getSimpleName();

    private int numberOfCell = 20;

    @BindView(R.id.rlvGlobalProgressbar)
    RelativeLayout rlvGlobalProgressbar;

    private ArrayList<CheckoutDate> checkoutDateArrayList;
    private CheckoutDateAdapter checkoutDateAdapter;
    @BindView(R.id.dateRecyclerView)
    RecyclerView dateRecyclerView;

    private ArrayList<CheckoutTime> checkoutTimeArrayList;
//    private CheckoutTimeAdapter checkoutTimeAdapter;
    @BindView(R.id.timeRecyclerView)
    RecyclerView timeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        ButterKnife.bind(this);

        checkoutDateArrayList = new ArrayList<CheckoutDate>();
        checkoutDateAdapter = new CheckoutDateAdapter(CheckOutActivity.this, checkoutDateArrayList,timeRecyclerView);
        LinearLayoutManager horizontalLayoutManagaerdate
                = new LinearLayoutManager(CheckOutActivity.this, LinearLayoutManager.HORIZONTAL, false);
        dateRecyclerView.setLayoutManager(horizontalLayoutManagaerdate);
        dateRecyclerView.setAdapter(checkoutDateAdapter);
        UpdateCheckOutDate();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String paymentId = data.getStringExtra(RESPONSE_PAYMENT_ID);
                Log.d(TAG, "paymentId = " + paymentId);
                Utils.showToastShort(CheckOutActivity.this, "Successfully");
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                Utils.showToastShort(CheckOutActivity.this, "Unsuccessfully");
            }
        }
    }

    @OnClick(R.id.txtPay)
    void makePayment() {
        Intent intent = new Intent(CheckOutActivity.this, PaymentActivity.class);
        startActivityForResult(intent, REQUEST_PAYMENT);
    }

    private void UpdateCheckOutDate() {

        for (int i = 1; i < numberOfCell; i++) {
            //Log.e(TAG, "I : " + i);
            CheckoutDate itemSched = new CheckoutDate();
            itemSched.setDate("Date :" + i);
            itemSched.setSelected(false);
            checkoutTimeArrayList = new ArrayList<>();

            for (int j = 1; j < numberOfCell; j++) {
                //Log.e(TAG, "(" + i + "," + j + ")");
                CheckoutTime itemSubSched = new CheckoutTime();
                itemSubSched.setDate("Date:" + i);
                itemSubSched.setTime("Time:" + j);
                itemSubSched.setSelected(false);
                checkoutTimeArrayList.add(itemSubSched);
                itemSched.setCheckoutTimesArrayList(checkoutTimeArrayList);
            }

            checkoutDateArrayList.add(itemSched);
        }
    }

}
