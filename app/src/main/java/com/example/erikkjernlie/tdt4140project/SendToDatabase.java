package com.example.erikkjernlie.tdt4140project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.Firebase;

public class SendToDatabase extends AppCompatActivity {

    Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_database);

        Firebase.setAndroidContext(SendToDatabase.this);


    }









}
