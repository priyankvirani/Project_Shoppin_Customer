package com.shoppin.customer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ubuntu on 17/8/16.
 */

public class ProductOptionValue implements Serializable {
    @SerializedName("option_value_id")
    public String optionValueId;

    @SerializedName("name")
    public String optionValueName;

    @SerializedName("price")
    public double optionValuePrice;

    public boolean isSelected;
}
