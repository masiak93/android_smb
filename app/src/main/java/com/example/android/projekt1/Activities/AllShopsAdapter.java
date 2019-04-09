package com.example.android.projekt1.Activities;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.projekt1.DatabaseHelper;
import com.example.android.projekt1.R;

public class AllShopsAdapter extends RecyclerView.Adapter<AllShopsAdapter.ViewHolder> {

    interface FavouriteShop{
        void onFavouriteShop(int id, boolean isVal);
    }

    private FavouriteShop favouriteShop;

    void setFavouriteShop(FavouriteShop favouriteShop){
        this.favouriteShop = favouriteShop;
    }

    private Cursor mCursor;

    AllShopsAdapter(Cursor mCursor) {
        this.mCursor = mCursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_items_shops, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        if (mCursor != null){
            mCursor.moveToPosition(position);
            String shopTitle = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.SHOP));
            String description = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.DESCRIPTION));
            String latitude = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.LATITUDE));
            String longitude = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.LONGITUDE));
            String radius = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.RADIUS));
            int isFavouriteShop = mCursor.getInt(mCursor.getColumnIndexOrThrow(DatabaseHelper.ISFAVOURITESHOP));
            final int id = mCursor.getInt(mCursor.getColumnIndexOrThrow(DatabaseHelper.ID));

            holder.shopTitleTextView.setText(shopTitle);
            holder.shopDescriptionTextView.setText(description);
            String latLonRad = latitude + " " + longitude + " " + "in area of " + radius + " m";
            final boolean isFav = isFavouriteShop == 1;
            holder.latlonTextView.setText(latLonRad);

            if (isFav){
                holder.favouriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);
            } else {
                holder.favouriteButton.setImageResource(R.drawable.ic_star_border_black_24dp);
            }

            holder.favouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Drawable.ConstantState currentState = holder.favouriteButton.getDrawable().getConstantState();
                    Drawable.ConstantState notFav = holder.itemView.getResources().getDrawable(R.drawable.ic_star_border_black_24dp).getConstantState();
                    Drawable.ConstantState fav = holder.itemView.getResources().getDrawable(R.drawable.ic_star_yellow_24dp).getConstantState();

                    if (currentState == notFav){
                        holder.favouriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);
                        favouriteShop.onFavouriteShop(id, true);
                    }
                    if (currentState == fav){
                        holder.favouriteButton.setImageResource(R.drawable.ic_star_border_black_24dp);
                        favouriteShop.onFavouriteShop(id, false);
                    }
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        if (mCursor != null){
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView shopTitleTextView;
        TextView shopDescriptionTextView;
        TextView latlonTextView;
        ImageView favouriteButton;



        ViewHolder(@NonNull View view) {
            super(view);
            shopTitleTextView = view.findViewById(R.id.shop_title_textview);
            shopDescriptionTextView = view.findViewById(R.id.shop_description_textview);
            latlonTextView = view.findViewById(R.id.radius_lon_lat_textview);
            favouriteButton = view.findViewById(R.id.favourite_button);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }
}
