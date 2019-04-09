package com.example.android.projekt1.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.projekt1.DatabaseHelper;
import com.example.android.projekt1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

      }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final SQLiteDatabase db = new DatabaseHelper(getApplicationContext()).getReadableDatabase();

        String[] projection = {
                DatabaseHelper.LATITUDE,
                DatabaseHelper.LONGITUDE,
                DatabaseHelper.RADIUS,
                DatabaseHelper.ISFAVOURITESHOP
        };

        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, projection,
                null, null, null, null, null);

        while (cursor.moveToNext()){
            String latitude = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.LATITUDE));
            String longitude = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.LONGITUDE));
            String radius = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.RADIUS));
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude))));
            mMap.addCircle(new CircleOptions()
                    .center(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)))
                    .radius(Double.parseDouble(radius))
                    .strokeWidth(1)
                    .strokeColor(Color.RED)
                    .fillColor(Color.TRANSPARENT));
        }

        cursor.close();
    }

}
