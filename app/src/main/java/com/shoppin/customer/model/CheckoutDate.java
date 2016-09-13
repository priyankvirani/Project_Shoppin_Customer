package com.shoppin.customer.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ubuntu on 11/8/16.
 */

public class CheckoutDate {
    public boolean isSelected;

    public String date;

    @SerializedName("slot_list")
    public ArrayList<CheckoutTime> checkoutTimesArrayList;
}
