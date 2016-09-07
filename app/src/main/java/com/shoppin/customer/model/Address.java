package com.shoppin.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ubuntu on 25/8/16.
 */

public class Address implements Parcelable {
    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
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

    public Address() {
    }

    protected Address(Parcel in) {
        this.addressId = in.readString();
        this.name = in.readString();
        this.phoneNumber = in.readString();
        this.street = in.readString();
        this.suburbId = in.readString();
        this.suburbName = in.readString();
        this.postCode = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.addressId);
        dest.writeString(this.name);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.street);
        dest.writeString(this.suburbId);
        dest.writeString(this.suburbName);
        dest.writeString(this.postCode);
    }
}
