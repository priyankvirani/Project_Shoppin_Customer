package com.shoppin.customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shoppin.customer.R;
import com.shoppin.customer.adapter.CheckoutDateAdapter;
import com.shoppin.customer.database.DBAdapter;
import com.shoppin.customer.database.IDatabase.IMap;
import com.shoppin.customer.model.Address;
import com.shoppin.customer.model.CheckoutDate;
import com.shoppin.customer.model.Store;
import com.shoppin.customer.network.DataRequest;
import com.shoppin.customer.network.IWebService;
import com.shoppin.customer.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckOutActivity extends AppCompatActivity {
    public static final int REQUEST_PAYMENT = 1001;
    public static final String RESPONSE_PAYMENT_ID = "payment_id";
    public static final String KEY_ADDRESS = "address";

    private static final String TAG = CheckOutActivity.class.getSimpleName();

    @BindView(R.id.toolbarCheckOut)
    Toolbar toolbar;
    @BindView(R.id.rlvGlobalProgressbar)
    RelativeLayout rlvGlobalProgressbar;

    @BindView(R.id.txtCustomerName)
    TextView txtCustomerName;

    @BindView(R.id.txtPhone)
    TextView txtPhone;

    @BindView(R.id.txtStreet)
    TextView txtStreet;

    @BindView(R.id.txtSuburb)
    TextView txtSuburb;

    @BindView(R.id.txtPostCode)
    TextView txtPostCode;

    @BindView(R.id.imgEditAddress)
    ImageView imgEditAddress;

    @BindView(R.id.imgAddNewAddress)
    ImageView imgAddNewAddress;

    @BindView(R.id.etxCoupon)
    EditText etxCoupon;

    @BindView(R.id.cardStore)
    CardView cardStore;

    @BindView(R.id.dateRecyclerView)
    RecyclerView dateRecyclerView;

    @BindView(R.id.timeRecyclerView)
    RecyclerView timeRecyclerView;

    private ArrayList<CheckoutDate> checkoutDateArrayList;
    private CheckoutDateAdapter checkoutDateAdapter;

    private ArrayList<Address> addressArrayList;
    private ArrayList<Store> storeArrayList;

    private Address currentAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        imgEditAddress.setVisibility(View.GONE);
        imgAddNewAddress.setVisibility(View.VISIBLE);

        checkoutDateArrayList = new ArrayList<CheckoutDate>();
        checkoutDateAdapter = new CheckoutDateAdapter(CheckOutActivity.this, checkoutDateArrayList, timeRecyclerView);
        LinearLayoutManager horizontalLayoutManagaerdate
                = new LinearLayoutManager(CheckOutActivity.this, LinearLayoutManager.HORIZONTAL, false);
        dateRecyclerView.setLayoutManager(horizontalLayoutManagaerdate);
        dateRecyclerView.setAdapter(checkoutDateAdapter);

        addressArrayList = new ArrayList<>();
        storeArrayList = new ArrayList<>();

        getCheckOutDetail();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String paymentId = data.getStringExtra(RESPONSE_PAYMENT_ID);
                Log.d(TAG, "paymentId = " + paymentId);
                Utils.showToastShort(CheckOutActivity.this, "Successfully.. clear cart");
                finish();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                Utils.showToastShort(CheckOutActivity.this, "Unsuccessfully");
            }
        } else if (requestCode == AddressEditActivity.REQUEST_CODE_ADDRESS) {
            if (resultCode == Activity.RESULT_OK) {
//                String paymentId = data.getStringExtra(RESPONSE_PAYMENT_ID);
                if (data != null && data.hasExtra(KEY_ADDRESS)) {
                    Address address = data.getParcelableExtra(KEY_ADDRESS);
                    setCurrentAddress(address);
                }
            }
        }
    }

    @OnClick(R.id.imgAddNewAddress)
    void addNewAddress() {
        Intent intent = new Intent(CheckOutActivity.this, ChooseDeliveryActivity.class);
        startActivityForResult(intent, AddressEditActivity.REQUEST_CODE_ADDRESS);
    }


    @OnClick(R.id.txtApplyCoupon)
    void applyCoupon() {
        Utils.showToastShort(CheckOutActivity.this, getString(R.string.under_development));
    }

    @OnClick(R.id.txtPay)
    void makePayment() {
        Intent intent = new Intent(CheckOutActivity.this, PaymentActivity.class);
        startActivityForResult(intent, REQUEST_PAYMENT);
    }

    private void getCheckOutDetail() {
        try {
            JSONObject loginParam = new JSONObject();
            loginParam.put(IWebService.KEY_REQ_CUSTOMER_ID,
                    DBAdapter.getMapKeyValueString(CheckOutActivity.this, IMap.CUSTOMER_ID));
            loginParam.put(IWebService.KEY_REQ_CUSTOMER__SUBURB_ID,
                    DBAdapter.getMapKeyValueString(CheckOutActivity.this, IMap.SUBURB_ID));

            DataRequest checkOutDetailDataRequest = new DataRequest(CheckOutActivity.this);
            checkOutDetailDataRequest.execute(IWebService.GET_CHECKOUT_DETAIL, loginParam.toString(), new DataRequest.CallBack() {
                public void onPreExecute() {
                    rlvGlobalProgressbar.setVisibility(View.VISIBLE);
                }

                public void onPostExecute(String response) {
                    try {
                        rlvGlobalProgressbar.setVisibility(View.GONE);
                        if (!DataRequest.hasError(CheckOutActivity.this, response, true)) {

                            JSONObject dataJObject = DataRequest.getJObjWebdata(response);

                            Gson gson = new Gson();

                            ArrayList<CheckoutDate> tmpCheckoutDateAdapter = gson.fromJson(
                                    dataJObject.getJSONArray(IWebService.KEY_RES_DATE_LIST).toString(),
                                    new TypeToken<ArrayList<CheckoutDate>>() {
                                    }.getType());
                            if (tmpCheckoutDateAdapter != null) {
                                checkoutDateArrayList.clear();
                                checkoutDateArrayList.addAll(tmpCheckoutDateAdapter);
                                checkoutDateArrayList.get(0).setSelected(true);
                                checkoutDateArrayList.get(0).getCheckoutTimesArrayList().get(0).setSelected(true);
                                checkoutDateAdapter.notifyDataSetChanged();
                                Log.d(TAG, "checkoutDateArrayList.size() = " + checkoutDateArrayList.size());
                            }

                            ArrayList<Address> tmpAddressArrayList = gson.fromJson(
                                    dataJObject.getJSONArray(IWebService.KEY_RES_ADDRESS_LIST).toString(),
                                    new TypeToken<ArrayList<Address>>() {
                                    }.getType());
                            if (tmpAddressArrayList != null) {
                                addressArrayList.clear();
                                addressArrayList.addAll(tmpAddressArrayList);
                                Log.d(TAG, "addressArrayList.size() = " + addressArrayList.size());
                            }

                            ArrayList<Store> tmpStoreArrayList = gson.fromJson(
                                    dataJObject.getJSONArray(IWebService.KEY_RES_STORE_LIST).toString(),
                                    new TypeToken<ArrayList<Store>>() {
                                    }.getType());
                            if (tmpAddressArrayList != null) {
                                storeArrayList.clear();
                                storeArrayList.addAll(tmpStoreArrayList);
                                Log.d(TAG, "storeArrayList.size() = " + storeArrayList.size());
                            }

                            setCheckoutDetailOnUi();
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

    private void setCheckoutDetailOnUi() {
        if (addressArrayList != null && addressArrayList.size() > 0) {
            setCurrentAddress(addressArrayList.get(0));
        }

        if (storeArrayList != null && storeArrayList.size() > 0) {
            cardStore.setVisibility(View.VISIBLE);
        } else {
            cardStore.setVisibility(View.GONE);
        }
    }

    private void setCurrentAddress(Address tmpCurrentAddress) {
        this.currentAddress = null;
        this.currentAddress = tmpCurrentAddress;
        txtCustomerName.setText(currentAddress.name);
        txtPhone.setText(currentAddress.phoneNumber);
        txtStreet.setText(currentAddress.street);
        txtSuburb.setText(currentAddress.suburbName);
        txtPostCode.setText(currentAddress.postCode);
    }
}
