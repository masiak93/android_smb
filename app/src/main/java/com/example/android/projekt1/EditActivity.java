package com.example.android.projekt1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editPrice, editQuantity;
    Button saveButton, deleteButton;

    private String selectedName, selectedPrice, selectedQuantity;
    private int selectedId;
    private boolean selectedIsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        saveButton = findViewById(R.id.edit_save_button);
        deleteButton = findViewById(R.id.delete_button);
        editName = findViewById(R.id.editName);
        editPrice = findViewById(R.id.editPrice);
        editQuantity = findViewById(R.id.editQuantity);
        myDb = new DatabaseHelper(this);

        getIncomingIntent();
        editData();
        deleteData();



    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("product_id") && getIntent().hasExtra("product_name")
        && getIntent().hasExtra("product_price") && getIntent().hasExtra("product_quantity")) {
            selectedId = getIntent().getIntExtra("product_id", 0);
            selectedName = getIntent().getStringExtra("product_name");
            selectedPrice = getIntent().getStringExtra("product_price").replaceAll("PLN", "");
            selectedQuantity = getIntent().getStringExtra("product_quantity").replaceAll("SZT", "");
            selectedIsChecked = getIntent().getBooleanExtra("product_checked", false);

            editName.setText(selectedName);
            editPrice.setText(selectedPrice);
            editQuantity.setText(selectedQuantity);
        }
    }

    public void editData() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ListItem item = new ListItem(
                            selectedId,
                            editName.getText().toString(),
                            Float.parseFloat(editPrice.getText().toString()),
                            Integer.parseInt(editQuantity.getText().toString().replaceAll("\\s+","")),
                            selectedIsChecked);
                    FirebaseDatabaseUtils.createOrUpdateItem(item);
                    Toast.makeText(EditActivity.this, "Product edited", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(EditActivity.this, "Failed while edit product, check your data", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void deleteData() {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabaseUtils.removeItem(selectedId);
                editName.setText("");
                editPrice.setText("");
                editQuantity.setText("");
                Toast.makeText(EditActivity.this, "Product deleted", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void prepareData() {

    }
}
