package com.shoppin.customer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shoppin.customer.model.Product;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import static com.shoppin.customer.database.IDatabase.ICart;
import static com.shoppin.customer.database.IDatabase.IMap;

/**
 * Created by ubuntu on 27/4/16.
 */
public class DBAdapter {
    private static final String TAG = DBAdapter.class.getSimpleName();

    public static void insertUpdateMap(Context context, String key, String value) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMap.KEY_MAP_VALUE, value);

        Cursor cursor = db.query(IMap.TABLE_MAP, new String[]{IMap.KEY_ID}, IMap.KEY_MAP_KEY + " = '" + key + "'", null, null, null, null, null);
        int index = -1;
        if (cursor != null && cursor.getCount() > 0) { //if the row exist then return the id
            cursor.moveToFirst();
            index = cursor.getInt(cursor.getColumnIndex(IMap.KEY_ID));
            cursor.close();
        }

        if (index == -1) {
            contentValues.put(IMap.KEY_MAP_KEY, key);
            db.insert(IMap.TABLE_MAP, null, contentValues);
        } else {
            db.update(IMap.TABLE_MAP, contentValues, IMap.KEY_ID + " = '" + index + "'", null);
        }
        Log.d(TAG, "insertUpdateMap key = " + key + ", value = " + value);
    }

    public static String getMapKeyValueString(Context context, String key) {
        String value = null;
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + IMap.TABLE_MAP + " where " + IMap.KEY_MAP_KEY + " = '" + key + "'", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            value = cursor.getString(cursor.getColumnIndexOrThrow(IMap.KEY_MAP_VALUE));
            cursor.close(); // that's important too, otherwise you're gonna leak cursors
        }
        Log.d(TAG, "getMapKeyValueString key = " + key + ", value = " + value);
        return value;
    }

    public static boolean getMapKeyValueBoolean(Context context, String key) {
        boolean result = false;
        if (IMap.TRUE.equalsIgnoreCase(getMapKeyValueString(context, key))) {
            result = true;
        }
        Log.d(TAG, "getMapKeyValueBoolean value [" + key + "] = " + result);
        return result;
    }

    public static void setMapKeyValueBoolean(Context context, String key,
                                             boolean value) {
        Log.d(TAG, "setMapKeyValueBoolean value[" + key + "] = " + value);
        if (value) {
            insertUpdateMap(context, key, IMap.TRUE);
        } else {
            insertUpdateMap(context, key, IMap.FALSE);
        }
    }


