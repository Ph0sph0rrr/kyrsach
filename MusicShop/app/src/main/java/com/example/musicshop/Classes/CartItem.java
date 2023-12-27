package com.example.musicshop.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable {
    private long itemId;
    private String name;
    private double price;
    private int quantity;

    public CartItem(long itemId, String name, double price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.quantity = 1;
    }

    protected CartItem(Parcel in) {
        itemId = in.readLong();
        name = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    public long getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        if (quantity > 1) {
            quantity--;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(itemId);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(quantity);
    }
}