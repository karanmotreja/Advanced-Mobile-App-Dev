package com.example.karanmotreja.afinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMainActivity extends AppCompatActivity {

    List<Restaurant> restaurants = new ArrayList<>();

    DatabaseReference restaurantref = FirebaseDatabase.getInstance().getReference("final");

    //define an adapter
    FirebaseRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //define a query
        Query query = FirebaseDatabase.getInstance().getReference().child("final");


       SnapshotParser<Restaurant> parser = new SnapshotParser<Restaurant>() {
           @NonNull
           @Override
           public Restaurant parseSnapshot(@NonNull DataSnapshot snapshot) {
               Restaurant newRestaurant = new Restaurant(snapshot.getKey(), snapshot.child("name").getValue().toString(), snapshot.child("url").getValue().toString());
                restaurants.add(newRestaurant);
                return newRestaurant;
           }
       };



        //adapter options
        FirebaseRecyclerOptions<Restaurant> options = new FirebaseRecyclerOptions.Builder<Restaurant>()
                .setQuery(query, parser)
                .build();


        //adapter
        adapter = new FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder>(options) {

            @NonNull
            @Override
            public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
                return new RestaurantViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(@NonNull final RestaurantViewHolder holder, int position, @NonNull final Restaurant model) {
                holder.setRestaurantname(model.getName());

                //onclick listener
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        int position = holder.getAdapterPosition();

                        String restaurantURL = restaurants.get(position).getUrl();

                        Intent intent = new Intent(Intent.ACTION_VIEW);

                        intent.setData(Uri.parse(restaurantURL));

                        startActivity(intent);
                    }
                });

                holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {

                        menu.setHeaderTitle("Delete " + model.getName());

                        menu.add(1, 1, 1, "Yes").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
                        {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                int position = holder.getAdapterPosition();

                                String restaurantid = restaurants.get(position).getId();

                                restaurantref.child(restaurantid).removeValue();
                                Snackbar.make(v, "Item removed", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                                return false;
                            }
                        });
                        menu.add(2, 2, 2, "No");
                    }
                });
            }
        };


        RecyclerView recyclerView = findViewById(R.id.recyclerView);


        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //add items
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout layout = new LinearLayout(RestaurantMainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);


                final EditText nameEditText = new EditText(RestaurantMainActivity.this);
                nameEditText.setHint("Restaurant name");
                layout.addView(nameEditText);
                final EditText urlEditText = new EditText(RestaurantMainActivity.this);
                urlEditText.setHint("URL");
                layout.addView(urlEditText);


                AlertDialog.Builder dialog = new AlertDialog.Builder(RestaurantMainActivity.this);
                dialog.setTitle("Add Restaurant");
                dialog.setView(layout);
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String restaurantName = nameEditText.getText().toString();
                        String restaurantUrl = urlEditText.getText().toString();
                        if (restaurantName.trim().length() > 0) {

                            String key = restaurantref.push().getKey();

                            Restaurant newRestaurant = new Restaurant(key, restaurantName, restaurantUrl);
                            //add to Firebase
                            restaurantref.child(key).child("name").setValue(newRestaurant.getName());
                            restaurantref.child(key).child("url").setValue(newRestaurant.getUrl());
                        }
                    }
                });
                dialog.setNegativeButton("Cancel", null);
                dialog.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_restaurant_main, menu);
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
}