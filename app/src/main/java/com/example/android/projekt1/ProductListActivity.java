package com.example.android.projekt1;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private Button addButton;
    DatabaseHelper myDB;
    private List<ListItem> itemList = new ArrayList<>();
    private MyAdapter listAdapter;
    private RecyclerView irv;
    private FirebaseAuth auth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        irv = findViewById(R.id.item_list);

        LinearLayoutManager rlm = new LinearLayoutManager(this);
        irv.setLayoutManager(rlm);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(irv.getContext(), rlm.getOrientation());
        irv.addItemDecoration(dividerItemDecoration);

        /*MyAdapter rva = new MyAdapter(getItems(), this);
        irv.setAdapter(rva);*/

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("products").child(auth.getUid());

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddActivity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ListItem item = snapshot.getValue(ListItem.class);
                    itemList.add(item);
                }

                listAdapter = new MyAdapter(itemList);
                irv.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void openAddActivity() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }


}
