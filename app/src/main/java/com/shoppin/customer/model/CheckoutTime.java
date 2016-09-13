package com.shoppin.customer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ubuntu on 11/8/16.
 */

public class CheckoutTime {
    @SerializedName("value")
    public String time;

    @SerializedName("slot_id")
    public  String slotId;

    public boolean isSelected;
}
