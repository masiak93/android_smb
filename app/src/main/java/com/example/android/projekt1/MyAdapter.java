package com.example.android.projekt1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> list;
    private Context context;
    DatabaseHelper myDB;

    public MyAdapter(List<ListItem> lil) {
        list = lil;
        /*context = ctx;*/
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_listitem, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int position) {
        ListItem li = list.get(position);
        h.setIdItem(li.getId());
        h.name.setText(li.getName());
        h.name.setTextColor(getColor(h.name.getContext()));
        h.name.setTextSize(getSize(h.name.getContext()));
        h.price.setText(" " + li.getPrice() + " PLN");
        h.price.setTextColor(getColor(h.price.getContext()));
        h.price.setTextSize(getSize(h.price.getContext()));
        h.quantity.setText(" " + li.getQuantity() + " SZT");
        h.quantity.setTextColor(getColor(h.quantity.getContext()));
        h.quantity.setTextSize(getSize(h.quantity.getContext()));
        h.bought.setChecked(li.getChecked());
    }

    private int getColor(@NonNull Context ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("FontColor", MODE_PRIVATE);
        int selected_color = sharedPreferences.getInt("color", ctx.getResources().getColor(R.color.colorBlack));
        return selected_color;
    }

    private Float getSize(@NonNull Context ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("FontSize", MODE_PRIVATE);
        Float selectedSize = sharedPreferences.getFloat("size", 15);
        return selectedSize;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView price;
        public TextView quantity;
        public CheckBox bought;
        public Integer idItem;
        public RelativeLayout parentLayout;

        public ViewHolder(View view) {
            super (view);
            name = view.findViewById(R.id.item_name);
            price = view.findViewById(R.id.item_price);
            quantity = view.findViewById(R.id.item_quantity);
            bought = view.findViewById(R.id.checkBox);
            parentLayout = view.findViewById(R.id.parentLayout);

            view.setOnClickListener(this);

            bought.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String str_name = name.getText().toString();
                    Float str_price = Float.valueOf(price.getText().toString().replaceAll("PLN", "").trim());
                    Integer str_quantity = Integer.valueOf(quantity.getText().toString().replaceAll("SZT", "").trim());
                    if (isChecked) {
                        ListItem item = new ListItem(idItem, str_name, str_price, str_quantity, true);
                        FirebaseDatabaseUtils.createOrUpdateItem(item);
                    } else {
                        ListItem item = new ListItem(idItem, str_name, str_price, str_quantity, false);
                        FirebaseDatabaseUtils.createOrUpdateItem(item);
                    }
                }
            });
        }

        public void setIdItem(Integer idItem) {
            this.idItem = idItem;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Selected item is " + name.getText(),
                    Toast.LENGTH_LONG).show();
            myDB = new DatabaseHelper(MyAdapter.this.context);
            Intent intent = new Intent(v.getContext(), EditActivity.class);
            String str_name = name.getText().toString();
            String str_price = price.getText().toString();
            String str_quantity = quantity.getText().toString();
            boolean is_checked;
            if (bought.isChecked()) {
                is_checked = true;
            }
            else {
                is_checked = false;
            }
            intent.putExtra("product_name", str_name);
            intent.putExtra("product_price", str_price);
            intent.putExtra("product_quantity", str_quantity);
            intent.putExtra("product_id", idItem);
            intent.putExtra("product_checked", is_checked);
            v.getContext().startActivity(intent);
        }
    }
}
