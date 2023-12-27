package com.example.musicshop.Activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicshop.Classes.CartItem;
import com.example.musicshop.Classes.Order;
import com.example.musicshop.Adapters.OrderHistoryManager;
import com.example.musicshop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        List<Order> orderHistoryList = OrderHistoryManager.getInstance().getOrderHistory();

        displayOrderHistory(orderHistoryList);
    }

    private void displayOrderHistory(List<Order> orderHistoryList) {
        ArrayAdapter<String> orderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, convertOrdersToStrings(orderHistoryList));
        ListView orderListView = findViewById(R.id.orderListView);
        orderListView.setAdapter(orderAdapter);
    }

    private List<String> convertOrdersToStrings(List<Order> orders) {
        List<String> orderStrings = new ArrayList<>();

        for (Order order : orders) {
            StringBuilder orderDetails = new StringBuilder();
            orderDetails.append("Номер заказа: ").append(order.getOrderId()).append("\n");
            orderDetails.append("Номер телефона: ").append(order.getPhoneNumber()).append("\n");
            orderDetails.append("Список товаров: \n");

            double totalAmount = 0;

            for (CartItem cartItem : order.getCartItems()) {
                orderDetails.append("- ").append(cartItem.getName()).append("\n");
                totalAmount += cartItem.getPrice() * cartItem.getQuantity();
            }

            orderDetails.append(String.format(Locale.getDefault(), "Общая сумма: %.2f", totalAmount));

            orderStrings.add(orderDetails.toString());
        }

        return orderStrings;
    }
}
