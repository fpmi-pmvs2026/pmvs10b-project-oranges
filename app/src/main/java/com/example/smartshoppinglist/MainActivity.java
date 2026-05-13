package com.example.smartshoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartshoppinglist.adapters.ProductAdapter;
import com.example.smartshoppinglist.data.AppDatabase;
import com.example.smartshoppinglist.data.Product;
import com.example.smartshoppinglist.data.ProductRepository;
import com.example.smartshoppinglist.utils.SettingsManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ProductRepository repository;
    private SettingsManager settingsManager;
    private List<Product> productList;
    private Button btnDeleteBought, btnDeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new ProductRepository(AppDatabase.getInstance(this));
        settingsManager = new SettingsManager(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddProductActivity.class));
        });

        // Находим кнопки
        btnDeleteBought = findViewById(R.id.btn_delete_bought);
        btnDeleteAll = findViewById(R.id.btn_delete_all);

        // Кнопка "Очистить купленные"
        btnDeleteBought.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Очистить список")
                    .setMessage("Удалить все купленные товары?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        new Thread(() -> {
                            repository.deleteBoughtItems();
                            runOnUiThread(() -> loadProducts());
                        }).start();
                        Toast.makeText(MainActivity.this, "Купленные товары удалены", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Нет", null)
                    .show();
        });

        // Кнопка "Очистить всё"
        btnDeleteAll.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Очистить весь список")
                    .setMessage("Вы уверены, что хотите удалить ВСЕ товары? Это действие нельзя отменить.")
                    .setPositiveButton("Да, удалить всё", (dialog, which) -> {
                        new Thread(() -> {
                            repository.deleteAllProducts();
                            runOnUiThread(() -> loadProducts());
                        }).start();
                        Toast.makeText(MainActivity.this, "Весь список очищен", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Отмена", null)
                    .show();
        });

        loadProducts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

    private void loadProducts() {
        new Thread(() -> {
            productList = repository.getAllProducts();
            runOnUiThread(() -> {
                if (adapter == null) {
                    adapter = new ProductAdapter(productList);
                    setupAdapterListeners();
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.updateList(productList);
                }
            });
        }).start();
    }

    private void setupAdapterListeners() {
        // Обработчик клика по чекбоксу
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onCheckClick(Product product, boolean isChecked) {
                product.setBought(isChecked);
                new Thread(() -> repository.update(product)).start();
                String msg = isChecked ? "✅ " + product.getName() + " куплен" : "↩️ " + product.getName() + " возвращён";
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        // Обработчик долгого нажатия
        adapter.setOnItemLongClickListener(new ProductAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Product product) {
                boolean isConfirm = settingsManager.isConfirmDeleteEnabled();
                if (isConfirm) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Удалить товар")
                            .setMessage("Удалить " + product.getName() + " из списка?")
                            .setPositiveButton("Да", (dialog, which) -> deleteProduct(product))
                            .setNegativeButton("Нет", null)
                            .show();
                } else {
                    deleteProduct(product);
                }
            }
        });
    }

    private void deleteProduct(Product product) {
        new Thread(() -> {
            repository.deleteById(product.getId());
            runOnUiThread(() -> {
                loadProducts();
                Toast.makeText(MainActivity.this, "🗑️ " + product.getName() + " удалён", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}