//    public static void insertUpdateDeleteCart(Context context, Product product, boolean increase) {
//        Log.e(TAG, "==== insertUpdateDeleteCart product.productId = " + product.productId + " ====");
//        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(ICart.KEY_PRODUCT_ID, product.productId);
//
//        Gson gson = new Gson();
//
//        Cursor cursor = db.query(ICart.TABLE_CART, new String[]{ICart.KEY_ID, ICart.KEY_PRODUCT_JSON}, ICart.KEY_PRODUCT_ID + " = '" + product.productId + "'", null, null, null, null, null);
//        int index = -1;
//        if (cursor != null && cursor.getCount() > 0) { //if the row exist then return the id
//            cursor.moveToFirst();
//            index = cursor.getInt(cursor.getColumnIndex(IMap.KEY_ID));
////            cursor.close();
//        }
//
//        if (index == -1) {
//            // Only if product need to add
//            // else product is for delete from cart
//            if (increase) {
//                // new product
//                product.productQuantity = product.productQuantity == 0 ? 1 : product.productQuantity;
//                contentValues.put(ICart.KEY_PRODUCT_JSON, gson.toJson(product));
//                db.insert(ICart.TABLE_CART, null, contentValues);
//            }
//        } else {
//
//            if (cursor != null && cursor.getCount() > 0) {
////                cursor.moveToFirst();
//                Product cartProduct = null;
//                boolean isHandledInLoop = false;
//                boolean isCheckedWithAllProductCart = false;
//
//                do {
//                    Log.d(TAG,"============DO-WHILE START=================");
//                    try {
//                        cartProduct = gson.fromJson(new JSONObject(cursor.getString(cursor.getColumnIndex(ICart.KEY_PRODUCT_JSON))).toString(), Product.class);
//                        if (cartProduct != null) {
//                            // Product with option
//                            Log.e(TAG, "cartProduct.productHasOption = " + cartProduct.productHasOption);
//                            if (cartProduct.productHasOption) {
////                            Log.e(TAG,"original  product = " + product.productOptionArrayList.size());
////                            Log.e(TAG,"cart product = " + cartProduct.productOptionArrayList.size());
//                                // same product & different options list
//                                if (product.productOptionArrayList.size() != cartProduct.productOptionArrayList.size()) {
//                                    db.update(ICart.TABLE_CART, contentValues, ICart.KEY_ID + " = '" + index + "'", null);
//                                    isHandledInLoop = true;
//                                    break;
//                                }
//                                // same product
//                                // options list size are same
//                                // now compare them
//                                else {
//                                    int optionMatchCount = 0;
//                                    // Original product option
//                                    for (int optionProduct = 0; optionProduct < product.productOptionArrayList.size(); optionProduct++) {
//                                        // Cart product option
//                                        for (int optionCartProduct = 0; optionCartProduct < cartProduct.productOptionArrayList.size(); optionCartProduct++) {
//                                            // Original and Cart product option id match
//                                            Log.e(TAG, "[" + optionProduct + "]optionProduct = " + product.productOptionArrayList.get(optionProduct).optionId);
//                                            Log.e(TAG, "[" + optionCartProduct + "]optionCartProduct = " + cartProduct.productOptionArrayList.get(optionCartProduct).optionId);
//                                            if (product.productOptionArrayList.get(optionProduct).optionId
//                                                    .equals(cartProduct.productOptionArrayList.get(optionCartProduct).optionId)) {
//                                                // Original product option value
//                                                for (int valueProduct = 0; valueProduct < product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.size(); valueProduct++) {
//                                                    // Cart product option value
//                                                    for (int valueCartProduct = 0; valueCartProduct < cartProduct.productOptionArrayList.get(optionCartProduct).productOptionValueArrayList.size(); valueCartProduct++) {
//                                                        // Both value id got matched
//                                                        // and both are selected
//                                                        Log.e(TAG, "[" + optionProduct + "]optionProduct, [" + valueProduct + "]valueProduct = " + product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).optionValueId
//                                                                + "," + product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).selected);
//                                                        Log.e(TAG, "[" + optionCartProduct + "]optionCartProduct [" + valueCartProduct + "]valueCartProduct = " + cartProduct.productOptionArrayList.get(optionCartProduct).productOptionValueArrayList.get(valueCartProduct).optionValueId
//                                                                + "," + cartProduct.productOptionArrayList.get(optionCartProduct).productOptionValueArrayList.get(valueCartProduct).selected);
//
//                                                        if (product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).optionValueId
//                                                                .equals(cartProduct.productOptionArrayList.get(optionCartProduct).productOptionValueArrayList.get(valueCartProduct).optionValueId)
//                                                                && product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).selected
//                                                                && cartProduct.productOptionArrayList.get(optionCartProduct).productOptionValueArrayList.get(valueCartProduct).selected) {
//                                                            Log.e(TAG, "================MATCH==================");
//                                                            optionMatchCount++;
//                                                            break;
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//
//                                    // Same product
//                                    // same option
//                                    // same option value
//                                    // update product quantity
//                                    Log.e(TAG, "optionMatchCount = " + optionMatchCount);
//                                    Log.e(TAG, "product.productOptionArrayList.size() = " + product.productOptionArrayList.size());
//                                    if (optionMatchCount == product.productOptionArrayList.size()) {
//                                        // increase cart
//                                        if (increase) {
//                                            cartProduct.productQuantity++;
//                                            product.productQuantity = cartProduct.productQuantity;
//                                            Log.e(TAG, "1 cartProduct.productQuantity = " + cartProduct.productQuantity);
//                                            contentValues.put(ICart.KEY_PRODUCT_JSON, gson.toJson(product));
//                                            db.update(ICart.TABLE_CART, contentValues, ICart.KEY_ID + " = '" + index + "'", null);
//                                            isHandledInLoop = true;
//                                            break;
//                                        }
//                                        // decrease cart
//                                        else {
//                                            cartProduct.productQuantity--;
//                                            if (cartProduct.productQuantity <= 0) {
//                                                // Remove product from cart
//                                                product.productQuantity = 0;
//                                                contentValues.put(ICart.KEY_PRODUCT_JSON, gson.toJson(product));
//                                                Log.e(TAG, "2 cartProduct.productQuantity = " + cartProduct.productQuantity);
//                                                db.delete(ICart.TABLE_CART, ICart.KEY_PRODUCT_ID + " = '" + product.productId + "'", null);
//                                                isHandledInLoop = true;
//                                                break;
//                                            } else {
//                                                product.productQuantity = cartProduct.productQuantity;
//                                                Log.e(TAG, "3 cartProduct.productQuantity = " + cartProduct.productQuantity);
//                                                contentValues.put(ICart.KEY_PRODUCT_JSON, gson.toJson(product));
//                                                db.update(ICart.TABLE_CART, contentValues, ICart.KEY_ID + " = '" + index + "'", null);
//                                                isHandledInLoop = true;
//                                                break;
//                                            }
//                                        }
//                                    }
//                                    // Same product
//                                    // same option
//                                    // different option value
//                                    // add product as new
//                                    else {
//                                        // Only after comparing with all products
//                                        if (cursor.isLast()) {
//                                            Log.e(TAG, "cursor.isLast() " + cursor.isLast());
//                                            product.productQuantity = product.productQuantity == 0 ? 1 : product.productQuantity;
//                                            contentValues.put(ICart.KEY_PRODUCT_JSON, gson.toJson(product));
//                                            db.insert(ICart.TABLE_CART, null, contentValues);
//                                            break;
//                                        }
//                                    }
//                                }
//
//                            }
//                            // Product without option
//                            else {
//                                if (increase) {
//                                    cartProduct.productQuantity++;
//                                    product.productQuantity = cartProduct.productQuantity;
//                                    contentValues.put(ICart.KEY_PRODUCT_JSON, gson.toJson(product));
//                                    db.update(ICart.TABLE_CART, contentValues, ICart.KEY_ID + " = '" + index + "'", null);
//                                    isHandledInLoop = true;
//                                    break;
//                                }
//                                // decrease cart
//                                else {
//                                    cartProduct.productQuantity--;
//                                    if (product.productQuantity <= 0) {
//                                        // Remove product from cart
//                                        product.productQuantity = 0;
//                                        contentValues.put(ICart.KEY_PRODUCT_JSON, gson.toJson(product));
//                                        db.delete(ICart.TABLE_CART, ICart.KEY_PRODUCT_ID + " = '" + product.productId + "'", null);
//                                        isHandledInLoop = true;
//                                        break;
//                                    } else {
//                                        product.productQuantity = cartProduct.productQuantity;
//                                        contentValues.put(ICart.KEY_PRODUCT_JSON, gson.toJson(product));
//                                        db.update(ICart.TABLE_CART, contentValues, ICart.KEY_ID + " = '" + index + "'", null);
//                                        isHandledInLoop = true;
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    cartProduct = null;
//                    if (isHandledInLoop) {
//                        break;
//                    }
//                    Log.d(TAG,"============DO-WHILE END=================");
//                } while (cursor.moveToNext());
//
//                cursor.close(); // that's important too, otherwise you're gonna leak cursors
//            }
//        }
//        Log.e(TAG, "==== insertUpdateDeleteCart product.productId = " + product.productId + " ====");
//    }

    public static void insertUpdateDeleteCart(Context context, Product product, boolean increase) {
        Log.e(TAG, "==== insertUpdateDeleteCart product.productId = " + product.productId + " ====");
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Gson gson = new Gson();

        String query = "select * from " + ICart.TABLE_CART + " ";
        query += "WHERE " + ICart.KEY_PRODUCT_ID + " = '" + product.productId + "' ";
        for (int optionProduct = 0; optionProduct < product.productOptionArrayList.size(); optionProduct++) {
            for (int valueProduct = 0; valueProduct < product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.size(); valueProduct++) {
                if (product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).selected) {
                    switch (optionProduct) {
                        case ICart.OPTION_0:
                            query += "AND " + ICart.KEY_OPTION_ID_0 + " = '" + product.productOptionArrayList.get(optionProduct).optionId + "' ";
                            query += "AND " + ICart.KEY_OPTION_VALUE_ID_0 + " = '" + product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).optionValueId + "' ";
                            break;
                        case ICart.OPTION_1:
                            query += "AND " + ICart.KEY_OPTION_ID_1 + " = '" + product.productOptionArrayList.get(optionProduct).optionId + "' ";
                            query += "AND " + ICart.KEY_OPTION_VALUE_ID_1 + " = '" + product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).optionValueId + "' ";
                            break;
                        case ICart.OPTION_2:
                            query += "AND " + ICart.KEY_OPTION_ID_2 + " = '" + product.productOptionArrayList.get(optionProduct).optionId + "' ";
                            query += "AND " + ICart.KEY_OPTION_VALUE_ID_2 + " = '" + product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).optionValueId + "' ";
                            break;
                        case ICart.OPTION_3:
                            query += "AND " + ICart.KEY_OPTION_ID_3 + " = '" + product.productOptionArrayList.get(optionProduct).optionId + "' ";
                            query += "AND " + ICart.KEY_OPTION_VALUE_ID_3 + " = '" + product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).optionValueId + "' ";
                            break;
                        case ICart.OPTION_4:
                            query += "AND " + ICart.KEY_OPTION_ID_4 + " = '" + product.productOptionArrayList.get(optionProduct).optionId + "' ";
                            query += "AND " + ICart.KEY_OPTION_VALUE_ID_4 + " = '" + product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).optionValueId + "' ";
                            break;
                    }
                }
            }
        }
        Log.d(TAG, "query = " + query);
        Cursor cursor = db.rawQuery(query, null);
        int index = -1;
        if (cursor != null && cursor.getCount() > 0) { //if the row exist then return the id
            cursor.moveToFirst();
            index = cursor.getInt(cursor.getColumnIndex(IMap.KEY_ID));
//            cursor.close();
        }

        if (index == -1) {
            // Only if product need to add
            // else product is for delete from cart
            if (increase) {
                // new product
                product.productQuantity = product.productQuantity == 0 ? 1 : product.productQuantity;
                contentValues.put(ICart.KEY_PRODUCT_ID, product.productId);
                contentValues.put(ICart.KEY_PRODUCT_JSON, gson.toJson(product));

                for (int optionProduct = 0; optionProduct < product.productOptionArrayList.size(); optionProduct++) {
                    for (int valueProduct = 0; valueProduct < product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.size(); valueProduct++) {
                        if (product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).selected) {
                            switch (optionProduct) {
                                case ICart.OPTION_0:
                                    contentValues.put(ICart.KEY_OPTION_ID_0, product.productOptionArrayList.get(optionProduct).optionId);
                                    contentValues.put(ICart.KEY_OPTION_VALUE_ID_0, product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).optionValueId);
                                    break;
                                case ICart.OPTION_1:
                                    contentValues.put(ICart.KEY_OPTION_ID_1, product.productOptionArrayList.get(optionProduct).optionId);
                                    contentValues.put(ICart.KEY_OPTION_VALUE_ID_1, product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).optionValueId);
                                    break;
                                case ICart.OPTION_2:
                                    contentValues.put(ICart.KEY_OPTION_ID_2, product.productOptionArrayList.get(optionProduct).optionId);
                                    contentValues.put(ICart.KEY_OPTION_VALUE_ID_2, product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).optionValueId);
                                    break;
                                case ICart.OPTION_3:
                                    contentValues.put(ICart.KEY_OPTION_ID_3, product.productOptionArrayList.get(optionProduct).optionId);
                                    contentValues.put(ICart.KEY_OPTION_VALUE_ID_3, product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).optionValueId);
                                    break;
                                case ICart.OPTION_4:
                                    contentValues.put(ICart.KEY_OPTION_ID_4, product.productOptionArrayList.get(optionProduct).optionId);
                                    contentValues.put(ICart.KEY_OPTION_VALUE_ID_4, product.productOptionArrayList.get(optionProduct).productOptionValueArrayList.get(valueProduct).optionValueId);
                                    break;
                            }
                        }
                    }
                }
                db.insert(ICart.TABLE_CART, null, contentValues);
            }
        } else {
            if (cursor != null && cursor.getCount() > 0) {
//                cursor.moveToFirst();
                try {
                    Product cartProduct = gson.fromJson(new JSONObject(cursor.getString(cursor.getColumnIndex(ICart.KEY_PRODUCT_JSON))).toString(), Product.class);
                    // Increase cart
                    if (increase) {
                        cartProduct.productQuantity++;
                        product.productQuantity = cartProduct.productQuantity;
                        contentValues.put(ICart.KEY_PRODUCT_JSON, gson.toJson(product));
                        db.update(ICart.TABLE_CART, contentValues, ICart.KEY_ID + " = '" + index + "'", null);
                    }
                    // Decrease cart
                    else {
                        cartProduct.productQuantity--;
                        // Remove product from cart
                        if (cartProduct.productQuantity <= 0) {
                            db.delete(ICart.TABLE_CART, ICart.KEY_ID + " = '" + index + "'", null);
                        }
                        // Decrease cart count
                        else {

                            product.productQuantity = cartProduct.productQuantity;
                            contentValues.put(ICart.KEY_PRODUCT_JSON, gson.toJson(product));
                            db.update(ICart.TABLE_CART, contentValues, ICart.KEY_ID + " = '" + index + "'", null);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cursor.close(); // that's important too, otherwise you're gonna leak cursors
            }
        }
        Log.e(TAG, "==== insertUpdateDeleteCart product.productId = " + product.productId + " ====");
    }

    public static Product getProductFromCart(Context context, String ProductId) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + ICart.TABLE_CART
                + " WHERE " + ICart.KEY_PRODUCT_ID + " = " + ProductId, null);

        Gson gson = new Gson();
        Product product = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                try {
                    product = gson.fromJson(new JSONObject(cursor.getString(cursor.getColumnIndex(ICart.KEY_PRODUCT_JSON))).toString(), Product.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
            cursor.close(); // that's important too, otherwise you're gonna leak cursors
        }
        return product;
    }

    public static ArrayList<Product> getAllProductFromCart(Context context) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + ICart.TABLE_CART, null);

        Gson gson = new Gson();
        ArrayList<Product> productArrayList = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                try {
                    Product product = gson.fromJson(new JSONObject(cursor.getString(cursor.getColumnIndex(ICart.KEY_PRODUCT_JSON))).toString(), Product.class);
                    productArrayList.add(product);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
            cursor.close(); // that's important too, otherwise you're gonna leak cursors
        }
        return productArrayList;
    }

