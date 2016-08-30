package com.shoppin.customer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ubuntu on 25/8/16.
 */

public class Address implements Serializable {
    @SerializedName("customer_address_id")
    public String addressId;

    @SerializedName("customer_name")
    public String name;

    @SerializedName("customer_mobile")
    public String phoneNumber;

    @SerializedName("customer_address_line1")
    public String street;

    @SerializedName("customer_suburb_id")
    public String suburbId;

    @SerializedName("customer_suburb_name")
    public String suburbName;


    @SerializedName("customer_zip")
    public String postCode;

//    public Address(String name, String phoneNumber, String street, String suburb, String postCode) {
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        this.street = street;
//        this.suburbId = suburb;
//        this.postCode = postCode;
//    }
}
