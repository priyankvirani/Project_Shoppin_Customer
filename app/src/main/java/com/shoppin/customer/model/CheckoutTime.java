package com.shoppin.customer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ubuntu on 11/8/16.
 */

public class CheckoutTime {
    @SerializedName("value")
    String time;
    @SerializedName("slot_id")
    String date;
    boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
