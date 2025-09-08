package com.example.pizzaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.VH> {

    /** Callbacks for card actions */
    public interface FoodListener {
        void onAdd(Food f);
        void onFavToggle(Food f, boolean nowFav);
    }

    private final List<Food> items;
    private final FoodListener listener;

    public FoodAdapter(@NonNull List<Food> items, FoodListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_food_card, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Food f = items.get(pos);

        h.txtTitle.setText(safe(f.name));
        h.txtSubtitle.setText(safe(f.subtitle));
        h.txtPrice.setText(formatRs(f.price));

        // Load image URL (fallback to launcher)
        if (f.imageurl != null && !f.imageurl.isEmpty()) {
            Glide.with(h.itemView.getContext())
                    .load(f.imageurl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(h.imgFood);
        } else {
            h.imgFood.setImageResource(R.drawable.ic_launcher_foreground);
        }

        h.btnAdd.setOnClickListener(v -> {
            if (listener != null) listener.onAdd(f);
        });

        h.btnFav.setOnClickListener(v -> {
            boolean sel = !v.isSelected();
            v.setSelected(sel);
            if (listener != null) listener.onFavToggle(f, sel);
        });
    }

    @Override public int getItemCount() { return items == null ? 0 : items.size(); }

    /** Matches ids in item_food_card.xml */
    public static class VH extends RecyclerView.ViewHolder {
        ImageView imgFood;
        ImageButton btnFav;
        TextView txtTitle, txtSubtitle, txtPrice;
        MaterialButton btnAdd;

        public VH(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            btnFav = itemView.findViewById(R.id.btnFav);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtSubtitle = itemView.findViewById(R.id.txtSubtitle);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }

    private static String safe(String s) { return s == null ? "" : s; }
    private static String formatRs(long v) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "LK"));
        return "Rs. " + nf.format(v);
        // If your price is stored as String in Firestore, change Food.price to String and remove this formatter.
    }
}