//    /**
//     * Clear offline data
//     *
//     * @param context
//     */
//    public static void clearOfflineData(Context context) {
//        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
//        db.delete(IDatabase.ICategory.TABLE_CATEGORY, null, null);
//        db.delete(IDatabase.ISubCategory.TABLE_SUB_CATEGORY, null, null);
//        db.delete(IDatabase.IProduct.TABLE_PRODUCT, null, null);
//    }
//
//    public static boolean insertLocations(Context context, ArrayList<MyLocation> myLocationArrayList, JSONArray categoryJArray) {
//        try {
//            if (myLocationArrayList != null && myLocationArrayList.size() > 0) {
//                Log.e(TAG, "myLocationArrayList.size() = " + myLocationArrayList.size());
//
//                SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
//
//                for (int i = 0; i < myLocationArrayList.size(); i++) {
//                    ContentValues contentValues = new ContentValues();
//                    contentValues.put(ILocation.KEY_LOCATION_ID, myLocationArrayList.get(i).id);
//                    contentValues.put(ILocation.KEY_LOCATION_NAME, myLocationArrayList.get(i).location);
//                    contentValues.put(ILocation.KEY_JSON, categoryJArray.getString(i));
//                    db.insert(ILocation.TABLE_LOCATION, null, contentValues);
//                }
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    public static String getLocations(Context context) {
//        JSONObject rootJObject = new JSONObject();
//        try {
//            SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
//            Cursor cursor = db.rawQuery("select * from " + ILocation.TABLE_LOCATION, null);
//
//            JSONArray dataJArray = new JSONArray();
//            // looping through all rows and adding to list
//            if (cursor != null && cursor.getCount() > 0) {
//                cursor.moveToFirst();
//                do {
//                    dataJArray.put(new JSONObject(cursor.getString(cursor.getColumnIndex(ICategory.KEY_JSON))));
//                } while (cursor.moveToNext());
//                cursor.close(); // that's important too, otherwise you're gonna leak cursors
//
//                rootJObject.put(IWebService.KEY_RES_SUCCESS, true);
//                rootJObject.put(IWebService.KEY_RES_MESSAGE, "location offline");
//            } else {
//                rootJObject.put(IWebService.KEY_RES_SUCCESS, false);
//                rootJObject.put(IWebService.KEY_RES_MESSAGE, "No offline data available for location");
//            }
//            rootJObject.put(IWebService.KEY_RES_DATA, dataJArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            try {
//                rootJObject.put(IWebService.KEY_RES_SUCCESS, false);
//                rootJObject.put(IWebService.KEY_RES_MESSAGE, "No offline data available for location");
//            } catch (JSONException e1) {
//                e1.printStackTrace();
//            }
//        }
//
//        return rootJObject.toString();
//    }
//
//    public static boolean insertCategories(Context context, ArrayList<Category> categoryArrayList, JSONArray categoryJArray) {
//        try {
//            if (categoryArrayList != null && categoryArrayList.size() > 0) {
//                Log.e(TAG, "categoryArrayList.size() = " + categoryArrayList.size());
//
//                SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
//
//                for (int i = 0; i < categoryArrayList.size(); i++) {
//                    ContentValues contentValues = new ContentValues();
//                    contentValues.put(ICategory.KEY_CATEGORY_ID, categoryArrayList.get(i).id);
//                    contentValues.put(ICategory.KEY_NAME, categoryArrayList.get(i).name);
//                    contentValues.put(ICategory.KEY_ICON, categoryArrayList.get(i).icon);
//                    contentValues.put(ICategory.KEY_TYPE, categoryArrayList.get(i).type);
//                    contentValues.put(ICategory.KEY_WEB_LINK, categoryArrayList.get(i).web_link);
//                    contentValues.put(ICategory.KEY_JSON, categoryJArray.getString(i));
//                    db.insert(ICategory.TABLE_CATEGORY, null, contentValues);
//                }
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    public static String getCategories(Context context) {
//        JSONObject rootJObject = new JSONObject();
//        JSONObject dataJObject = new JSONObject();
//        JSONArray categoryJArray = new JSONArray();
//        try {
//            SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
//            Cursor cursor = db.rawQuery("select * from " + ICategory.TABLE_CATEGORY, null);
//
//            // looping through all rows and adding to list
//            if (cursor != null && cursor.getCount() > 0) {
//                cursor.moveToFirst();
//                do {
//                    //                Contact contact = new Contact();
//                    //                contact.setID(Integer.parseInt(cursor.getString(0)));
//                    //                contact.setName(cursor.getString(1));
//                    //                contact.setPhoneNumber(cursor.getString(2));
//                    //                // Adding contact to list
//                    //                contactList.add(contact);
//                    categoryJArray.put(new JSONObject(cursor.getString(cursor.getColumnIndex(ICategory.KEY_JSON))));
//                } while (cursor.moveToNext());
//                cursor.close(); // that's important too, otherwise you're gonna leak cursors
//
//                rootJObject.put(IWebService.KEY_RES_SUCCESS, true);
//                rootJObject.put(IWebService.KEY_RES_MESSAGE, "category offline");
//            } else {
//                rootJObject.put(IWebService.KEY_RES_SUCCESS, false);
//                rootJObject.put(IWebService.KEY_RES_MESSAGE, "No offline data available for category");
//            }
//            dataJObject.put(IWebService.KEY_RES_CATEGORY, categoryJArray);
//            rootJObject.put(IWebService.KEY_RES_DATA, dataJObject);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            try {
//                rootJObject.put(IWebService.KEY_RES_SUCCESS, false);
//                rootJObject.put(IWebService.KEY_RES_MESSAGE, "No offline data available for category");
//                dataJObject.put(IWebService.KEY_RES_CATEGORY, new JSONArray());
//                rootJObject.put(IWebService.KEY_RES_DATA, dataJObject);
//            } catch (JSONException e1) {
//                e1.printStackTrace();
//            }
//        }
//
//        return rootJObject.toString();
//    }
//
//    public static boolean insertSubCategories(Context context, ArrayList<SubCategory> productOptionValueArrayList, JSONArray subCategoryJArray) {
//        try {
//            if (productOptionValueArrayList != null && productOptionValueArrayList.size() > 0) {
//                Log.e(TAG, "subCategories.size() = " + productOptionValueArrayList.size());
//
//                SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
//
//                for (int i = 0; i < productOptionValueArrayList.size(); i++) {
//                    ContentValues contentValues = new ContentValues();
//                    contentValues.put(ISubCategory.KEY_SUB_CATEGORY_ID, productOptionValueArrayList.get(i).id);
//                    contentValues.put(ISubCategory.KEY_CATEGORY_ID, productOptionValueArrayList.get(i).categoryId);
//                    contentValues.put(ISubCategory.KEY_NAME, productOptionValueArrayList.get(i).name);
//                    contentValues.put(ISubCategory.KEY_JSON, subCategoryJArray.getString(i));
//                    db.insert(ISubCategory.TABLE_SUB_CATEGORY, null, contentValues);
//                }
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    public static boolean insertProduct(Context context, ArrayList<Product> productArrayList, JSONArray productJArray) {
//        try {
//            if (productArrayList != null
//                    && productArrayList.size() > 0) {
//                Log.e(TAG, "productArrayList.size() = "
//                        + productArrayList.size());
//                SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
//
//                for (int i = 0; i < productArrayList.size(); i++) {
//                    ContentValues contentValues = new ContentValues();
//                    contentValues.put(IProduct.KEY_PRODUCT_ID, productArrayList.get(i).productId);
//                    contentValues.put(IProduct.KEY_CATEGORY_ID, productArrayList.get(i).categoryId);
//                    contentValues.put(IProduct.KEY_SUB_CATEGORY_ID, productArrayList.get(i).sub_cat_id);
//                    contentValues.put(IProduct.KEY_NAME, productArrayList.get(i).name);
//                    contentValues.put(IProduct.KEY_LOCATION, productArrayList.get(i).location);
//                    contentValues.put(IProduct.KEY_JSON, productJArray.getString(i));
//
//                    db.insert(IProduct.TABLE_PRODUCT, null, contentValues);
//                }
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    public static String getProducts(Context context, String filterParams) {
//        JSONObject rootJObject = new JSONObject();
//        JSONObject dataJObject = new JSONObject();
//        int pageCount = -1;
//
//        try {
//            JSONObject filterJObject = new JSONObject(filterParams);
//
//            SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
//
//            /**
//             * For sub category compare Category id
//             */
//            if (filterJObject.has(IWebService.KEY_REQ_ID) && !Utils.isNullOrEmpty(filterJObject.getString(IWebService.KEY_REQ_ID))) {
//                JSONArray subCategoryJArray = new JSONArray();
//                String subCategoryQuery = "SELECT " + ISubCategory.KEY_JSON
//                        + " FROM " + ISubCategory.TABLE_SUB_CATEGORY
//                        + " WHERE " + ISubCategory.KEY_CATEGORY_ID + " = " + filterJObject.getString(IWebService.KEY_REQ_ID);
//                Cursor subCategoryCursor = db.rawQuery(subCategoryQuery, null);
//                if (subCategoryCursor != null && subCategoryCursor.getCount() > 0) {
//                    subCategoryCursor.moveToFirst();
//                    do {
//                        subCategoryJArray.put(new JSONObject(subCategoryCursor.getString(subCategoryCursor.getColumnIndex(ICategory.KEY_JSON))));
//                    } while (subCategoryCursor.moveToNext());
//                    subCategoryCursor.close();
//                    dataJObject.put(IWebService.KEY_RES_SUBCATEGORY, subCategoryJArray);
//                }
//            }
//
//            /**
//             * For product
//             */
//            String productQuery = "SELECT " + IProduct.KEY_JSON
//                    + " FROM " + IProduct.TABLE_PRODUCT + " WHERE ";
//            ArrayList<String> componentQuery = new ArrayList<>();
//
//            // Location id
//            // assuming that id of ALL will be 0
//            if (filterJObject.has(IWebService.KEY_REQ_LOCATION_ID)
//                    && !Utils.isNullOrEmpty(filterJObject.getString(IWebService.KEY_REQ_LOCATION_ID))
//                    && !filterJObject.getString(IWebService.KEY_REQ_LOCATION_ID).equals("0")) {
//                componentQuery.add(IProduct.KEY_LOCATION + " = " + filterJObject.getString(IWebService.KEY_REQ_LOCATION_ID));
//            }
//
//            // Category id
//            if (filterJObject.has(IWebService.KEY_REQ_ID) && !Utils.isNullOrEmpty(filterJObject.getString(IWebService.KEY_REQ_ID))) {
//                componentQuery.add(IProduct.KEY_CATEGORY_ID + " = " + filterJObject.getString(IWebService.KEY_REQ_ID));
//            }
//
//            // Sub Category id
//            if (filterJObject.has(IWebService.KEY_REQ_SUBCATEGORY)) {
//                String subCategoryQuery = "(";
//                JSONArray subCategoryJArray = filterJObject.getJSONArray(IWebService.KEY_REQ_SUBCATEGORY);
//                for (int i = 0; i < subCategoryJArray.length(); i++) {
//                    if (i == 0) {
//                        subCategoryQuery += "(',' || " + IProduct.KEY_SUB_CATEGORY_ID + " || ',') LIKE '%," + subCategoryJArray.get(i) + ",%'";
//                    } else {
//                        subCategoryQuery += "OR (',' || " + IProduct.KEY_SUB_CATEGORY_ID + " || ',') LIKE '%," + subCategoryJArray.get(i) + ",%'";
//                    }
//                }
//                subCategoryQuery += ")";
//                componentQuery.add(subCategoryQuery);
//            }
//
//            // keyword
//            if (filterJObject.has(IWebService.KEY_REQ_KEYWORD) && !Utils.isNullOrEmpty(filterJObject.getString(IWebService.KEY_REQ_KEYWORD))) {
//                componentQuery.add(IProduct.KEY_NAME + " LIKE '%" + filterJObject.getString(IWebService.KEY_REQ_KEYWORD) + "%'");
//            }
//
//            for (int i = 0; i < componentQuery.size(); i++) {
//                if (i == 0) {
//                    productQuery += componentQuery.get(i);
//                } else {
//                    productQuery += " AND " + componentQuery.get(i);
//                }
//            }
//            productQuery += " ORDER BY(" + IProduct.KEY_NAME + ") ASC";
//
////            String pagination = "";
//            int limit = 10;
//            if (filterJObject.has(IWebService.KEY_REQ_PAGE_NO)) {
//                pageCount = filterJObject.getInt(IWebService.KEY_REQ_PAGE_NO);
//                if (pageCount == 1) {
//                    productQuery += " LIMIT " + limit + " OFFSET 0";
//                } else if (pageCount > 1) {
//                    productQuery += " LIMIT " + limit + " OFFSET " + ((pageCount - 1) * limit);
//                }
//            }
//
//            Log.d(TAG, "productQuery = " + productQuery);
//
//            Cursor productCursor = db.rawQuery(productQuery, null);
//            JSONArray productJArray = new JSONArray();
//            // looping through all rows and adding to list
//            if (productCursor != null && productCursor.getCount() > 0) {
//                productCursor.moveToFirst();
//                do {
//                    productJArray.put(new JSONObject(productCursor.getString(productCursor.getColumnIndex(ICategory.KEY_JSON))));
//                } while (productCursor.moveToNext());
//                productCursor.close(); // that's important too, otherwise you're gonna leak cursors
//
//                dataJObject.put(IWebService.KEY_RES_PRODUCT, productJArray);
//                rootJObject.put(IWebService.KEY_RES_SUCCESS, true);
//                rootJObject.put(IWebService.KEY_RES_MESSAGE, "product offline");
//            } else {
//                dataJObject.put(IWebService.KEY_RES_PRODUCT, new JSONArray());
//                rootJObject.put(IWebService.KEY_RES_SUCCESS, false);
//                if (pageCount > 1) {
//                    rootJObject.put(IWebService.KEY_RES_MESSAGE, "No more offline data available for this product");
//                } else {
//                    rootJObject.put(IWebService.KEY_RES_MESSAGE, "No offline data available for this product");
//                }
//            }
//            if (!dataJObject.has(IWebService.KEY_RES_SUBCATEGORY)) {
//                dataJObject.put(IWebService.KEY_RES_SUBCATEGORY, new JSONArray());
//            }
//            rootJObject.put(IWebService.KEY_RES_DATA, dataJObject);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            try {
//                dataJObject.put(IWebService.KEY_RES_SUBCATEGORY, new JSONArray());
//                dataJObject.put(IWebService.KEY_RES_PRODUCT, new JSONArray());
//                rootJObject.put(IWebService.KEY_RES_DATA, dataJObject);
//                rootJObject.put(IWebService.KEY_RES_SUCCESS, false);
//                rootJObject.put(IWebService.KEY_RES_MESSAGE, "No offline data available for this product");
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
//        return rootJObject.toString();
//    }
//
//
//    public static String getFavouriteProducts(Context context, String filterParams) {
//        JSONObject rootJObject = new JSONObject();
//        JSONObject dataJObject = new JSONObject();
//        int pageCount = -1;
//
//        try {
//            JSONObject filterJObject = new JSONObject(filterParams);
//
//            SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
//
//            /**
//             * For sub category compare Category id
//             */
//            String favProduct = getMapKeyValueString(context, IMap.KEY_FAVOURITE);
//            if (!Utils.isNullOrEmpty(favProduct)) {
//                JSONArray favProductJArray = new JSONArray(favProduct);
//                if (favProductJArray.length() > 0) {
//                    /**
//                     * For product
//                     */
//                    String productQuery = "SELECT " + IProduct.KEY_JSON
//                            + " FROM " + IProduct.TABLE_PRODUCT + " WHERE ";
//                    ArrayList<String> componentQuery = new ArrayList<>();
//
//                    String subCategoryQuery = IProduct.KEY_PRODUCT_ID + " IN (";
//                    for (int i = 0; i < favProductJArray.length(); i++) {
//                        if (i < favProductJArray.length() - 1) {
//                            subCategoryQuery += "'" + favProductJArray.get(i) + "', ";
//                        } else {
//                            subCategoryQuery += "'" + favProductJArray.get(i) + "'";
//                        }
//                    }
//                    subCategoryQuery += ")";
//                    componentQuery.add(subCategoryQuery);
//
//                    for (int i = 0; i < componentQuery.size(); i++) {
//                        if (i == 0) {
//                            productQuery += componentQuery.get(i);
//                        } else {
//                            productQuery += " AND " + componentQuery.get(i);
//                        }
//                    }
//                    productQuery += " ORDER BY(" + IProduct.KEY_NAME + ") ASC";
//
//                    int limit = 10;
//                    if (filterJObject.has(IWebService.KEY_REQ_PAGE_NO)) {
//                        pageCount = filterJObject.getInt(IWebService.KEY_REQ_PAGE_NO);
//                        if (pageCount == 1) {
//                            productQuery += " LIMIT " + limit + " OFFSET 0";
//                        } else if (pageCount > 1) {
//                            productQuery += " LIMIT " + limit + " OFFSET " + ((pageCount - 1) * limit);
//                        }
//                    }
//
//                    Log.d(TAG, "productQuery = " + productQuery);
//
//                    Cursor productCursor = db.rawQuery(productQuery, null);
//                    JSONArray productJArray = new JSONArray();
//                    // looping through all rows and adding to list
//                    if (productCursor != null && productCursor.getCount() > 0) {
//                        productCursor.moveToFirst();
//                        do {
//                            productJArray.put(new JSONObject(productCursor.getString(productCursor.getColumnIndex(ICategory.KEY_JSON))));
//                        } while (productCursor.moveToNext());
//                        productCursor.close(); // that's important too, otherwise you're gonna leak cursors
//
//                        dataJObject.put(IWebService.KEY_RES_PRODUCT, productJArray);
//                        rootJObject.put(IWebService.KEY_RES_DATA, dataJObject);
//                        rootJObject.put(IWebService.KEY_RES_SUCCESS, true);
//                        rootJObject.put(IWebService.KEY_RES_MESSAGE, "my stuff offline");
//                    } else {
//                        throw new Exception("no data found");
//                    }
//                } else {
//                    throw new Exception("favourite array is empty");
//                }
//            } else {
//                throw new Exception("favourite array is null");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                dataJObject.put(IWebService.KEY_RES_PRODUCT, new JSONArray());
//                rootJObject.put(IWebService.KEY_RES_DATA, dataJObject);
//                rootJObject.put(IWebService.KEY_RES_SUCCESS, false);
//                if (pageCount > 1) {
//                    rootJObject.put(IWebService.KEY_RES_MESSAGE, "No more offline data available for my stuff");
//                } else {
//                    rootJObject.put(IWebService.KEY_RES_MESSAGE, "No offline data available for my stuff");
//                }
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
//        return rootJObject.toString();
//    }
//
//    public static String getProductDetail(Context context, String filterParams) {
//        JSONObject rootJObject = new JSONObject();
//        JSONObject dataJObject = new JSONObject();
//
//        try {
//            JSONObject filterJObject = new JSONObject(filterParams);
//
//            SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
//
//            /**
//             * For sub category compare Category id
//             */
//            if (filterJObject.has(IWebService.KEY_REQ_PRODUCTID)
//                    && !Utils.isNullOrEmpty(filterJObject.getString(IWebService.KEY_REQ_PRODUCTID))) {
//                String subCategoryQuery = "SELECT " + IProduct.KEY_JSON
//                        + " FROM " + IProduct.TABLE_PRODUCT
//                        + " WHERE " + IProduct.KEY_PRODUCT_ID + " = " + filterJObject.getString(IWebService.KEY_REQ_PRODUCTID);
//                Cursor productDetailCursor = db.rawQuery(subCategoryQuery, null);
//                if (productDetailCursor != null && productDetailCursor.getCount() > 0) {
//                    productDetailCursor.moveToFirst();
//                    dataJObject = new JSONObject(productDetailCursor.getString(productDetailCursor.getColumnIndex(ICategory.KEY_JSON)));
//                    rootJObject.put(IWebService.KEY_RES_DATA, dataJObject);
//                    rootJObject.put(IWebService.KEY_RES_SUCCESS, true);
//                    rootJObject.put(IWebService.KEY_RES_MESSAGE, "product detail offline");
//                    productDetailCursor.close();
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } finally {
//            if (!rootJObject.has(IWebService.KEY_RES_DATA)) {
//                try {
//                    rootJObject.put(IWebService.KEY_RES_DATA, dataJObject);
//                    rootJObject.put(IWebService.KEY_RES_SUCCESS, false);
//                    rootJObject.put(IWebService.KEY_RES_MESSAGE, "product detail offline not available");
//                } catch (JSONException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//        return rootJObject.toString();
//    }
//
//    public static String getFavourites(Context context) {
//        JSONObject rootJObject = new JSONObject();
//        JSONObject dataJObject = new JSONObject();
//        try {
//            String favProduct = getMapKeyValueString(context, IMap.KEY_FAVOURITE);
//
//            // looping through all rows and adding to list
//            if (!Utils.isNullOrEmpty(favProduct)) {
//                rootJObject.put(IWebService.KEY_RES_SUCCESS, true);
//                rootJObject.put(IWebService.KEY_RES_MESSAGE, "category offline");
//                dataJObject.put(IWebService.KEY_RES_PRODUCT_ID, new JSONArray(favProduct));
//                rootJObject.put(IWebService.KEY_RES_DATA, dataJObject);
//            } else {
//                throw new Exception("favourite array is null");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                rootJObject.put(IWebService.KEY_RES_SUCCESS, false);
//                rootJObject.put(IWebService.KEY_RES_MESSAGE, "No offline data available for favourites");
//                rootJObject.put(IWebService.KEY_RES_DATA, dataJObject);
//            } catch (JSONException e1) {
//                e1.printStackTrace();
//            }
//        }
//
//        return rootJObject.toString();
//    }

    public static void exportDB(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            FileChannel source;
            FileChannel destination;
            String currentDBPath = "/data/" + "com.shoppin.customer" + "/databases/" + IDatabase.DATABASE_NAME;
            String backupDBPath = IDatabase.DATABASE_NAME;
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(context, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
