package com.shoppin.customer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ubuntu on 24/8/16.
 */

public class Store implements Serializable {

    @SerializedName("store_id")
    public String storeId;
    @SerializedName("store_name")
    public String storeName;
    @SerializedName("store_address")
    public String storeAddress;

    public boolean isSelected;

    public Store(String store_id, String store_name, String store_address) {
        this.storeId = store_id;
        this.storeName = store_name;
        this.storeAddress = store_address;
    }
}
