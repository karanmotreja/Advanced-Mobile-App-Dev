package com.example.karanmotreja.thankyouproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.karanmotreja.thankyouproject.Adapter.ListItemAdapter;

public class messageActivity extends AppCompatActivity {

    EditText number, text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        number = (EditText)findViewById(R.id.phoneNumber);
        text = (EditText)findViewById(R.id.textMessage);


    }

    public void buttonSend(View view)
    {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED)
        {
            MyMessage();
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},0);
        }


    }

    private void MyMessage() {
        String phoneNumber = number.getText().toString().trim();
        String textMessage = text.getText().toString().trim();

        if (!number.getText().toString().equals("") || !text.getText().toString().equals("")) {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, textMessage, null, null);

            Toast.makeText(this, "Message Sent!", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "Enter Number/Message!", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode)
        {
            case 0:

                if(grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    MyMessage();
                }
                else
                {
                    Toast.makeText(this, "You don't have Required Permissions!", Toast.LENGTH_SHORT).show();

                }
        }

    }
}
