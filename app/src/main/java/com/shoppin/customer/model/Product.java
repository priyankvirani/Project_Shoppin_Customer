package com.shoppin.customer.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.shoppin.customer.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ubuntu on 17/8/16.
 */

public class Product implements Serializable {
    private static final String TAG = Product.class.getSimpleName();

    @SerializedName("product_id")
    public String productId;
    @SerializedName("name")
    public String productName;
    @SerializedName("images")
    public ArrayList<String> productImages;
    @SerializedName("price")
    public double productPrice;
    @SerializedName("saleprice")
    public double productSalePrice;
    @SerializedName("description")
    public String productDescription;
    @SerializedName("has_option")
    public boolean productHasOption = false;
    @SerializedName("option_list")
    public ArrayList<ProductOption> productOptionArrayList;
    public int productQuantity;

    //    private double priceAsPerSelection = 0;
    private String selectedOptions = null;

    public double getPriceAsPerSelection() {
        double priceAsPerSelection = 0;
//        if (priceAsPerSelection == 0) {
        if (productHasOption) {
            double salePrice = 0;
            salePrice += productSalePrice;
            Log.d(TAG, "price productSalePrice = " + productSalePrice);

            int optionSize = 0;
            if (productHasOption && productOptionArrayList != null && productOptionArrayList.size() > 0) {
                optionSize = productOptionArrayList.size();
            }

            for (int iOption = 0; iOption < optionSize; iOption++) {
                for (int jOptionValue = 0;
                     jOptionValue < productOptionArrayList.get(iOption).productOptionValueArrayList.size();
                     jOptionValue++) {
                    if (productOptionArrayList.get(iOption).productOptionValueArrayList.get(jOptionValue).selected) {
                        salePrice += productOptionArrayList.get(iOption).productOptionValueArrayList.get(jOptionValue).optionValuePrice;
                        Log.d(TAG, "price optionValueName = " + productOptionArrayList.get(iOption).productOptionValueArrayList.get(jOptionValue).optionValueName);
                        Log.d(TAG, "price optionValuePrice = " + productOptionArrayList.get(iOption).productOptionValueArrayList.get(jOptionValue).optionValuePrice);
                        break;
                    }
                }
            }
            priceAsPerSelection = salePrice;

        } else {
            priceAsPerSelection = productSalePrice;
        }
        Log.d(TAG, "price  priceAsPerSelection = " + priceAsPerSelection);
        // multiply sale price and quantity
        Log.d(TAG, "price  productQuantity = " + productQuantity);
        priceAsPerSelection = priceAsPerSelection * (productQuantity == 0 ? 1 : productQuantity);
//        }

        return priceAsPerSelection;
    }

    public String getSelectedOptions() {
//        Log.d(TAG, " Utils.isNullOrEmpty(selectedOptions) = " + Utils.isNullOrEmpty(selectedOptions) + ", " + selectedOptions);
//        Log.d(TAG, " productHasOption = " + productHasOption);
        if (Utils.isNullOrEmpty(selectedOptions) && productHasOption) {
            String tmpSelectedOptions = "";
            int optionSize = 0;
            if (productHasOption && productOptionArrayList != null && productOptionArrayList.size() > 0) {
                optionSize = productOptionArrayList.size();
            }
            for (int iOption = 0; iOption < optionSize; iOption++) {
                tmpSelectedOptions += productOptionArrayList.get(iOption).optionName + " : ";
                for (int jOptionValue = 0;
                     jOptionValue < productOptionArrayList.get(iOption).productOptionValueArrayList.size();
                     jOptionValue++) {
                    if (productOptionArrayList.get(iOption).productOptionValueArrayList.get(jOptionValue).selected) {
                        if (iOption == productOptionArrayList.size() - 1) {
                            tmpSelectedOptions += productOptionArrayList.get(iOption).productOptionValueArrayList.get(jOptionValue).optionValueName;
                        } else {
                            tmpSelectedOptions += productOptionArrayList.get(iOption).productOptionValueArrayList.get(jOptionValue).optionValueName + " \n ";
                        }

//                        Log.d(TAG, "price optionValueName = " + productOptionArrayList.get(iOption).productOptionValueArrayList.get(jOptionValue).optionValueName);
//                        Log.d(TAG, "price optionValuePrice = " + productOptionArrayList.get(iOption).productOptionValueArrayList.get(jOptionValue).optionValuePrice);
                        break;
                    }
                }
            }
            selectedOptions = tmpSelectedOptions;
        }
        Log.d(TAG, "selectedOptions = " + selectedOptions);
        return selectedOptions;
    }
}
