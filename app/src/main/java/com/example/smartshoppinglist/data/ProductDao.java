package com.example.smartshoppinglist.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products ORDER BY isBought ASC, id DESC")
    List<Product> getAllProducts();

    @Insert
    void insert(Product product);

    @Update
    void update(Product product);

    @Query("DELETE FROM products WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM products WHERE isBought = 1")
    void deleteBoughtItems();

    @Query("DELETE FROM products")
    void deleteAllProducts();
}