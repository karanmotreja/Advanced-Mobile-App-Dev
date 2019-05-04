package com.example.karanmotreja.motrejafinal;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private String name;
    private static final String PREFS_NAME = "ITEMS";

    public Item(String newItem) {
        name = newItem;
    }

    public static List<Item> myItems = new ArrayList<Item>();


    public String getName() {
        return name;
    }

    public static void storeData(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putInt("size", myItems.size());

        for (int i = 0; i < myItems.size(); i++) {
            editor.putString("item" + i, myItems.get(i).getName());
        }

        editor.apply();
    }

    public static void loadData(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        int size = sharedPrefs.getInt("size", 0);

        if(size>0) {
            for (int i = 0; i < size; i++) {
                myItems.add(new Item(sharedPrefs.getString("item" + i, "")));
            }
        }
    }
}