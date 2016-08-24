package com.shoppin.customer.model;

import java.io.Serializable;

/**
 * Created by ubuntu on 24/8/16.
 */

public class Store implements Serializable {

    public String store_id;
    public String store_name;
    public String store_address;

    public Store(String store_id, String store_name, String store_address) {
        this.store_id = store_id;
        this.store_name = store_name;
        this.store_address = store_address;
    }
}
