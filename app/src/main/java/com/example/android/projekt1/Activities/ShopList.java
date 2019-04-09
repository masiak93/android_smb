package com.example.android.projekt1.Activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.projekt1.DatabaseHelper;
import com.example.android.projekt1.R;

public class ShopList extends AppCompatActivity {

    SQLiteDatabase mDatabase;
    AllShopsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_shops);

        mDatabase = new DatabaseHelper(this).getWritableDatabase();
        adapter = new AllShopsAdapter(getAllItems());

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setFavouriteShop(new AllShopsAdapter.FavouriteShop() {
            @Override
            public void onFavouriteShop(int id, boolean isVal) {
                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                db.updateValidation(id, isVal);
            }
        });

    }


    Cursor getAllItems() {

        String selection = "((" + DatabaseHelper.LATITUDE + " NOTNULL) AND ("
                + DatabaseHelper.SHOP + " NOTNULL))";

        String[] projection = {
                DatabaseHelper.ID,
                DatabaseHelper.SHOP,
                DatabaseHelper.DESCRIPTION,
                DatabaseHelper.LATITUDE,
                DatabaseHelper.LONGITUDE,
                DatabaseHelper.RADIUS,
                DatabaseHelper.ISFAVOURITESHOP
        };

        return mDatabase.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null
        );

    }

}
