/*  Register_user
 *
 *  Register the user in firebase.
 *
 *  Created by Jørgen Mortensen and Erik Kjernlie
 *  Copyright © uniBOT
 */


package com.example.erikkjernlie.tdt4140project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;


public class Register_user extends AppCompatActivity {

    private EditText enterEmailAddress;
    private EditText enterPassword1;
    private EditText enterPassword2;
    private Button registerBtn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Firebase mRef;
    private Button switchRegisterToLogin;

    //the user can press outside the keyboard, and the keyboard will hide automatically
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Register_user.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        FirebaseApp.initializeApp(this);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Register_user.this);
        initButtons();
    }

    private void initButtons(){
        enterEmailAddress = (EditText) findViewById(R.id.enterEmailAddress);
        enterPassword1 = (EditText) findViewById(R.id.enterPassword1);
        enterPassword1 = (EditText) findViewById(R.id.enterPassword1);
        enterPassword2 = (EditText) findViewById(R.id.enterPassword2);

        enterEmailAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !enterPassword1.hasFocus() && !enterPassword2.hasFocus()) {
                    hideKeyboard(v);
                }
            }
        });
        enterPassword1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !enterEmailAddress.hasFocus() && !enterPassword2.hasFocus()) {
                    hideKeyboard(v);
                }
            }
        });
        enterPassword2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !enterEmailAddress.hasFocus() && !enterPassword1.hasFocus()) {
                    hideKeyboard(v);
                }
            }
        });

        registerBtn = (Button) findViewById(R.id.registerBtn);

        switchRegisterToLogin = (Button) findViewById(R.id.switchRegisterToLogin);
        switchRegisterToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register_user.this, Sign_in.class);
                startActivity(i);
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void registerUser() {
        String email = enterEmailAddress.getText().toString().trim();
        String password1 = enterPassword1.getText().toString().trim();
        String password2 = enterPassword2.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //Write email again
            Toast.makeText(Register_user.this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password1)) {
            //Write password again
            Toast.makeText(Register_user.this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password2)) {
            //Write password again
            Toast.makeText(Register_user.this, "You have to confirm your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(password1.equals(password2))) {
            //Write password again
            Toast.makeText(Register_user.this, "The password fields do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(Register_user.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Successfull user registration
                    Toast.makeText(Register_user.this, "User was successfully registered", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(Register_user.this, Add_information.class);
                    Firebase.setAndroidContext(Register_user.this);

                    firebaseAuth = firebaseAuth.getInstance();
                    mRef = new Firebase("https://tdt4140project2.firebaseio.com/Users/" +
                            firebaseAuth.getCurrentUser().getUid());
                    storeVariables();
                    startActivity(intent);
                } else {
                    //Must try again
                    Toast.makeText(Register_user.this,"The user already exists or the password does not contain at "
                            + "least 6 characters", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    //creates initiated information to make sure the
    private void storeVariables() {
        UserInfo user = new UserInfo(1995, 0.0, 0, 0, 0, 0, 0, 0, null,
                null, '\u0000', 0, new ArrayList<>(Arrays.asList("Studies")));
        UserInfo.userInfo = user;
        mRef.setValue(user);
    }
}
