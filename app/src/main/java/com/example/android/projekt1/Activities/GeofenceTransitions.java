package com.example.android.projekt1.Activities;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceTransitions extends IntentService {


    public GeofenceTransitions() {
        super(GeofenceTransitions.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v("Pending intent", "Starting intentservice");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e("Geofence error", "Error occured while handling intent");
            return;
        }
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT){
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            Log.v("Triggering geofences", triggeringGeofences.toString());

            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                Toast.makeText(this, "You've entered the shop", Toast.LENGTH_SHORT).show();
            }
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT){
                Toast.makeText(this, "You've left the shop", Toast.LENGTH_SHORT).show();
            }

        }

    }

}
