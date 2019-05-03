package com.example.karanmotreja.project2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditActivity extends AppCompatActivity {

    EditText editName, editGift, editMessage;
    Button updateButton, deleteButton;
    DatabaseReference reference;

//    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        editName = findViewById(R.id.editName);
        editGift = findViewById(R.id.editGift);
        editMessage = findViewById(R.id.editMessage);

        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);

       // listView = (ListView) findViewById(R.id.recycler);

        // Bring value from previous page

        editName.setText(getIntent().getStringExtra("nameText"));
        editGift.setText(getIntent().getStringExtra("giftText"));
        editMessage.setText(getIntent().getStringExtra("confirmationText"));

        final String keykeyText = getIntent().getStringExtra("keyText");

        reference = FirebaseDatabase.getInstance().getReference().child("Project2").child("Gift" + keykeyText);


        //  Deleting items from Firebase Database
        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {

                        if(task.isSuccessful())
                        {
                            Intent a = new Intent(EditActivity.this, MainActivity.class);
                            startActivity(a);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Failed to Delete!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
//




        // Save button
        updateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

               // reference = FirebaseDatabase.getInstance().getReference().child("Project2").child("Gift" + keykeyText);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {

                        dataSnapshot.getRef().child("nameText").setValue(editName.getText().toString());
                        dataSnapshot.getRef().child("giftText").setValue(editGift.getText().toString());
                        dataSnapshot.getRef().child("confirmationText").setValue(editMessage.getText().toString());
                        dataSnapshot.getRef().child("keyText").setValue(keykeyText);

                        Intent a = new Intent(EditActivity.this, MainActivity.class);
                        startActivity(a);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
