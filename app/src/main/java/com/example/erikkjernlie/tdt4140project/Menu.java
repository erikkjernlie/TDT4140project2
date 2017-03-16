package com.example.erikkjernlie.tdt4140project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    private Button register; //register button
    private Button explore; //explore button
    private Button aboutUnibot; //about button
    private Button aboutUs;

    public static final String PREFS_NAME = "MyPrefsFile";

    //pop-up screen, appears only the first time the app is running
    private void alert(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.layout.overlay);
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder1.setView(inflater.inflate(R.layout.overlay, null));
        builder1.setTitle("Welcome to uniBOT");


        builder1.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void initButtons(){
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Menu.this, Add_information.class);
                startActivity(b);
            }
        });
        explore = (Button) findViewById(R.id.explore);
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(Menu.this, ChatBot.class);
                startActivity(c);
            }
        });
        aboutUnibot = (Button) findViewById(R.id.aboutUnibot);
        aboutUnibot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent(Menu.this, Slideshow_about_unibot.class);
                startActivity(d);
            }
        });
        aboutUs = (Button) findViewById(R.id.aboutUs);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(Menu.this, Slideshow_about_us.class);
                startActivity(e);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initButtons();
        //uncomment this when we want the alert just to appear the first time the app is started
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        boolean dialogShown = settings.getBoolean("dialogShown", false);

        if (!dialogShown) {
            // AlertDialog code here
        alert();

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("dialogShown", true);
            editor.commit();
        }
    }
}
