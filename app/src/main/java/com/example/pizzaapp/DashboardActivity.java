package com.example.pizzaapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "Dashboard";

    private RecyclerView rvPopular;
    private TextView emptyView;

    private final List<Food> items = new ArrayList<>();
    private FoodAdapter adapter;

    private FirebaseFirestore db;
    private ListenerRegistration regMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        db = FirebaseFirestore.getInstance();

        rvPopular = findViewById(R.id.rvPopular);
        emptyView = findViewById(R.id.emptyView);

        rvPopular.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        adapter = new FoodAdapter(items, new FoodAdapter.FoodListener() {
            @Override public void onAdd(Food f) {
                Toast.makeText(DashboardActivity.this,
                        "Added: " + (f.name == null ? "" : f.name), Toast.LENGTH_SHORT).show();
            }
            @Override public void onFavToggle(Food f, boolean nowFav) {
                // optional persistence later
            }
        });
        rvPopular.setAdapter(adapter);
    }

    @Override protected void onStart() {
        super.onStart();
        // <-- MUST MATCH the document id in your screenshot exactly:
        listenBranchMenu("Colombo Branch");
    }

    @Override protected void onStop() {
        super.onStop();
        if (regMenu != null) { regMenu.remove(); regMenu = null; }
    }

    /** Loads branches/{branchId}/menu */
    private void listenBranchMenu(String branchId) {
        DocumentReference branchDoc = db.collection("branches").document(branchId);

        regMenu = branchDoc.collection("menu")
                .addSnapshotListener((@Nullable QuerySnapshot snaps,
                                      @Nullable com.google.firebase.firestore.FirebaseFirestoreException e) -> {
                    if (e != null) {
                        Log.e(TAG, "Firestore error", e);
                        Toast.makeText(this, "Menu load failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    items.clear();
                    if (snaps != null) {
                        Log.d(TAG, "menu size = " + snaps.size());
                        for (QueryDocumentSnapshot d : snaps) {
                            Food f = d.toObject(Food.class); // expects fields: imageurl (String), name (String), price (Number/String)
                            if (f != null) {
                                if (f.subtitle == null) f.subtitle = ""; // optional field
                                items.add(f);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    updateEmpty();
                });
    }

    private void updateEmpty() {
        if (items.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            rvPopular.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            rvPopular.setVisibility(View.VISIBLE);
        }
    }
}
