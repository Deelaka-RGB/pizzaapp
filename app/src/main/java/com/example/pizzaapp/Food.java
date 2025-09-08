package com.example.pizzaapp;

public class Food {
    public String imageurl;   // Firestore: "https://..."
    public String name;       // "BBQ Chicken"
    public long price;      // 1600  (if you stored a number)
    public String subtitle;   // optional (category/description)

    public Food() {}          // required for Firestore
}
