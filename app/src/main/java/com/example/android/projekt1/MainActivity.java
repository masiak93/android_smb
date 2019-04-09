package com.example.android.projekt1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.android.projekt1.Activities.MapsActivity;
import com.example.android.projekt1.Activities.NewShopActivity;
import com.example.android.projekt1.Activities.ScheduleGeofenceTransitions;
import com.example.android.projekt1.Activities.ShopList;
import com.example.android.projekt1.Activities.favouriteShopsOnMap;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button options_button;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = firebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        Button logout_button = findViewById(R.id.button_logout);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        Button list_button = findViewById(R.id.list_button);
        list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductListActivity();
            }
        });

        Button mapsButton = findViewById(R.id.open_maps);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        Button newShopButton =  findViewById(R.id.new_shop_button);
        newShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewShopActivity.class);
                startActivity(intent);
                }
        });

        Button shopListButton = findViewById(R.id.shop_list_button);
        shopListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShopList.class);
                startActivity(intent);
            }
        });

        Button showFavShopsOnMapButton = findViewById(R.id.map_fav_shop_button);
        showFavShopsOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), favouriteShopsOnMap.class);
                startActivity(intent);
            }
        });

        checkPermission();
        setUpBroadcastReceiver();


        options_button = findViewById(R.id.option_button);
        options_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptionsActivity();
            }
        });
    }

    public void openProductListActivity() {
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }

    public void openOptionsActivity() {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    void checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        }
    }

    void setUpBroadcastReceiver(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            //Set up Proximity Alert
            ScheduleGeofenceTransitions geofenceTransitions = new ScheduleGeofenceTransitions(getApplicationContext());
            geofenceTransitions.mainMethod();
            Log.v("Broadcast Receiver", "Set up");

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpBroadcastReceiver();
    }
}

