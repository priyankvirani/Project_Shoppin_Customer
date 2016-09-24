package com.shoppin.customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.shoppin.customer.R;
import com.shoppin.customer.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = PaymentActivity.class.getSimpleName();

    @BindView(R.id.etxCardNumber)
    EditText etxCardNumber;

    @BindView(R.id.etxExpireDate)
    EditText etxExpireDate;

    @BindView(R.id.etxCvv)
    EditText etxCvv;

    private String tempStringCardNumber;
    private char[] stringArrayCardNumber;

    private String tempStringExpireDate;
    private char[] stringArrayExpireDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        cardsValueFormat();


    }

    @OnClick(R.id.txtPayNow)
    void clickToPay() {
        if (creditCardValidation()) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(CheckOutActivity.RESPONSE_PAYMENT_ID, "payment123");
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }


    private boolean cardsValueFormat() {

        etxCardNumber.addTextChangedListener(new TextWatcher() {
                                                 @Override
                                                 public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                 }

                                                 @Override
                                                 public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                    if (etxCardNumber.getText().length() == 5 || etxCardNumber.getText().length() == 10 || etxCardNumber.getText().length() == 15) {
                                                         tempStringCardNumber = etxCardNumber.getText().toString() + "-";
                                                         char c = tempStringCardNumber.charAt(tempStringCardNumber.length() - 2);

                                                         if (c != '-') {
                                                             stringArrayCardNumber = tempStringCardNumber.toCharArray();
                                                             stringArrayCardNumber[tempStringCardNumber.length() - 2] = stringArrayCardNumber[tempStringCardNumber.length() - 1];
                                                             stringArrayCardNumber[tempStringCardNumber.length() - 1] = c;

                                                             //code to convert charArray back to String..
                                                             tempStringCardNumber = new String(stringArrayCardNumber);
                                                             etxCardNumber.setText(tempStringCardNumber);
                                                             etxCardNumber.setSelection(tempStringCardNumber.length());
                                                             tempStringCardNumber = null;
                                                         }
                                                     }


                                                 }

                                                 @Override
                                                 public void afterTextChanged(Editable s) {


                                                 }
                                             }

        );
        etxExpireDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etxExpireDate.getText().length() == 3) {
                    tempStringExpireDate = etxExpireDate.getText().toString() + "/";
                    char c = tempStringExpireDate.charAt(tempStringExpireDate.length() - 2);

                    if (c != '/') {
                        stringArrayExpireDate = tempStringExpireDate.toCharArray();
                        stringArrayExpireDate[tempStringExpireDate.length() - 2] = stringArrayExpireDate[tempStringExpireDate.length() - 1];
                        stringArrayExpireDate[tempStringExpireDate.length() - 1] = c;

                        //code to convert charArray back to String..
                        tempStringExpireDate = new String(stringArrayExpireDate);
                        etxExpireDate.setText(tempStringExpireDate);
                        etxExpireDate.setSelection(tempStringExpireDate.length());
                        tempStringExpireDate = null;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return false;
    }

    private boolean creditCardValidation() {
        boolean isValid = true;
        etxCardNumber.setError(null);
        etxExpireDate.setError(null);
        etxCvv.setError(null);
        if (Utils.isNullOrEmpty(etxCardNumber.getText().toString())) {
            etxCardNumber.setError(getString(R.string.error_required));
            etxCardNumber.requestFocus();
            isValid = false;
        } else if (Utils.isNullOrEmpty(etxExpireDate.getText().toString())) {
            etxExpireDate.setError(getString(R.string.error_required));
            etxExpireDate.requestFocus();
            isValid = false;
        } else if (Utils.isNullOrEmpty(etxCvv.getText().toString())) {
            etxCvv.setError(getString(R.string.error_required));
            etxCvv.requestFocus();
            isValid = false;
        }
//        else {
//            //isExpireDate = etxExpireDate.getText().toString().matches("(?:0[1-9]|1[0-2])/[0-9]{2}");
//
//            String input = etxExpireDate.getText().toString();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
//            simpleDateFormat.setLenient(false);
//            Date expiry = null;
//            try {
//                expiry = simpleDateFormat.parse(input);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            boolean expired = expiry.before(new Date());
//            boolean expired1 = expiry.after(new Date());
//
//            Log.e(TAG, "expired  : "+expired);
//            Log.e(TAG, "expired1 : "+expired1);
//
//            if (expired == true) {
//                Log.e(TAG, "This card has already expired");
//                etxExpireDate.setError(getString(R.string.error_card_expires));
//                etxExpireDate.requestFocus();
//                isValid = false;
//            }
//
//        }
        return isValid;
    }
}
