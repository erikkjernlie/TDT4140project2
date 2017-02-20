package com.example.erikkjernlie.tdt4140project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class Register extends AppCompatActivity {

    private TextView textView;
    private char gender;
    private double grade;
    private Firebase mRef;
    private Firebase mRefChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initGenderBtns();

        Firebase.setAndroidContext(Register.this);

        mRef = new Firebase("https://tdt4140project2.firebaseio.com/");

    }
    //hvis vi vil ha muligheten til å gå ET HAKK tilbake, så kan vi ikke bare ha setContentView, da må vi vise til en ny Activity
    //finner ut kjønn
    public void initGenderBtns(){
        ImageButton femaleBtn = (ImageButton)findViewById(R.id.female);
        ImageButton maleBtn = (ImageButton)findViewById(R.id.male);
        femaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = 'F';
                Toast.makeText(Register.this, "Female", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.q_grade);
                findOutGrade(); //sender videre til karakterer

            }
        });
        maleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = 'M';
                Toast.makeText(Register.this, "Male", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.q_grade);
                findOutGrade();
            }
        });

    }

    public void findOutGrade(){
        Button reg = (Button) findViewById(R.id.Registered);
        Button notReg = (Button) findViewById(R.id.notRegistered);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.calculated_grades);
                knowsGrade(); //hvis personen vet karakteren sendes man til denne metoden

            }
        });
        notReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.register_grades);
                //personen vet ikke karakteren sin
            }
        });

    }

    public void knowsGrade(){
        Button b = (Button)findViewById(R.id.button3);
        final EditText e = (EditText) findViewById(R.id.editText3);
        final TextView t = (TextView)findViewById(R.id.textView3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //her må vi ha med masse tester itilfelle input er string
                grade = Double.valueOf(e.getText().toString());
                t.setText(Double.toString(grade));
                Firebase mRefChild = mRef.child("Grade");
                mRefChild.setValue(grade);

            }
        });


    }


    public char getGender() {
        return gender;
    }
}

