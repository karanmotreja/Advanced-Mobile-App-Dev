package com.example.karanmotreja.motrejafinal;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);

        listAdapter = new ListAdapter(Item.myItems);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(listAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //load data
        if(Item.myItems.isEmpty()) {
            Item.loadData(this);
        }

        //fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText restaurant = new EditText(getApplicationContext());
                restaurant.setHint("Enter Restaurant");
                layout.addView(restaurant);

                final EditText url = new EditText(getApplicationContext());
                url.setHint("Enter URL");
                layout.addView(url);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Add Restaurant");
                dialog.setView(layout);


                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        String newURL = url.getText().toString();
                        String newRestaurant = restaurant.getText().toString();
                        if (!newRestaurant.isEmpty()) {
                            listAdapter.addItem(newRestaurant);
                        }
                        Snackbar.make(view, "Item added", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                dialog.show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //save data
        Item.storeData(this);
    }
}