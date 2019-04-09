package com.example.android.projekt1;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    Button btnRed, btnBlack, btnBlue, btnSmall, btnMedium, btnBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        btnRed = findViewById(R.id.button_red);
        btnBlack = findViewById(R.id.button_black);
        btnBlue = findViewById(R.id.button_blue);
        btnSmall = findViewById(R.id.button_small);
        btnMedium = findViewById(R.id.button_medium);
        btnBig = findViewById(R.id.button_big);

        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeColor(getResources().getColor(R.color.colorRed));
                Toast.makeText(OptionsActivity.this, "Color changed", Toast.LENGTH_LONG).show();
            }
        });

        btnBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeColor(getResources().getColor(R.color.colorBlack));
                Toast.makeText(OptionsActivity.this, "Color changed", Toast.LENGTH_LONG).show();
            }
        });

        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeColor(getResources().getColor(R.color.colorBlue));
                Toast.makeText(OptionsActivity.this, "Color changed", Toast.LENGTH_LONG).show();
            }
        });

        btnSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeSize(Float.valueOf(getResources().getString(R.string.font_small)));
                Toast.makeText(OptionsActivity.this, "Size changed", Toast.LENGTH_LONG).show();
            }
        });

        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeSize(Float.valueOf(getResources().getString(R.string.font_medium)));
                Toast.makeText(OptionsActivity.this, "Size changed", Toast.LENGTH_LONG).show();
            }
        });

        btnBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeSize(Float.valueOf(getResources().getString(R.string.font_big)));
                Toast.makeText(OptionsActivity.this, "Size changed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void storeColor(int color) {
        SharedPreferences sharedPreferences = getSharedPreferences("FontColor", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("color", color);
        editor.apply();
    }

    private void storeSize(Float size) {
        SharedPreferences sharedPreferences = getSharedPreferences("FontSize", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("size", size);
        editor.apply();
    }

    private int getColor() {
        SharedPreferences sharedPreferences = getSharedPreferences("FontColor", MODE_PRIVATE);
        int selected_color = sharedPreferences.getInt("color", getResources().getColor(R.color.colorBlack));
        return selected_color;
    }

    private String getSize() {
        SharedPreferences sharedPreferences = getSharedPreferences("FontSize", MODE_PRIVATE);
        String selectedSize = sharedPreferences.getString("size", "15sp");
        return selectedSize;
    }
}
