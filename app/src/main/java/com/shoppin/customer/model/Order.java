package com.shoppin.customer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ubuntu on 12/9/16.
 */

public class Order {
    @SerializedName("order_number")
    public String orderNumber;

    public String status;

    @SerializedName("ordered_on")
    public String orderDateTime;

    public String total;

    @SerializedName("status_label")
    public String statusLabel;

    @SerializedName("item_count")
    public String itemCount;
}
