package com.example.vivek.signature1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Customer implements Parcelable {

    private String customerID;
    private String tax;
    private String name;
    private String email;
    private String address;
    private String city;
    private String code;
    private String country;
    private String phone;
    private String mobile;

    public Customer(){}

    public Customer(String customerID, String tax, String name, String email, String address, String city, String code, String country, String phone, String mobile) {
        this.customerID = customerID;
        this.tax = tax;
        this.name = name;
        this.email = email;
        this.address = address;
        this.city = city;
        this.code = code;
        this.country = country;
        this.phone = phone;
        this.mobile = mobile;
    }

    protected Customer(Parcel in) {
        customerID = in.readString();
        tax = in.readString();
        name = in.readString();
        email = in.readString();
        address = in.readString();
        city = in.readString();
        code = in.readString();
        country = in.readString();
        phone = in.readString();
        mobile = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public String getCustomerID() {
        return customerID;
    }

    public String getTax() {
        return tax;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public String getMobile() {
        return mobile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(customerID);
        dest.writeString(tax);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(code);
        dest.writeString(country);
        dest.writeString(phone);
        dest.writeString(mobile);
    }
}
