package com.example.karanmotreja.afinal;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RestaurantViewHolder extends RecyclerView.ViewHolder
{
    private TextView restaurantname;

    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        restaurantname = itemView.findViewById(R.id.restaurantTextView);
    }

    public void setRestaurantname(String name){
        restaurantname.setText(name);
    }
}
