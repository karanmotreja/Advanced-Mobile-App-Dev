package com.example.karanmotreja.project2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class NewActivity extends AppCompatActivity
{

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    TextView titlePage, addName, addGift, messageText, phoneNumber;
    EditText editName, editGift, editMessage, editNumber;
    Button sendNow, sendLater;

    DatabaseReference reference;
    Integer giftNum = new Random().nextInt();
    String keyText = Integer.toString(giftNum);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);


        titlePage = findViewById(R.id.titlePage);
        addName = findViewById(R.id.addName);
        addGift = findViewById(R.id.addGift);
        messageText = findViewById(R.id.messageText);
        phoneNumber = findViewById(R.id.phoneNumber);

        editName = findViewById(R.id.editName);
        editGift = findViewById(R.id.editGift);
        editMessage = findViewById(R.id.editMessage);
        editNumber = findViewById(R.id.editNumber);


        sendNow = findViewById(R.id.saveButton);
        sendLater = findViewById(R.id.cancelButton);

        //alert = findViewById(R.id.alert);


//        sendNow.setEnabled(false);

        if(checkPermission(Manifest.permission.SEND_SMS))
        {
            sendNow.setEnabled(true);
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }

//        public void onSend(View v)
//        {
//            String number = phoneNumber.getText().toString();
//            String msg = messageText.getText().toString();
//
//            if(number == null || number.length() == 0 || msg == null || msg.length() == 0)
//            {
//                return;
//            }
//
//            if(checkPermission(Manifest.permission.SEND_SMS))
//            {
//                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage(number, null, msg, null, null);
//                Toast.makeText(this, "Message Sent!", Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
//            }
//        }


//        if(sendNow.isPressed())
//        {
//            TextView alert = findViewById(R.id.alert);
//            alert.setText("Sent!");
//        }
//        else if(sendLater.isPressed())
//        {
//            TextView alert = findViewById(R.id.alert);
//            alert.setText("Pending!");
//        }


        sendNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String number = phoneNumber.getText().toString();
                String msg = messageText.getText().toString();

                if(number == null || number.length() == 0 || msg == null || msg.length() == 0)
                {
                    return;
                }

                if(checkPermission(Manifest.permission.SEND_SMS))
                {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, msg, null, null);
                    Toast.makeText(getApplicationContext(), "Message Sent!", Toast.LENGTH_SHORT).show();
                }
//                else
//                {
//                   // Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
//                }


//                TextView alert = findViewById(R.id.alert);
//                alert.setText("Sent!");

                reference = FirebaseDatabase.getInstance().getReference().child("Project2").child("Gift" + giftNum);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("nameText").setValue(editName.getText().toString());
                        dataSnapshot.getRef().child("giftText").setValue(editGift.getText().toString());
                        dataSnapshot.getRef().child("confirmationText").setValue(editMessage.getText().toString());
                        dataSnapshot.getRef().child("keyText").setValue(keyText);

                        Intent a = new Intent(NewActivity.this, MainActivity.class);
                        startActivity(a);

                        //alert.setText("Sent!");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        sendLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                TextView alert = findViewById(R.id.alert);
//                alert.setText("Pending...");

                reference = FirebaseDatabase.getInstance().getReference().child("Project2").child("Gift" + giftNum);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("nameText").setValue(editName.getText().toString());
                        dataSnapshot.getRef().child("giftText").setValue(editGift.getText().toString());
                        dataSnapshot.getRef().child("confirmationText").setValue(editMessage.getText().toString());

                        Intent a = new Intent(NewActivity.this, MainActivity.class);
                        startActivity(a);

                       // alert.setText("Pending!");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    private boolean checkPermission(String permission)
    {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return(check == PackageManager.PERMISSION_GRANTED);
    }
}
