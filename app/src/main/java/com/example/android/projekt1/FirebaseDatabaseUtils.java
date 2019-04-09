package com.example.android.projekt1;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Random;

import com.example.android.projekt1.ListItem;

import static android.support.constraint.Constraints.TAG;

public class FirebaseDatabaseUtils {

    private static DatabaseReference databaseReference = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("products");
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static Random random = new Random();

    public static void createOrUpdateItem(ListItem item) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (item.getId() == null) {
            item.setId(random.nextInt());
        }

        databaseReference.child(currentUser.getUid())
                .child(item.getId().toString())
                .setValue(item);
    }

    public static void removeItem(Integer id) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        databaseReference.child(currentUser.getUid())
                .child(id.toString())
                .removeValue();
    }
}
