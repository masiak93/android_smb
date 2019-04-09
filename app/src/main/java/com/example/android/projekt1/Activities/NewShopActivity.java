package com.example.android.projekt1.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.projekt1.DatabaseHelper;
import com.example.android.projekt1.R;

import org.w3c.dom.Text;

public class NewShopActivity extends AppCompatActivity {

    private double latitide;
    private double longitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shop);
        if (checkPermissions()) {
            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitide = location.getLatitude();
                    longitude = location.getLongitude();
                    updateTextView();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            try {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
            } catch (SecurityException e){
                Log.e("No permissions granted", e.toString());
            }

        } else {
            askForPermission();
        }
    }

    @SuppressLint("SetTextI18n")
    void updateTextView(){
        TextView latLonTextView = findViewById(R.id.lat_lon_textview);
        latLonTextView.setText(latitide + " " + longitude);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.done:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void save(){
        EditText shopEditText = findViewById(R.id.name_of_shop_edittext);
        EditText descriptionEditText = findViewById(R.id.shop_description_edittext);
        EditText radiusEditText = findViewById(R.id.radius_edittext);

        if (TextUtils.isEmpty(shopEditText.getText())){
            shopEditText.setError("Please fill in data");
            return;
        }
        if (TextUtils.isEmpty(descriptionEditText.getText())){
            descriptionEditText.setError("Please fill in data");
            return;
        }
        if (TextUtils.isEmpty(radiusEditText.getText())){
            radiusEditText.setError("Please fill in data");
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        long response =
                dbHelper.addShops(String.valueOf(latitide), String.valueOf(longitude), shopEditText.getText().toString(),
                 descriptionEditText.getText().toString(), radiusEditText.getText().toString());

        if (response == -1) {
            Toast.makeText(this, "There has been an error while saving data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Shop saved", Toast.LENGTH_SHORT).show();
            finish();
        }

    }


    boolean checkPermissions(){
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    void askForPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
}
