package com.shoppin.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shoppin.customer.R;
import com.shoppin.customer.activity.AddressEditActivity;
import com.shoppin.customer.activity.NavigationDrawerActivity;
import com.shoppin.customer.activity.SigninActivity;
import com.shoppin.customer.activity.SignupActivity;
import com.shoppin.customer.adapter.AddressRecyleAdapter;
import com.shoppin.customer.database.DBAdapter;
import com.shoppin.customer.model.Address;
import com.shoppin.customer.model.Suburb;
import com.shoppin.customer.network.DataRequest;
import com.shoppin.customer.network.IWebService;
import com.shoppin.customer.utils.IConstants;
import com.shoppin.customer.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shoppin.customer.R.id.atxSuburb;
import static com.shoppin.customer.R.id.rlvGlobalProgressbar;
import static com.shoppin.customer.database.IDatabase.IMap;

/**
 * Created by ubuntu on 15/8/16.
 */

public class MyAccountFragment extends BaseFragment {

    private static final String TAG = MyAccountFragment.class.getSimpleName();

    @BindView(R.id.rlvGlobalProgressbar)
    RelativeLayout rlvGlobalProgressbar;

    @BindView(R.id.lstAddress)
    RecyclerView lstAddress;

    @BindView(R.id.txtAddNewAddress)
    TextView txtAddNewAddress;

    @BindView(R.id.etxName)
    EditText etxName;

    @BindView(R.id.etxPhoneNumber)
    EditText etxPhoneNumber;

    @BindView(R.id.etxStreet)
    EditText etxStreet;

    @BindView(R.id.atxSuburb)
    AutoCompleteTextView atxSuburb;

    @BindView(R.id.etxPostcode)
    EditText etxPostcode;

    @BindView(R.id.etxPassword)
    EditText etxPassword;

    @BindView(R.id.txtUpdate)
    TextView txtUpdate;

    private ArrayList<Address> addressArrayList;
    private AddressRecyleAdapter addressAdapter;

    private ArrayList<Suburb> suburbArrayList;
    private ArrayAdapter<Suburb> suburbArrayAdapter;
    private Suburb selectedSuburb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        layoutView = inflater.inflate(R.layout.fragment_home, null);
        layoutView = inflater.inflate(R.layout.fragment_my_account, container, false);
        ButterKnife.bind(this, layoutView);

        addressArrayList = new ArrayList<>();
        addressAdapter = new AddressRecyleAdapter(getActivity(), addressArrayList, MyAccountFragment.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        lstAddress.setLayoutManager(mLayoutManager);
        lstAddress.setAdapter(addressAdapter);

        suburbArrayList = new ArrayList<>();
        suburbArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, suburbArrayList);
        atxSuburb.setAdapter(suburbArrayAdapter);
        getSuburbs();


        Log.d(TAG, "customer_id = " + DBAdapter.getMapKeyValueString(getActivity(), IMap.CUSTOMER_ID));
        dummyData();

        txtAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddressEditActivity.class);
                intent.putExtra(IConstants.IRequestCode.KEY_ADDRESS_LIST, (Serializable) addressArrayList);
                startActivityNew(intent);
            }
        });

        txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupValidation();
            }
        });

        return layoutView;
    }


    @OnClick(R.id.btnLogOut)
    void logOut() {
        DBAdapter.insertUpdateMap(getActivity(), IMap.SUBURB_ID, "");
        DBAdapter.insertUpdateMap(getActivity(), IMap.SUBURB_NAME, "");
        DBAdapter.insertUpdateMap(getActivity(), IMap.CUSTOMER_ID, "");
        DBAdapter.insertUpdateMap(getActivity(), IMap.CUSTOMER_ADDRESS_ID, "");

        Intent intent = new Intent(getActivity(), SigninActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void updateFragment() {
        super.updateFragment();
        if (getActivity() != null && getActivity() instanceof NavigationDrawerActivity) {
            ((NavigationDrawerActivity) getActivity()).setToolbarTitle(
                    getActivity().getResources().getString(R.string.fragment_myaccount));
        }
    }

    private void dummyData() {
        Address address1 = new Address("Peter Parker", "1234567890", "Surat", "Surat", "345678");
        addressArrayList.add(address1);

        Address address2 = new Address("Peter Parker", "1234567890", "Surat", "Surat", "345678");
        addressArrayList.add(address2);

        addressAdapter.notifyDataSetChanged();
    }

//    @OnClick(R.id.txtAddNewAddress)

    public void startActivityNew(Intent intent) {
        startActivityForResult(intent, IConstants.IRequestCode.REQUEST_CODE_ADDRESS);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IConstants.IRequestCode.REQUEST_CODE_ADDRESS) {
            if (data != null) {
                addressArrayList.clear();
                addressArrayList.addAll((Collection<? extends Address>) data.getSerializableExtra(IConstants.IRequestCode.KEY_ADDRESS_LIST));
                Log.d(TAG, "addressArrayList.size() = " + addressArrayList.size());
                addressAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getSuburbs() {
        DataRequest getSuburbsDataRequest = new DataRequest(getActivity());
        getSuburbsDataRequest.execute(IWebService.GET_SUBURB, null, new DataRequest.CallBack() {
            public void onPreExecute() {
                rlvGlobalProgressbar.setVisibility(View.VISIBLE);
            }

            public void onPostExecute(String response) {
                try {
                    rlvGlobalProgressbar.setVisibility(View.GONE);
                    if (!DataRequest.hasError(getActivity(), response, true)) {
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

    public boolean signupValidation() {
        boolean isValid = true;
        etxName.setError(null);
        etxPhoneNumber.setError(null);
        etxStreet.setError(null);
        atxSuburb.setError(null);
        etxPostcode.setError(null);
        etxPassword.setError(null);

        selectedSuburb = Utils.getSelectedSuburb(suburbArrayList, atxSuburb.getText().toString());

        if (Utils.isNullOrEmpty(etxName.getText().toString())) {
            etxName.setError(getString(R.string.error_required));
            etxName.requestFocus();
            isValid = false;
        }
//        else if (Utils.isNullOrEmpty(etxPhoneNumber.getText().toString())) {
//            etxPhoneNumber.setError(getString(R.string.error_required));
//            etxPhoneNumber.requestFocus();
//            isValid = false;
//        }
        else if (Utils.isNullOrEmpty(etxStreet.getText().toString())) {
            etxStreet.setError(getString(R.string.error_required));
            etxStreet.requestFocus();
            isValid = false;
        } else if (selectedSuburb == null) {
            atxSuburb.setError(getString(R.string.error_valid_suburb));
            atxSuburb.requestFocus();
            isValid = false;
        } else if (Utils.isNullOrEmpty(etxPostcode.getText().toString())) {
            etxPostcode.setError(getString(R.string.error_required));
            etxPostcode.requestFocus();
            isValid = false;
        } else if (Utils.isNullOrEmpty(etxPassword.getText().toString())) {
            etxPassword.setError(getString(R.string.error_required));
            etxPassword.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    private void updateMyAccountDetails() {
        DataRequest updateDataRequest = new DataRequest(getActivity());
        JSONObject updateParms = new JSONObject();
        try {
            updateParms.put(IWebService.KEY_RES_CUSTOMER_ID,
                    DBAdapter.getMapKeyValueString(getActivity(), IMap.CUSTOMER_ID));
            updateParms.put(IWebService.KEY_REQ_ADDRESS_ID,
                    DBAdapter.getMapKeyValueString(getActivity(), IMap.CUSTOMER_ADDRESS_ID));
            updateParms.put(IWebService.KEY_REQ_CUSTOMER_NAME, etxName.getText().toString());
//            updateParms.put(IWebService.KEY_REQ_CUSTOMER_MOBILE, etxPhoneNumber.getText().toString());
            updateParms.put(IWebService.KEY_REQ_CUSTOMER_STREET, etxStreet.getText().toString());
            updateParms.put(IWebService.KEY_REQ_CUSTOMER_SUBURB_ID, selectedSuburb.suburb_id);
            updateParms.put(IWebService.KEY_REQ_CUSTOMER_POSTCODE, etxPostcode.getText().toString());
            updateParms.put(IWebService.KEY_REQ_CUSTOMER_PASSWORD, etxPassword.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateDataRequest.execute(IWebService.CUSTOMER_ACCOUNT_UPDATE, updateParms.toString(), new DataRequest.CallBack() {
            @Override
            public void onPreExecute() {
                rlvGlobalProgressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPostExecute(String response) {
                rlvGlobalProgressbar.setVisibility(View.GONE);
                Log.d(TAG, "response = " + response);
                if (!DataRequest.hasError(getActivity(), response, true)) {

                }
            }
        });
    }

    private void getAddressList() {
        DataRequest addressDataRequest = new DataRequest(getActivity());
        JSONObject addressParams = new JSONObject();

        try {
            addressParams.put(IWebService.KEY_RES_CUSTOMER_ID, DBAdapter.getMapKeyValueString(getActivity(), IMap.CUSTOMER_ID));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        addressDataRequest.execute(IWebService.CUSTOMER_ADDRESS_LIST, addressParams.toString(), new DataRequest.CallBack() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onPostExecute(String response) {
                Log.d(TAG, "response = " + response);
                if (!DataRequest.hasError(getActivity(), response, true)) {

                }
            }
        });
    }

}
