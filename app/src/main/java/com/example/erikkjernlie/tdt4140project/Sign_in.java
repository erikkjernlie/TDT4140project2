package com.example.erikkjernlie.tdt4140project;

import android.app.Dialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_in extends AppCompatActivity  {

    private EditText logInEmail;
    private EditText logInPassword;
    private Button logInBtn;
    private FirebaseAuth firebaseAuth;
    private Button switchLoginToRegister;
    private TextView forgotPassword;
    private EditText email_retrieve_password;
    private TextView confirm_email;


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Sign_in.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

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

        logInBtn = (Button) findViewById(R.id.logInBtn);

        firebaseAuth = firebaseAuth.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(this);

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

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Sign_in.this, "Login unsuccessful, please try again", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(Sign_in.this, Menu.class));
                    finish();
                }
            }
        });

    }

    public void alertRetrievePassword(){
        final Dialog d = new Dialog(Sign_in.this);
        d.setContentView(R.layout.alertdialog_password);
        d.setTitle("Insert e-mail");
        email_retrieve_password = (EditText) d.findViewById(R.id.email_retrieve_password);
        confirm_email = (TextView) d.findViewById(R.id.confirm_email);
        confirm_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    String email = email_retrieve_password.getText().toString();
                //her er emailen, retrieve passordet for faen
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email);
                    Toast.makeText(Sign_in.this, "Instructions are sent to the requested e-mail", Toast.LENGTH_SHORT).show();
                    d.dismiss();


            }
        });
        d.show();
    }


}