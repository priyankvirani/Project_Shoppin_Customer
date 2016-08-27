package com.shoppin.customer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ubuntu on 25/8/16.
 */

public class Address implements Serializable {

    @SerializedName("name")
    public String name;

    @SerializedName("phoneNumber")
    public String phoneNumber;

    @SerializedName("street")
    public String street;

    @SerializedName("suburb")
    public String suburb;


    @SerializedName("postCode")
    public String postCode;

    public Address(String name, String phoneNumber, String street, String suburb, String postCode) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.suburb = suburb;
        this.postCode = postCode;
    }
}
