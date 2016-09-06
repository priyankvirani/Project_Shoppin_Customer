package com.shoppin.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.shoppin.customer.R;
import com.shoppin.customer.database.DBAdapter;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentSuccessfulActivity extends AppCompatActivity {

    private static final String TAG = PaymentSuccessfulActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_successful);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.txtContinueShopping)
    void continueShopping() {
        DBAdapter.clearCart(PaymentSuccessfulActivity.this);
        Intent intent = new Intent(PaymentSuccessfulActivity.this, NavigationDrawerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
    }
}
