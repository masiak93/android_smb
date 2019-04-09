package com.example.android.projekt1.Activities;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.projekt1.DatabaseHelper;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class ScheduleGeofenceTransitions  {

    private PendingIntent mGeofencePendingIntent;
    private GeofencingClient geofencingClient;
    List mGeofenceList = new ArrayList<>();
    private Context context;

    public ScheduleGeofenceTransitions(Context context) {
        this.context = context;
    }

    @SuppressLint("MissingPermission")
    public void mainMethod(){
        geofencingClient = LocationServices.getGeofencingClient(context);


        final SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        String[] projection = {
                DatabaseHelper.LATITUDE,
                DatabaseHelper.LONGITUDE,
                DatabaseHelper.RADIUS,
                DatabaseHelper.ID
        };

        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, projection,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            String ID = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.ID));
            String latitude = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.LATITUDE));
            String longitude = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.LONGITUDE));
            String radius = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.RADIUS));

            mGeofenceList.add(new Geofence.Builder()
                    .setRequestId(ID)
                    .setCircularRegion(
                            Double.parseDouble(latitude),
                            Double.parseDouble(longitude),
                            Float.parseFloat(radius)
                    )
                    .setExpirationDuration(-1)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
            }

        Log.v("List", mGeofenceList.toString());
        if (!mGeofenceList.isEmpty()) {
            geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent());
        }
        Log.v("Geofences", "setting up geofences");

    }


    private GeofencingRequest getGeofencingRequest(){
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    PendingIntent getGeofencePendingIntent(){
        if (mGeofencePendingIntent != null){
            return  mGeofencePendingIntent;
        }
        Intent intent = new Intent(context, GeofenceTransitions.class);
        mGeofencePendingIntent = PendingIntent.getService(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }
}


