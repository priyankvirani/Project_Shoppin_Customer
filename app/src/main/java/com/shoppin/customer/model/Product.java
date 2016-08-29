package com.shoppin.customer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ubuntu on 17/8/16.
 */

public class Product implements Serializable {
    public String product_id;

    public String product_name;
    @SerializedName("product_variants")
    public ArrayList<ProductVariant> productVariantArrayList;
}
