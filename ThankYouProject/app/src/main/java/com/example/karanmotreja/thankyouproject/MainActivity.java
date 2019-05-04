package com.example.karanmotreja.thankyouproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karanmotreja.thankyouproject.Adapter.ListItemAdapter;
import com.example.karanmotreja.thankyouproject.Model.Gift;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity
{

    List<Gift> gifts = new ArrayList<>();
    FirebaseFirestore db;

    RecyclerView giftList;
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton fab;

    public MaterialEditText name, gift;
    public boolean isUpdate = false;
    public String idUpdate = "";

    ListItemAdapter adapter;


    android.app.AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();


        //View
        dialog = new SpotsDialog(this);
        //dialog = new SpotsDialog(this);

        name = (MaterialEditText)findViewById(R.id.name);
        gift = (MaterialEditText)findViewById(R.id.gift);
        fab = (FloatingActionButton) findViewById(R.id.fab);




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(!isUpdate)
                {
                    setData(name.getText().toString(), gift.getText().toString());
                }
                else
                {
                    updateData(name.getText().toString(), gift.getText().toString());
                    isUpdate = !isUpdate;
                }

            }
        });

        giftList = (RecyclerView)findViewById(R.id.giftList);
        giftList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        giftList.setLayoutManager(layoutManager);


        loadData();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Delete"))
        {
            deleteItem(item.getOrder());
        }

        return super.onContextItemSelected(item);
    }

    private void deleteItem(int index)
    {
        db.collection("Project2")
                .document(gifts.get(index).getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loadData();
                    }
                });
    }

    private void updateData(String name, String gift)
    {

        db.collection("Project2").document(idUpdate)
                .update("name", name, "gift",gift)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this,"Updated!", Toast.LENGTH_SHORT).show();
                    }
                });

        db.collection("Project2").document(idUpdate)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                        loadData();
                    }
                });

    }

    private void setData(String name, String gift)
    {
        String id = UUID.randomUUID().toString();
        Map<String,Object> present = new HashMap<>();

        present.put("id",id);
        present.put("name",name);
        present.put("gift",gift);

        db.collection("Project2").document(id)
                .set(present).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                //Refresh Data
                loadData();
            }
        });
    }

    private void loadData()
    {
        dialog.show();

        if(gifts.size() > 0)
        {
            gifts.clear();
        }
            db.collection("Project2")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Gift present = new Gift(doc.getString("id"),
                                        doc.getString("name"),
                                        doc.getString("gift"));
                                gifts.add(present);
                            }

                            adapter = new ListItemAdapter(MainActivity.this, gifts);
                            giftList.setAdapter(adapter);

                            dialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(MainActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    });

    }
}
