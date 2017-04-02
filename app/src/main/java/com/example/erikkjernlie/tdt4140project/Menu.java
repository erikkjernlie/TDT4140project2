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

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    private Button register; //register button
    private Button explore; //explore button
    private Button aboutUnibot; //about button
    private FirebaseAuth firebaseAuth;
    private Button signOut;
    private Button aboutUs;
    private ImageView cogwheel;
    private Firebase mRef;
    private char gender;
    private double calculatedGrade;
    private int birthYear;
    private ArrayList<String> courses;
    private ArrayList<String> extraEducation;
    private int R2Grade;
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

        mRef = new Firebase("https://tdt4140project2.firebaseio.com/" +
                firebaseAuth.getCurrentUser().getUid());


        getInfoDatabase();
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

        if (this.gender == 'M') {
            img.setImageResource(R.drawable.man_selected);

        } else if (this.gender == 'F') {
            img.setImageResource(R.drawable.female_selected);

        } else {
            img.setVisibility(View.GONE);
        }

        if (this.birthYear != 0) {
            t2.setText("You are born in " + birthYear + ".");
        } else {
            t2.setVisibility(View.GONE);
        }

        if (this.courses != null) {
            t7.setText("You have had the following courses that gives extra points:");
            String array = this.courses.toString();
            array = array.substring(1, array.length()-1);
            t3.setText(array);
        } else {
            t3.setVisibility(View.GONE);
            t7.setVisibility(View.GONE);
        }
        if (this.R2Grade != 0) {
            t4.setText("You got an " + this.R2Grade + " in R2");
        } else {
            t4.setVisibility(View.GONE);
        }
        if (this.extraEducation != null) {
            t8.setText("You have also had:");
            String array = this.extraEducation.toString();
            array = array.substring(1, array.length()-1);
            t5.setText(array);
        } else {
            t8.setVisibility(View.GONE);
            t5.setVisibility(View.GONE);
        }

        if (calculatedGrade != 0.0) {
            t6.setText("You score is  " + calculatedGrade + ".");

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

    /*private void getInformation() {
        //Sends a StudyProgramInfo-object to the database (TEST)
        Firebase infoRef = new Firebase("https://tdt4140project2.firebaseio.com/Studies/");
        infoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    System.out.println("AAAAAA");
                    System.out.println(snapshot.getKey());
                    addStudyPrograms(snapshot.getKey(), snapshot.getValue(StudyProgramInfo.class));
                    System.out.println(snapshot.getValue(StudyProgramInfo.class).toString());
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }*/

    public void getInfoDatabase() {

        Firebase userInfoRef = new Firebase("https://tdt4140project2.firebaseio.com/Users/");

        userInfoRef.child(firebaseAuth.getCurrentUser().getUid()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("GGGGGG");
                        System.out.println(dataSnapshot.getValue(UserInfo.class));
                        setUser(dataSnapshot.getValue(UserInfo.class));
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
    }


    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public double getCalculatedGrade() {
        return calculatedGrade;
    }

    public void setCalculatedGrade(double calculatedGrade) {
        this.calculatedGrade = calculatedGrade;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    public ArrayList<String> getExtraEducation() {
        return extraEducation;
    }

    public void setExtraEducation(ArrayList<String> extraEducation) {
        this.extraEducation = extraEducation;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getR2Grade() {
        return R2Grade;
    }

    public void setR2Grade(int r2Grade) {
        R2Grade = r2Grade;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
