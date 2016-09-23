package com.shoppin.customer.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.shoppin.customer.network.DataRequest;
import com.shoppin.customer.network.IWebService;
import com.shoppin.customer.utils.UniqueId;

import org.json.JSONObject;

/**
 * Created by ubuntu on 16/9/16.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        try {
            JSONObject tokenRegistrationParam = new JSONObject();
            tokenRegistrationParam.put(IWebService.KEY_REQ_CUSTOMER_DEVICE_TOKEN, token);
            tokenRegistrationParam.put(IWebService.KEY_REQ_CUSTOMER_DEVICE_ID, UniqueId.getUniqueId(this));

            DataRequest tokenRegistrationDataRequest = new DataRequest(this);
            tokenRegistrationDataRequest.execute(IWebService.FCM_TOKEN_REGISTRATION, tokenRegistrationParam.toString(), new DataRequest.CallBack() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void onPostExecute(String response) {
                    Log.e(TAG, "response = " + response);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}