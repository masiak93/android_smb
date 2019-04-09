package com.example.android.projekt1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NotificationChooseActivity extends AppCompatActivity {

    TextView editProductButton, mainListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_choose);

        editProductButton = findViewById(R.id.editProductButton);
        mainListButton = findViewById(R.id.mainListButton);
    }

    public void goToListActivity(View view) {
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToEditProductActivity(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtras(getIntent());
        intent.putExtra("disableDelete", 1);
        this.startActivity(intent);
    }
}
