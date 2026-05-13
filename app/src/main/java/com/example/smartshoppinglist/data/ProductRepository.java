package com.example.smartshoppinglist.data;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {

    private ProductDao productDao;
    private ExecutorService executorService;

    public ProductRepository(AppDatabase database) {
        this.productDao = database.productDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public void insert(Product product) {
        executorService.execute(() -> productDao.insert(product));
    }

    public void update(Product product) {
        executorService.execute(() -> productDao.update(product));
    }

    public void deleteById(int id) {
        executorService.execute(() -> productDao.deleteById(id));
    }

    public void deleteBoughtItems() {
        executorService.execute(() -> productDao.deleteBoughtItems());
    }

    public void deleteAllProducts() {
        executorService.execute(() -> productDao.deleteAllProducts());
    }
}