package com.shoppin.customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shoppin.customer.R;
import com.shoppin.customer.adapter.AddressAdapter;
import com.shoppin.customer.database.DBAdapter;
import com.shoppin.customer.database.IDatabase;
import com.shoppin.customer.model.Address;
import com.shoppin.customer.network.DataRequest;
import com.shoppin.customer.network.IWebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseDeliveryActivity extends AppCompatActivity {
    private static final String TAG = ChooseDeliveryActivity.class.getSimpleName();

    @BindView(R.id.toolbarChooseDelivery)
    Toolbar toolbar;
    @BindView(R.id.rlvGlobalProgressbar)
    RelativeLayout rlvGlobalProgressbar;
    @BindView(R.id.rclAddress)
    RecyclerView rclAddress;

    private ArrayList<Address> addressArrayList;
    private AddressAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_delivery);
        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        addressArrayList = new ArrayList<>();
        addressAdapter = new AddressAdapter(ChooseDeliveryActivity.this, addressArrayList, null);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ChooseDeliveryActivity.this);
        rclAddress.setLayoutManager(mLayoutManager);
        rclAddress.setAdapter(addressAdapter);
        addressAdapter.setOnItemClickListener(new AddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(CheckOutActivity.KEY_ADDRESS, addressArrayList.get(position));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        getAddressList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_choose_delivery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        } else if (item.getItemId() == R.id.action_add_new_address) {
            Intent intent = new Intent(ChooseDeliveryActivity.this, AddressEditActivity.class);
            startActivityForResult(intent, AddressEditActivity.REQUEST_CODE_ADDRESS);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "resultCode = " + requestCode);
        if (requestCode == AddressEditActivity.REQUEST_CODE_ADDRESS) {
            Log.d(TAG, "resultCode = " + resultCode);
            if (resultCode == Activity.RESULT_OK) {
                getAddressList();
            }
        }
    }

    private void getAddressList() {
        DataRequest addressDataRequest = new DataRequest(ChooseDeliveryActivity.this);
        JSONObject addressParams = new JSONObject();
        try {
            addressParams.put(IWebService.KEY_RES_CUSTOMER_ID, DBAdapter.getMapKeyValueString(ChooseDeliveryActivity.this, IDatabase.IMap.CUSTOMER_ID));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        addressDataRequest.execute(IWebService.CUSTOMER_ADDRESS_LIST, addressParams.toString(), new DataRequest.CallBack() {
            @Override
            public void onPreExecute() {
                rlvGlobalProgressbar.setVisibility(View.VISIBLE);
                addressArrayList.clear();
                addressAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPostExecute(String response) {
                Log.d(TAG, "response = " + response);
                rlvGlobalProgressbar.setVisibility(View.GONE);
                if (!DataRequest.hasError(ChooseDeliveryActivity.this, response, true)) {
                    Gson gson = new Gson();
                    JSONObject dataJObject = DataRequest.getJObjWebdata(response);

                    try {
                        ArrayList<Address> tmpAddressArrayList = gson.fromJson(
                                dataJObject.getJSONArray(IWebService.KEY_RES_ADDRESS_LIST).toString(),
                                new TypeToken<ArrayList<Address>>() {
                                }.getType());
                        if (tmpAddressArrayList != null) {
                            Log.e(TAG, "size = " + tmpAddressArrayList.size());
                            addressArrayList.addAll(tmpAddressArrayList);
                            addressAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
