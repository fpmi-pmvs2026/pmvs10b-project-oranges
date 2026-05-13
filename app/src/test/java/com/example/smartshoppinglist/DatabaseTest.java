package com.example.smartshoppinglist;

import androidx.room.Room;
import android.content.Context;
import androidx.test.core.app.ApplicationProvider;

import com.example.smartshoppinglist.data.AppDatabase;
import com.example.smartshoppinglist.data.Product;
import com.example.smartshoppinglist.data.ProductDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class DatabaseTest {

    private AppDatabase database;
    private ProductDao productDao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(
                context,
                AppDatabase.class
        ).allowMainThreadQueries().build();
        productDao = database.productDao();
    }

    @After
    public void tearDown() {
        database.close();
    }

    @Test
    public void testInsertAndGetProduct() {
        Product product = new Product("Молоко", 2, "л", false);
        productDao.insert(product);

        List<Product> products = productDao.getAllProducts();

        assertEquals(1, products.size());
        assertEquals("Молоко", products.get(0).getName());
        assertEquals(2, products.get(0).getQuantity());
        assertEquals("л", products.get(0).getUnit());
        assertFalse(products.get(0).isBought());
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product("Хлеб", 1, "шт", false);
        productDao.insert(product);

        List<Product> products = productDao.getAllProducts();
        int id = products.get(0).getId();

        product.setId(id);
        product.setBought(true);
        productDao.update(product);

        Product updated = productDao.getAllProducts().get(0);
        assertTrue(updated.isBought());
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product("Яблоки", 3, "кг", false);
        productDao.insert(product);

        List<Product> products = productDao.getAllProducts();
        int id = products.get(0).getId();

        productDao.deleteById(id);

        assertEquals(0, productDao.getAllProducts().size());
    }
}