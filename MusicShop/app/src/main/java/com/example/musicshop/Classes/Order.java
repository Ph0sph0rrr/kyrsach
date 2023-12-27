package com.example.musicshop.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Order implements Parcelable {
    private String orderId;
    private String phoneNumber;
    private List<CartItem> cartItems;

    public Order(String orderId, String phoneNumber, List<CartItem> cartItems) {
        this.orderId = orderId;
        this.phoneNumber = phoneNumber;
        this.cartItems = new ArrayList<>(cartItems);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
    protected Order(Parcel in) {
        orderId = in.readString();
        phoneNumber = in.readString();
        cartItems = in.createTypedArrayList(CartItem.CREATOR);
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(phoneNumber);
        dest.writeTypedList(cartItems);
    }

    @Override
    public String toString() {
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Номер телефона: ").append(phoneNumber).append("\n");
        orderDetails.append("Список блюд: \n");
        for (CartItem item : cartItems) {
            orderDetails.append("- ").append(item.getName()).append("\n");
        }
        return orderDetails.toString();
    }
}