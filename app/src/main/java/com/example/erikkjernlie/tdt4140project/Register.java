package com.example.erikkjernlie.tdt4140project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private TextView textView;
    private char gender;
    private double grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initGenderBtns();




    }




    public void initGenderBtns(){
        ImageButton femaleBtn = (ImageButton)findViewById(R.id.female);
        ImageButton maleBtn = (ImageButton)findViewById(R.id.male);
        femaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = 'F';
                Toast.makeText(Register.this, "Male", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.register_information);
            }
        });
        maleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = 'M';
                Toast.makeText(Register.this, "Female", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.register_information);
            }
        });

    }

    public char getGender() {
        return gender;
    }
}
