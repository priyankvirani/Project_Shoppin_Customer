package com.shoppin.customer.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ubuntu on 11/8/16.
 */

public class CheckoutDate {
    boolean isSelected;
    String date;
    @SerializedName("slot_list")
    ArrayList<CheckoutTime> checkoutTimesArrayList;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public ArrayList<CheckoutTime> getCheckoutTimesArrayList() {
        return checkoutTimesArrayList;
    }

    public void setCheckoutTimesArrayList(ArrayList<CheckoutTime> checkoutTimesArrayList) {
        this.checkoutTimesArrayList = checkoutTimesArrayList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
