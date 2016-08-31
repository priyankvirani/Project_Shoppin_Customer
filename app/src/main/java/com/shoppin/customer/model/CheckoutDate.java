package com.shoppin.customer.model;

import java.util.ArrayList;

/**
 * Created by ubuntu on 11/8/16.
 */

public class CheckoutDate {
    private boolean isSelected;
    private String Date;
    private ArrayList<CheckoutTime> checkoutTimesArrayList;

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
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
