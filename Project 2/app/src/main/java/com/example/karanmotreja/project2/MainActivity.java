package com.example.karanmotreja.project2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView titlePage, subtitlePage, endPage;
    Button addButton;


    DatabaseReference reference;
    RecyclerView recycler;
    ArrayList<MyTY> list;
    TYAdapter tyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        titlePage = findViewById(R.id.titlePage);
        subtitlePage = findViewById(R.id.subtitlePage);
        endPage = findViewById(R.id.endPage);


        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, NewActivity.class);
                startActivity(a);
            }
        });
        // working with data

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyTY>();


        // pull data from Firebase

        reference = FirebaseDatabase.getInstance().getReference().child("Project2");
        reference.addValueEventListener(new ValueEventListener() {

            // retrieve and replace data
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    MyTY p = dataSnapshot1.getValue(MyTY.class);
                    list.add(p);
                }

                tyAdapter = new TYAdapter(MainActivity.this, list);
                recycler.setAdapter(tyAdapter);
                tyAdapter.notifyDataSetChanged();

            }

            // error
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
