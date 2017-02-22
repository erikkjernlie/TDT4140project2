package com.example.erikkjernlie.tdt4140project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import static com.example.erikkjernlie.tdt4140project.R.drawable.robot;

public class Menu extends AppCompatActivity {

    private Button but1; //register button
    private Button but2; //explore button
    private Button but3; //about button

    public static final String PREFS_NAME = "MyPrefsFile";

    //få denne til å kjøre første gangen, nå kjører den hele jævla tiden
    //pop-up skjermen, også alert/overlay
    private void alert(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.layout.overlay);
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder1.setView(inflater.inflate(R.layout.overlay, null));
        builder1.setTitle("Welcome to uniBOT. Please confirm that every thursdag is" +
                " a DT-thursdag");
        builder1.setIcon(robot);

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
        but1 = (Button) findViewById(R.id.but1);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Menu.this, Register.class);
                startActivity(b);
            }
        });
        but2 = (Button) findViewById(R.id.but2);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(Menu.this, ChatBot.class);
                startActivity(c);
            }
        });
        but3 = (Button) findViewById(R.id.but3);
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent(Menu.this, Slideshow.class);
                startActivity(d);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initButtons();
        //uncomment this when we want the alert just to appear the first time the app is started
        //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        //boolean dialogShown = settings.getBoolean("dialogShown", false);

        //if (!dialogShown) {
            // AlertDialog code here
        alert();

         //   SharedPreferences.Editor editor = settings.edit();
         //   editor.putBoolean("dialogShown", true);
         //   editor.commit();
        //}
    }
}
