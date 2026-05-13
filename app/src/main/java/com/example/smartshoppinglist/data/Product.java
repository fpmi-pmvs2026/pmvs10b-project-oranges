package com.example.smartshoppinglist.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int quantity;
    private String unit;
    private boolean isBought;

    public Product(String name, int quantity, String unit, boolean isBought) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.isBought = isBought;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public boolean isBought() { return isBought; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setUnit(String unit) { this.unit = unit; }
    public void setBought(boolean bought) { isBought = bought; }
}