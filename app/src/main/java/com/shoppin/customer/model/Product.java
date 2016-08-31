package com.shoppin.customer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ubuntu on 17/8/16.
 */

public class Product implements Serializable {
    @SerializedName("product_id")
    public String productId;
    @SerializedName("name")
    public String productName;
    @SerializedName("images")
    public ArrayList<String> productImages;
    @SerializedName("price")
    public double productPrice;
    @SerializedName("saleprice")
    public double productSalePrice;
    @SerializedName("description")
    public String productDescription;
    @SerializedName("has_option")
    public boolean productHasOption;
    @SerializedName("option_list")
    public ArrayList<ProductOption> productOptionArrayList;
    public int productQuantity;
}
