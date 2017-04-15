/*  Sign_in
 *
 *  Signing in the user. The user has the possibility to save other
 *  information about themselves in the add_information-activity.
 *
 *  Created by Erik Kjernlie and Jørgen Mortensen
 *  Copyright © uniBOT
 */

package com.example.erikkjernlie.tdt4140project;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class Sign_in extends AppCompatActivity {

    private EditText logInEmail;
    private EditText logInPassword;
    private Button logInBtn;
    private FirebaseAuth firebaseAuth;
    private Button switchLoginToRegister;
    private TextView forgotPassword;
    private EditText email_retrieve_password;
    private TextView confirm_email;
    private UserInfo user;
    private HashMap<String, StudyProgramInfo> studyPrograms;
    private HashMap<String, Union> unions;

    //the user can press outside the keyboard, and the keyboard will hide automatically
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Sign_in.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        firebaseAuth = firebaseAuth.getInstance();
        ProgressDialog progressDialog = new ProgressDialog(this);
        if (firebaseAuth.getCurrentUser() != null) {
            Toast.makeText(Sign_in.this, "Logging in...", Toast.LENGTH_LONG).show();
            getUserInfoDatabase();
            getStudyInfoDatabase();
            getUnionInfoDatabase();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //sender deg videre til homescreen
                    Intent homeIntent = new Intent(Sign_in.this, Menu.class);
                    startActivity(homeIntent);
                    Toast.makeText(Sign_in.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }, 3000);
        } else {
            setContentView(R.layout.activity_sign_in);
            studyPrograms = new HashMap<>();

            logInEmail = (EditText) findViewById(R.id.enterEmailAddress);
            logInPassword = (EditText) findViewById(R.id.enterPassword);

            logInEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus && !logInPassword.hasFocus()) {
                        hideKeyboard(v);
                    }
                }
            });
            logInPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus && !logInEmail.hasFocus()) {
                        hideKeyboard(v);
                    }
                }
            });

            initButtons();
            }
    }

    private void initButtons(){
        logInBtn = (Button) findViewById(R.id.logInBtn);

        switchLoginToRegister = (Button) findViewById(R.id.switchLoginToRegister);
        switchLoginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sign_in.this, Register_user.class);
                startActivity(i);
                finish();
            }
        });

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInUser();
            }
        });

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertRetrievePassword();
            }
        });
    }


    //logging in the user
    public void logInUser() {
        String email = logInEmail.getText().toString().trim();
        String password = logInPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //Write email again
            Toast.makeText(Sign_in.this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //Write password again
            Toast.makeText(Sign_in.this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(Sign_in.this, "Trying to connect...", Toast.LENGTH_SHORT).show();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    Toast.makeText(Sign_in.this, "Login unsuccessful, please try again", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Sign_in.this, "Logging in...", Toast.LENGTH_LONG).show();
                    getUserInfoDatabase();
                    getStudyInfoDatabase();
                    getUnionInfoDatabase();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //sender deg videre til homescreen
                            Intent homeIntent = new Intent(Sign_in.this, Menu.class);
                            startActivity(homeIntent);
                            Toast.makeText(Sign_in.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }, 5000);





                }
            }
        });

    }


    //if the user has forgotten the password, this is the alertDialog that appears
    //the user can write in the e-mail adress and change the password via his/her e-mail
    public void alertRetrievePassword() {
        final Dialog d = new Dialog(Sign_in.this);
        d.setContentView(R.layout.alertdialog_password);
        d.setTitle("Insert e-mail");
        email_retrieve_password = (EditText) d.findViewById(R.id.email_retrieve_password);
        confirm_email = (TextView) d.findViewById(R.id.confirm_email);
        confirm_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = email_retrieve_password.getText().toString();
                if (!email.equals("")) {
                    String email2 = (String) email_retrieve_password.toString();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email2);
                    Toast.makeText(Sign_in.this, "Instructions are sent to the requested e-mail", Toast.LENGTH_SHORT).show();
                    d.dismiss();
                } else {
                    Toast.makeText(Sign_in.this, "Type in your e-mail!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        d.show();
    }

    public void getUserInfoDatabase() {
        firebaseAuth = firebaseAuth.getInstance();

        Firebase mRef = new Firebase("https://tdt4140project2.firebaseio.com/Users/" +
                firebaseAuth.getCurrentUser().getUid());

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

    private void getStudyInfoDatabase() {
        //Sends a StudyProgramInfo-object to the database (TEST)
        Firebase infoRef = new Firebase("https://tdt4140project2.firebaseio.com/Studies/");
        infoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    addStudyPrograms(snapshot.getKey(), snapshot.getValue(StudyProgramInfo.class));
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    //Retrieving information about the unions at NTNU
    public void getUnionInfoDatabase() {
        Firebase unionRef = new Firebase("https://tdt4140project2.firebaseio.com/Unions/");
        unionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    addUnions(snapshot.getValue(Union.class));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void addStudyPrograms(String study, StudyProgramInfo info) {
        StudyProgramInfo.studyPrograms.put(study, info);
        UserInfo.userInfo.studyPrograms.put(study, info);
    }

    public void setUser(UserInfo user) {
        UserInfo.userInfo = user;
    }

    public UserInfo getUser(){
        return UserInfo.userInfo;
    }

    public void addUnions(Union union) {
        Union.unions.put(union.getName(), union);
    }


}