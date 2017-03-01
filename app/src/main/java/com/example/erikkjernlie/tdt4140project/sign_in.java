package com.example.erikkjernlie.tdt4140project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_in extends AppCompatActivity implements View.OnClickListener {

    private EditText logInEmail;
    private EditText logInPassword;
    private Button logInBtn;
    private FirebaseAuth firebaseAuth;
    private TextView registerText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        logInEmail = (EditText) findViewById(R.id.enterEmailAddress);
        logInPassword = (EditText) findViewById(R.id.enterPassword);

        logInBtn = (Button) findViewById(R.id.logInBtn);

        firebaseAuth = firebaseAuth.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(this);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInUser();
            }
        });
    }

    public void logInUser() {
        String email = logInEmail.getText().toString().trim();
        String password = logInPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //Write email again
            Toast.makeText(Sign_in.this, "Write email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //Write password again
            Toast.makeText(Sign_in.this, "Write password!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Sign_in.this, "Something went wrong, try again!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Sign_in.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Sign_in.this, Menu.class));
                    finish();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        registerText = (TextView) findViewById(R.id.registerText);
        if (v==registerText){
            Intent i = new Intent(Sign_in.this, Register_user.class);
            startActivity(i);
            finish();
        }
    }
}