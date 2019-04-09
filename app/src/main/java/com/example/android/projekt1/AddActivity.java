package com.example.android.projekt1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.android.projekt1.FirebaseDatabaseUtils;

public class AddActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editPrice, editQuantity;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        myDb = new DatabaseHelper(this);
        editName = findViewById(R.id.editTextName);
        editPrice = findViewById(R.id.editTextPrice);
        editQuantity = findViewById(R.id.editTextQuantity);
        saveButton = findViewById(R.id.save_button);
        addData();
    }

    public void addData() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ListItem item = new ListItem(editName.getText().toString(),
                            Float.parseFloat(editPrice.getText().toString()),
                            Integer.parseInt(editQuantity.getText().toString()),
                            false);
                    FirebaseDatabaseUtils.createOrUpdateItem(item);
                    broadcastIntent();
                } catch (Exception e) {
                    Toast.makeText(AddActivity.this, "Failed while adding product, check your data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void broadcastIntent() {
        Intent intent = new Intent();
        String permissions = "com.example.android.mybroadcastreceiver.my_permissions.MY_PERMISSION";
        intent.putExtra("name", editName.getText().toString());
        intent.putExtra("price", Float.parseFloat(editPrice.getText().toString()));
        intent.putExtra("quantity", Integer.parseInt(editQuantity.getText().toString()));
        intent.setAction("com.example.android.projekt1.notification");
        intent.setPackage("com.example.android.mybroadcastreceiver");
        sendBroadcast(intent, permissions);
    }
}
