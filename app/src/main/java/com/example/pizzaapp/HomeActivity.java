package com.example.pizzaapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList; import java.util.List;

public class HomeActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView rv = findViewById(R.id.rvPopularPizzas);
        rv.setLayoutManager(new GridLayoutManager(this, 2));

        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(R.drawable.pizza_1,"Margherita","$9.99"));
        pizzas.add(new Pizza(R.drawable.pizza_2,"Pepperoni","$11.49"));
        pizzas.add(new Pizza(R.drawable.pizza_3,"Veggie Delight","$10.99"));
        pizzas.add(new Pizza(R.drawable.pizza_4,"BBQ Chicken","$12.49"));
        pizzas.add(new Pizza(R.drawable.pizza_5,"Cheese Burst","$13.99"));

        rv.setAdapter(new PizzaAdapter(this, pizzas, new PizzaAdapter.OnPizzaClickListener() {
            @Override public void onAddClicked(Pizza p, int pos) {
                Toast.makeText(HomeActivity.this,"Added: "+p.getName(),Toast.LENGTH_SHORT).show();
            }
            @Override public void onItemClicked(Pizza p, int pos) {
                Toast.makeText(HomeActivity.this,"Open: "+p.getName(),Toast.LENGTH_SHORT).show();
            }
        }));
    }
}
