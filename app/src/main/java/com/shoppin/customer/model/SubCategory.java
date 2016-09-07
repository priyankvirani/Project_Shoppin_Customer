package com.shoppin.customer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ubuntu on 4/8/16.
 */

public class SubCategory implements Serializable {
    @SerializedName("subcategory_id")
    public String subcategoryId;

    @SerializedName("subcategory_name")
    public String subcategoryName;

    @SerializedName("image")
    public String subcategoryImage;
}
