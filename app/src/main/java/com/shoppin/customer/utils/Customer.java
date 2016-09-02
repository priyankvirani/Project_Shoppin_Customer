package com.shoppin.customer.utils;

import android.content.Context;

import com.shoppin.customer.database.DBAdapter;
import com.shoppin.customer.database.IDatabase;

/**
 * Created by ubuntu on 2/9/16.
 */

public class Customer {

    public static void singOut(Context context) {
        DBAdapter.insertUpdateMap(context, IDatabase.IMap.SUBURB_ID, "");
        DBAdapter.insertUpdateMap(context, IDatabase.IMap.SUBURB_NAME, "");
        DBAdapter.insertUpdateMap(context, IDatabase.IMap.CUSTOMER_ID, "");
        DBAdapter.insertUpdateMap(context, IDatabase.IMap.CUSTOMER_ADDRESS_ID, "");
        DBAdapter.setMapKeyValueBoolean(context, IDatabase.IMap.IS_LOGIN, false);
    }
}
