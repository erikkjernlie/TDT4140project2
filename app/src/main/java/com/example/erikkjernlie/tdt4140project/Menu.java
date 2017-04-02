/*  Menu
 *
 *  Menu for the app. Contains buttons to the different functions of uniBOT;
 *  ChatBot - explore, Slideshow_about_us - information about the team, SlideShow_about_unibot
 *  - information about the app and a sign out button - which signs the user out of the app and
 *  returns the user to the Sign_in acitivity.
 *  The menu contains an photoshopped image of the main building on Gløshaugen,
 *  with uniBOT's logo in the background.
 *
 *  Created by Erik Kjernlie
 *  Copyright © uniBOT
 */


package com.example.erikkjernlie.tdt4140project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {

    private Button register; //register button
    private Button explore; //explore button
    private Button aboutUnibot; //about button
    private FirebaseAuth firebaseAuth;
    private Button signOut;
    private Button aboutUs;
    private ImageView cogwheel;
    private Firebase mRef;
    private UserInfo user;


    public void initButtons() {
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
        signOut = (Button) findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth user = firebaseAuth.getInstance();
                user.signOut();
                Toast.makeText(Menu.this, "Signed out", Toast.LENGTH_SHORT).show();
                Intent d = new Intent(Menu.this, Sign_in.class);
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

        cogwheel = (ImageView) findViewById(R.id.cogwheel);
        cogwheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertSettings();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //initButtons();
        Firebase.setAndroidContext(Menu.this);

        //hvordan henter man info
        firebaseAuth = firebaseAuth.getInstance();

        mRef = new Firebase("https://tdt4140project2.firebaseio.com/Users/" +
                firebaseAuth.getCurrentUser().getUid());


        getUserInfoDatabase();
        initButtons();

        //uncomment this when we want the alert just to appear the first time the app is started

    }


    public void alertSettings() {
        final Dialog d = new Dialog(Menu.this);
        d.setContentView(R.layout.alertdialog_settings);
        d.setTitle("Settings");
        ImageView img = (ImageView) d.findViewById(R.id.gender_picture);

        TextView t2 = (TextView) d.findViewById(R.id.birthyear_settings);
        TextView t3 = (TextView) d.findViewById(R.id.cources_settings);
        TextView t4 = (TextView) d.findViewById(R.id.r2grade_settings);
        TextView t5 = (TextView) d.findViewById(R.id.extra_information_settings);
        TextView t6 = (TextView) d.findViewById(R.id.calculated_grade_settings);
        TextView t7 = (TextView) d.findViewById(R.id.courses_havehad);
        TextView t8 = (TextView) d.findViewById(R.id.extra_havehad);

        if (this.user.getGender() == 'M') {
            img.setImageResource(R.drawable.man_selected);

        } else if (this.user.getGender() == 'F') {
            img.setImageResource(R.drawable.female_selected);

        } else {
            img.setVisibility(View.GONE);
        }

        if (this.user.getBirthYear() != 0) {
            t2.setText("You are born in " + this.user.getBirthYear() + ".");
        } else {
            t2.setVisibility(View.GONE);
        }

        if (this.user.getCourses() != null) {
            t7.setText("You have had the following courses that gives extra points:");
            String array = this.user.getCourses().toString();
            array = array.substring(1, array.length() - 1);
            t3.setText(array);
        } else {
            t3.setVisibility(View.GONE);
            t7.setVisibility(View.GONE);
        }
        if (this.user.getR2Grade() != 0) {
            t4.setText("You got an " + this.user.getR2Grade() + " in R2");
        } else {
            t4.setVisibility(View.GONE);
        }
        if (this.user.getExtraEducation() != null) {
            t8.setText("You have also had:");
            String array = this.user.getExtraEducation().toString();
            array = array.substring(1, array.length() - 1);
            t5.setText(array);
        } else {
            t8.setVisibility(View.GONE);
            t5.setVisibility(View.GONE);
        }

        if (this.user.getCalculatedGrade() != 0.0) {
            t6.setText("You score is  " + this.user.getCalculatedGrade() + ".");

        } else {
            t6.setVisibility(View.GONE);
        }

        d.show();
        TextView change_password = (TextView) d.findViewById(R.id.change_password);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertRetrievePassword();
            }
        });
        TextView return_to_settings = (TextView) d.findViewById(R.id.return_to_settings);
        return_to_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });


    }

    public void alertRetrievePassword() {
        final Dialog e = new Dialog(Menu.this);
        e.setContentView(R.layout.alertdialog_password);
        e.setTitle("Insert e-mail");
        final EditText email_retrieve_password = (EditText) e.findViewById(R.id.email_retrieve_password);
        TextView confirm_email = (TextView) e.findViewById(R.id.confirm_email);
        confirm_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = email_retrieve_password.getText().toString();
                if (!email.equals("")) {
                    String email2 = (String) email_retrieve_password.toString();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email2);
                    Toast.makeText(Menu.this, "Instructions are sent to the requested e-mail", Toast.LENGTH_SHORT).show();
                    e.dismiss();
                } else {
                    Toast.makeText(Menu.this, "Type in your e-mail!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        e.show();
    }

    public void getUserInfoDatabase() {

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setUser(dataSnapshot.getValue(UserInfo.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public UserInfo getUser(){
        return this.user;
    }
}
