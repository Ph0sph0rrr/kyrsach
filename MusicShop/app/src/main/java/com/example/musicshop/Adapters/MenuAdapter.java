package com.example.musicshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicshop.Classes.Item;
import com.example.musicshop.R;

import java.util.List;
import java.util.Locale;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context context;
    private List<Item> items;
    private OnAddToCartClickListener addToCartClickListener;

    public MenuAdapter(Context context, List<Item> items, OnAddToCartClickListener addToCartClickListener) {
        this.context = context;
        this.items = items;
        this.addToCartClickListener = addToCartClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);

        holder.textItemName.setText(item.getName());
        holder.textItemDescription.setText(item.getDescription());
        holder.textItemPrice.setText(String.format(Locale.getDefault(), "%.2f", item.getPrice()));

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addToCartClickListener != null) {
                    addToCartClickListener.onAddToCartClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textItemName;
        TextView textItemDescription;
        TextView textItemPrice;
        Button btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textItemName = itemView.findViewById(R.id.textItemName);
            textItemDescription = itemView.findViewById(R.id.textItemDescription);
            textItemPrice = itemView.findViewById(R.id.textItemPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }

    public interface OnAddToCartClickListener {
        void onAddToCartClick(Item item);
    }
}