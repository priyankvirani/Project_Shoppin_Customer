package com.shoppin.customer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shoppin.customer.R;
import com.shoppin.customer.model.Address;
import com.shoppin.customer.model.Suburb;
import com.shoppin.customer.network.DataRequest;
import com.shoppin.customer.network.IWebService;
import com.shoppin.customer.utils.IConstants;
import com.shoppin.customer.utils.Utils;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ubuntu on 25/8/16.
 */

public class AddressEditActivity extends AppCompatActivity {
    private static final String TAG = AddressEditActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rlvGlobalProgressbar)
    RelativeLayout rlvGlobalProgressbar;

    @BindView(R.id.etxName)
    EditText etxName;

    @BindView(R.id.etxPhone)
    EditText etxPhone;

    @BindView(R.id.etxStreet)
    EditText etxStreet;

    @BindView(R.id.atxSuburb)
    AutoCompleteTextView atxSuburb;

    @BindView(R.id.etxPostcode)
    EditText etxPostCode;

    private ArrayList<Address> addressArrayList;
    private static int ADDRESS_POSITION;

    private ArrayList<Suburb> suburbArrayList;
    private ArrayAdapter<Suburb> suburbArrayAdapter;
    private Suburb selectedSuburb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        ButterKnife.bind(this);

        if (toolbar != null) {
//            toolbar.setNavigationIcon(R.drawable.menu_icon);
//            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.menu_icon));
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(getResources().getString(R.string.activity_address));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        if (intent != null) {
            addressArrayList = new ArrayList<>();
            addressArrayList.addAll((Collection<? extends Address>) intent.getSerializableExtra(IConstants.IRequestCode.KEY_ADDRESS_LIST));
            Log.d(TAG, "addressArrayList.size() = " + addressArrayList.size());
            ADDRESS_POSITION = intent.getIntExtra(IConstants.IRequestCode.KEY_ADDRESS_POSITION, -1);
            Log.d(TAG, "postion = " + ADDRESS_POSITION);

            if (addressArrayList != null && ADDRESS_POSITION >= 0) {
                loadAddress();
            }

        }

        suburbArrayList = new ArrayList<>();
        suburbArrayAdapter = new ArrayAdapter<>(AddressEditActivity.this, android.R.layout.simple_dropdown_item_1line, suburbArrayList);
        atxSuburb.setAdapter(suburbArrayAdapter);
        getSuburbs();
    }

    private void loadAddress() {
        etxName.setText(addressArrayList.get(ADDRESS_POSITION).name);
        etxPhone.setText(addressArrayList.get(ADDRESS_POSITION).phoneNumber);
        etxStreet.setText(addressArrayList.get(ADDRESS_POSITION).street);
        atxSuburb.setText(addressArrayList.get(ADDRESS_POSITION).suburb);
        etxPostCode.setText(addressArrayList.get(ADDRESS_POSITION).postCode);
    }

    private void getSuburbs() {
        DataRequest getSuburbsDataRequest = new DataRequest(AddressEditActivity.this);
        getSuburbsDataRequest.execute(IWebService.GET_SUBURB, null, new DataRequest.CallBack() {
            public void onPreExecute() {
                rlvGlobalProgressbar.setVisibility(View.VISIBLE);
            }

            public void onPostExecute(String response) {
                try {
                    rlvGlobalProgressbar.setVisibility(View.GONE);
                    if (!DataRequest.hasError(AddressEditActivity.this, response, true)) {
                        Gson gson = new Gson();
                        JSONObject dataJObject = DataRequest.getJObjWebdata(response);

                        ArrayList<Suburb> tmpSuburbArrayList = gson.fromJson(
                                dataJObject.getJSONArray(IWebService.KEY_RES_SUBURB_LIST).toString(),
                                new TypeToken<ArrayList<Suburb>>() {
                                }.getType());
                        if (tmpSuburbArrayList != null) {
                            Log.e(TAG, "size = " + tmpSuburbArrayList.size());
                            suburbArrayList.addAll(tmpSuburbArrayList);
                            suburbArrayAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_address, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_save:
                if (validData()) {
                    Intent data = new Intent();
                    data.putExtra(IConstants.IRequestCode.KEY_ADDRESS_LIST, (Serializable) addressArrayList);
                    setResult(IConstants.IRequestCode.REQUEST_CODE_ADDRESS, data);
                    updateAddress();
                    onBackPressed();
                }
                return true;
            case R.id.action_delete:
                deleteAddress();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etxName.getWindowToken(), 0);
        finish();
    }

    private boolean validData() {
        boolean isValid = true;
        etxName.setError(null);
        etxPhone.setError(null);
        etxStreet.setError(null);
        atxSuburb.setError(null);
        etxPostCode.setError(null);

        selectedSuburb = Utils.getSelectedSuburb(suburbArrayList, atxSuburb.getText().toString());

        if (Utils.isNullOrEmpty(etxName.getText().toString())) {
            etxName.setError(getString(R.string.error_required));
            etxName.requestFocus();
            isValid = false;
        } else if (Utils.isNullOrEmpty(etxPhone.getText().toString())) {
            etxPhone.setError(getString(R.string.error_required));
            etxPhone.requestFocus();
            isValid = false;
        } else if (Utils.isNullOrEmpty(etxStreet.getText().toString())) {
            etxStreet.setError(getString(R.string.error_required));
            etxStreet.requestFocus();
            isValid = false;
        } else if (selectedSuburb == null) {
            atxSuburb.setError(getString(R.string.error_valid_suburb));
            atxSuburb.requestFocus();
            isValid = false;
        } else if (Utils.isNullOrEmpty(etxPostCode.getText().toString())) {
            etxPostCode.setError(getString(R.string.error_required));
            etxPostCode.requestFocus();
            isValid = false;
        } else if (Utils.isNullOrEmpty(etxPostCode.getText().toString())) {
            etxPostCode.setError(getString(R.string.error_required));
            etxPostCode.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    private void updateAddress() {
        String name = etxName.getText().toString().trim();
        String phoneNumber = etxPhone.getText().toString().trim();
        String street = etxStreet.getText().toString().trim();
        String suburb = atxSuburb.getText().toString().trim();
        String postcode = etxPostCode.getText().toString().trim();
        Address address = new Address(name, phoneNumber, street, suburb, postcode);
        if (ADDRESS_POSITION >= 0) {

            addressArrayList.remove(ADDRESS_POSITION);
            addressArrayList.add(ADDRESS_POSITION, address);

        } else {
            addressArrayList.add(address);
        }
    }

    private void deleteAddress() {
        if (ADDRESS_POSITION >= 0) {
            Intent data = new Intent();
            data.putExtra(IConstants.IRequestCode.KEY_ADDRESS_LIST, (Serializable) addressArrayList);
            setResult(IConstants.IRequestCode.REQUEST_CODE_ADDRESS, data);
            addressArrayList.remove(ADDRESS_POSITION);
            onBackPressed();
        }
    }


}

