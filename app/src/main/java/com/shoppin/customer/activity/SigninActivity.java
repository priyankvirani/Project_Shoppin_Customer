package com.shoppin.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shoppin.customer.R;
import com.shoppin.customer.database.DBAdapter;
import com.shoppin.customer.network.DataRequest;
import com.shoppin.customer.network.IWebService;
import com.shoppin.customer.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shoppin.customer.database.IDatabase.IMap;

public class SigninActivity extends AppCompatActivity {

    private static final String TAG = SplashScreenActivity.class.getSimpleName();

    @BindView(R.id.rlvGlobalProgressbar)
    RelativeLayout rlvGlobalProgressbar;

    @BindView(R.id.etxSigninId)
    EditText etxSigninId;
    @BindView(R.id.etxPassword)
    EditText etxPassword;

    @BindView(R.id.txtSignin)
    TextView txtSignin;
    @BindView(R.id.txtSignup)
    TextView txtSignup;
    @BindView(R.id.txtGuest)
    TextView txtGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

//        rlvGlobalProgressbar.setVisibility(View.VISIBLE);
//        getSuburbs();
    }

    @OnClick(R.id.txtSignin)
    void singIn() {
        if (loginValidation()) {
            DataRequest signinDataRequest = new DataRequest(SigninActivity.this);
            JSONObject loginParam = new JSONObject();
            try {
                loginParam.put(IWebService.KEY_REQ_CUSTOMER_MOBILE, etxSigninId.getText().toString());
                loginParam.put(IWebService.KEY_REQ_CUSTOMER_PASSWORD, etxPassword.getText().toString());
                loginParam.put(IWebService.KEY_REQ_CUSTOMER_DEVICE_TYPE, etxSigninId.getText().toString());
                loginParam.put(IWebService.KEY_REQ_CUSTOMER_DEVICE_TOKEN, etxSigninId.getText().toString());
                loginParam.put(IWebService.KEY_REQ_CUSTOMER_DEVICE_ID, etxSigninId.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            signinDataRequest.execute(IWebService.CUSTOMER_LOGIN, loginParam.toString(), new DataRequest.CallBack() {
                public void onPreExecute() {
                    rlvGlobalProgressbar.setVisibility(View.VISIBLE);
                }

                public void onPostExecute(String response) {
                    try {
                        rlvGlobalProgressbar.setVisibility(View.GONE);
                        if (!DataRequest.hasError(SigninActivity.this, response, true)) {

                            JSONObject dataJObject = DataRequest.getJObjWebdata(response);

                            DBAdapter.insertUpdateMap(SigninActivity.this, IMap.SUBURB_ID,
                                    dataJObject.getString(IWebService.KEY_RES_SUBURB_ID));
                            DBAdapter.insertUpdateMap(SigninActivity.this, IMap.SUBURB_NAME,
                                    dataJObject.getString(IWebService.KEY_RES_SUBURB_NAME));
                            DBAdapter.setMapKeyValueBoolean(SigninActivity.this, IMap.IS_LOGIN, true);

                            Intent intent = new Intent(SigninActivity.this, NavigationDrawerActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private boolean loginValidation() {
        boolean isValid = true;
        etxSigninId.setError(null);
        etxPassword.setError(null);
        if (Utils.isNullOrEmpty(etxSigninId.getText().toString())) {
            etxSigninId.setError(getString(R.string.error_required));
            etxSigninId.requestFocus();
            isValid = false;
        } else if (Utils.isNullOrEmpty(etxPassword.getText().toString())) {
            etxPassword.setError(getString(R.string.error_required));
            etxPassword.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    @OnClick(R.id.txtSignup)
    void singUp() {
        Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }
}
