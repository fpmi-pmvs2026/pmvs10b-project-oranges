package com.example.smartshoppinglist;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartshoppinglist.data.AppDatabase;
import com.example.smartshoppinglist.data.Product;
import com.example.smartshoppinglist.data.ProductRepository;

public class AddProductActivity extends AppCompatActivity {

    private EditText etName, etQuantity;
    private Spinner spinnerUnit;
    private ProductRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        repository = new ProductRepository(AppDatabase.getInstance(this));

        etName = findViewById(R.id.et_product_name);
        etQuantity = findViewById(R.id.et_quantity);
        spinnerUnit = findViewById(R.id.spinner_unit);
        Button btnSave = findViewById(R.id.btn_save);

        String[] units = {"шт", "кг", "г", "л", "мл", "уп"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(adapter);

        btnSave.setOnClickListener(v -> saveProduct());
    }

    private void saveProduct() {
        String name = etName.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Введите название");
            return;
        }
        if (quantityStr.isEmpty()) {
            etQuantity.setError("Введите количество");
            return;
        }

        int quantity = Integer.parseInt(quantityStr);
        String unit = spinnerUnit.getSelectedItem().toString();
        Product product = new Product(name, quantity, unit, false);

        new Thread(() -> {
            repository.insert(product);
            runOnUiThread(() -> {
                Toast.makeText(this, "✅ " + name + " добавлен", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}