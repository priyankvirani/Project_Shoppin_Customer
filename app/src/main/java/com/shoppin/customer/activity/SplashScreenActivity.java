package com.shoppin.customer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.shoppin.customer.R;
import com.shoppin.customer.database.DBAdapter;
import com.shoppin.customer.network.DataRequest;
import com.shoppin.customer.network.IWebService;
import com.shoppin.customer.utils.Utils;

import org.json.JSONObject;

import static com.shoppin.customer.database.IDatabase.IMap;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = SplashScreenActivity.class.getSimpleName();

    private static long SLEEP_TIME = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        checkForInternet();
    }

    private void startApp() {
        IntentLauncher launcher = new IntentLauncher();
        launcher.start();
    }

    private void checkForInternet() {
        if (!Utils.isInternetAvailable(SplashScreenActivity.this, false)) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setMessage(R.string.error_internet_check);
            alertDialogBuilder.setPositiveButton(R.string.retry,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "onClick");
                            if (!Utils.isInternetAvailable(
                                    SplashScreenActivity.this, false)) {
                                AlertDialog alertDialog = alertDialogBuilder
                                        .create();
                                alertDialog.show();
                            } else {
                                appVersionVerify();
                            }
                        }
                    });
            alertDialogBuilder.setNegativeButton(R.string.cancel,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } else {
            appVersionVerify();
        }
    }

    private void appVersionVerify() {
        JSONObject appVersionParam = new JSONObject();
        try {
            appVersionParam.put(IWebService.KEY_REQ_DEVICE_TYPE, "android");
            int versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            appVersionParam.put(IWebService.KEY_REQ_CURRENT_VERSION, versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataRequest appVersionVerifyDataRequest = new DataRequest(SplashScreenActivity.this);
        appVersionVerifyDataRequest.execute(IWebService.APP_VERSION_VERIFY, appVersionParam.toString(), new DataRequest.CallBack() {
            public void onPreExecute() {
//                rlvGlobalProgressbar.setVisibility(View.VISIBLE);
            }

            public void onPostExecute(String response) {
                try {
//                    rlvGlobalProgressbar.setVisibility(View.GONE);
                    if (!DataRequest.hasError(SplashScreenActivity.this, response, true)) {
                        JSONObject dataJObject = DataRequest.getJObjWebdata(response);
                        if (dataJObject.getBoolean(IWebService.KEY_RES_UPDATE_REQUIRE)) {
                            showAlertForAppUpdate();
                        } else {
                            startApp();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void showAlertForAppUpdate() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(R.string.error_app_update);
        alertDialogBuilder.setPositiveButton(R.string.update,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToPlayStore();
                    }
                });
        alertDialogBuilder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void goToPlayStore() {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private class IntentLauncher extends Thread {

        @Override
        /**
         * Sleep for some time and than start new activity.
         */
        public void run() {
            try {
                // Sleeping
                Thread.sleep(SLEEP_TIME * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Utils.isNullOrEmpty(DBAdapter.getMapKeyValueString(SplashScreenActivity.this, IMap.SUBURB_ID))) {
                Intent intent = new Intent(SplashScreenActivity.this, SigninActivity.class);
                startActivity(intent);
                finish();
            } else {
//                if (DBAdapter.getMapKeyValueBoolean(SplashScreenActivity.this, IMap.IS_LOGIN)) {
                Intent intent = new Intent(SplashScreenActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

}
