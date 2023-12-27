package com.example.musicshop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicshop.Classes.CartItem;
import com.example.musicshop.DatabaseHelper;
import com.example.musicshop.Classes.Item;
import com.example.musicshop.Adapters.MenuAdapter;
import com.example.musicshop.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class СatalogMenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private DatabaseHelper databaseHelper;
    private List<CartItem> cartItems;
    private Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_menu);

        recyclerView = findViewById(R.id.recyclerViewMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);

        menuAdapter = new MenuAdapter(this, getItemsFromDatabase(), new MenuAdapter.OnAddToCartClickListener() {
            @Override
            public void onAddToCartClick(Item item) {
                addToCart(item);
            }
        });

        recyclerView.setAdapter(menuAdapter);
        cartItems = new ArrayList<>();

        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(v -> openCartActivity());
    }

    private List<Item> getItemsFromDatabase() {
        if (databaseHelper != null) {
            return databaseHelper.getAllItems();
        } else {
            return new ArrayList<>();
        }
    }

    private void addToCart(Item items) {
        CartItem cartItem = new CartItem(items.getId(), items.getName(), items.getPrice());

        boolean itemExists = false;
        for (CartItem item : cartItems) {
            if (item.getItemId() == cartItem.getItemId()) {
                item.incrementQuantity();
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            cartItems.add(cartItem);
        }

        String toastMessage = String.format("%s добавлен в корзину", items.getName());
        Snackbar.make(findViewById(android.R.id.content), toastMessage, Snackbar.LENGTH_SHORT).show();
    }

    private void openCartActivity() {
        if (cartItems.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), "Добавьте что-нибудь в корзину", Snackbar.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(СatalogMenuActivity.this, CartActivity.class);
            intent.putExtra("cartItems", new ArrayList<>(cartItems));
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("cartItems", new ArrayList<>(cartItems));
        super.onSaveInstanceState(outState);
    }
}
