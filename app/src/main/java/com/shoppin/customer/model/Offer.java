package com.shoppin.customer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ubuntu on 16/8/16.
 */

public class Offer implements Serializable {

    public String[] offer_list;

    public String offer_detail;

    public Offer(String offer_detail) {
        this.offer_detail = offer_detail;
    }
}
