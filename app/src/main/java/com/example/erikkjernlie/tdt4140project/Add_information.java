package com.example.erikkjernlie.tdt4140project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.support.annotation.NonNull;
import android.widget.EditText;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import java.util.HashMap;
import java.util.Map;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.erikkjernlie.tdt4140project.R.id.dropdownExtrapoints;

public class Add_information extends AppCompatActivity{

    private ImageButton female;
    private double calculatedGrade;
    boolean isPressedFemale = false;
    public Date today = new Date();
    boolean isPressedMan = false;
    private ImageButton man;
    private char gender;
    private int minimum_born_year = 1985; //makes sure that the user has a birthdate, if the user does not spin the numberspinner
    private int year = minimum_born_year;
    Button dropdownCourses;
    Button dropdownExtraPoints;
    Button submit;
    String grades;
    private int extraPoints = 0;
    private EditText gradeSingle;
    private Firebase mRootRef;
    private Firebase mRef;
    private FirebaseAuth firebaseAuth;

    private List<String> courses_array = new ArrayList<String>(); //list for storing the courses
    private final CharSequence[] courses ={"Matematikk S1","Matematikk S2","Matematikk R1","Matematikk R2","Fysikk 1","Fysikk 2","Kjemi 1","Kjemi 2","Biologi 1","Biologi 2","Geofag 1", "Geofag 2", "Informasjonsteknologi 1", "Informasjonsteknologi 2", "Teknologi og forskningslære 1", "Teknologi og forskningslære 2", "VG3 naturbruk", "Fremmedspråk 3"}; //items in the alertdialog that displays checkboxes
    private final boolean checked_state_courses[]={false,false,false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

    private List<String> extra_education_array = new ArrayList<String>(); //list for storing the courses
    private final CharSequence[] extra_education ={"Folkehøgskole", "Militærtjeneste", "Siviltjeneste", "Høyere utdanning"}; //items in the alertdialog that displays checkboxes
    private final boolean checked_state_education[]={false,false,false, false};
    private HashMap<String, Double> fagbase = new HashMap<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        gradeSingle = (EditText) findViewById(R.id.gradeSingle);

        Firebase.setAndroidContext(Add_information.this);

        firebaseAuth = firebaseAuth.getInstance();


        mRef = new Firebase("https://tdt4140project2.firebaseio.com/" +
                firebaseAuth.getCurrentUser().getUid());

        mRootRef = new Firebase("https://tdt4140project2.firebaseio.com/" +
                firebaseAuth.getCurrentUser().getUid());


        initFagbase();
        initButtons();
        numberPicker();
    }

    private void initFagbase() {
        fagbase.put("Matematikk S1", 0.5);
        fagbase.put("Matematikk S2", 0.5);
        fagbase.put("Matematikk R1", 0.5);
        fagbase.put("Matematikk R2", 1.0);
        fagbase.put("Fysikk 1", 0.5);
        fagbase.put("Fysikk 2", 1.0);
        fagbase.put("Kjemi 1", 0.5);
        fagbase.put("Kjemi 2", 0.5);
        fagbase.put("Biologi 1", 0.5);
        fagbase.put("Biologi 2", 0.5);
        fagbase.put("Geofag 1", 0.5);
        fagbase.put("Geofag 2", 0.5);
        fagbase.put("Informasjonsteknologi 1", 0.5);
        fagbase.put("Informasjonsteknologi 2", 0.5);
        fagbase.put("Teknologi og forskningslære 1", 0.5);
        fagbase.put("Teknologi og forskningslære 2", 0.5);
        fagbase.put("Vg3 Naturbruk", 0.5);
        fagbase.put("Fremmedspråk 3", 1.0);
    }

    private void storeVariables() {
        //Store averageGrade
        Firebase mRefChildGrade = mRootRef.child("CalculatedGrade");
        mRefChildGrade.setValue(this.calculatedGrade);

        //Store gender
        Firebase mRefChildGender = mRootRef.child("Gender");
        mRefChildGender.setValue(this.gender);

        //Store courses
        Firebase mRefChildCourses = mRootRef.child("Courses");
        mRefChildCourses.setValue(this.courses_array);

        //Store extra education
        Firebase mRefChildExEd = mRootRef.child("Extra education");
        mRefChildExEd.setValue(extra_education_array);

        //Store birthyear
        Firebase mRefChildYear = mRootRef.child("BirthYear");
        mRefChildYear.setValue(year);
    }


    public void initButtons() {
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressedMan) {
                    gender = 'M';
                } else if (isPressedFemale) {
                    gender = 'F';
                } //the user can select none of them, but then it will be an error at the end
                final EditText gradeSingle = (EditText) findViewById(R.id.gradeSingle);
                grades = gradeSingle.getText().toString();
                calculatedGrade = grade_calculation(grades);
                storeVariables();
                Toast.makeText(Add_information.this, "Your average grade is: " + calculatedGrade, Toast.LENGTH_LONG).show();
                Intent i = new Intent(Add_information.this, Menu.class);
                startActivity(i);
            }
        });
        female = (ImageButton) findViewById(R.id.female);
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressedFemale) {
                    female.setImageResource(R.drawable.female_notselected);
                } else {
                    //this is because you can't select male and female
                    female.setImageResource(R.drawable.female_selected);
                    if (isPressedMan) {
                        man.setImageResource(R.drawable.man_notselected);
                        isPressedMan = false;
                    }


                }
                isPressedFemale = !isPressedFemale;
            }
        });
        man = (ImageButton) findViewById(R.id.man);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressedMan) {

                    man.setImageResource(R.drawable.man_notselected);
                    //this is because you can't select male and female
                } else {
                    man.setImageResource(R.drawable.man_selected);
                    if (isPressedFemale) {
                        female.setImageResource(R.drawable.female_notselected);
                        isPressedFemale = false;
                    }

                }
                isPressedMan = !isPressedMan;
            }
        });
        if (isPressedMan) {
            this.gender = 'M';
        } else if (isPressedFemale) {
            this.gender = 'F';
        } //the user can select none of them, but then it will be an error at the end

        dropdownCourses = (Button) findViewById(R.id.dropdownCourses);
        dropdownCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courses_array = new ArrayList<String>();
                //resets if the user goes in and out
                alertCourses();
            }
        });

        dropdownExtraPoints = (Button) findViewById(dropdownExtrapoints);
        dropdownExtraPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extra_education_array = new ArrayList<String>();
                alertExtraEducation();
            }
        });
    }

    private void numberPicker(){
        NumberPicker np = (NumberPicker) findViewById(R.id.np);


        //Set the minimum value of NumberPicker
        np.setMinValue(minimum_born_year);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(2002);
        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);
        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                year = newVal;

            }
        });

    }



    protected int agePoints(int birthYear) {
        if (birthYear < minimum_born_year) {
            throw new IllegalArgumentException();
        }
        int points = 0;
        points = 2 * ((today.getYear() - birthYear + 1900) - 19);
        System.out.println(points);
        if (points > 8) {
            points = 8;
        } else if (points < 0) {
            points = 0;
        }
        return points;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public double grade_calculation(String s) {
        double grade_calculated = 0;
        double realFagPoints = 0;
        int counter = 0;
        String newS = s.replace(",", "").trim();
        if (newS.contains(".")) {
            grade_calculated = new Double(newS).doubleValue()*10;
            grade_calculated = round(grade_calculated, 2);

            if (extra_education_array.size() > 0) {
                this.extraPoints = 2;
            }

            for (String course : courses_array) {
                realFagPoints += fagbase.get(course);
            }

            if (realFagPoints > 4) {
                realFagPoints = 4;
            }
            grade_calculated += agePoints(year) + extraPoints + realFagPoints;
            return grade_calculated;
        } else {

            for (int i = 0; i < newS.length(); i++) {
                grade_calculated += Character.getNumericValue(newS.charAt(i));
                counter++;
            }

            if (extra_education_array.size() > 0) {
                this.extraPoints = 2;
            }

            for (String course : courses_array) {
                realFagPoints += fagbase.get(course);
            }

            if (realFagPoints > 4) {
                realFagPoints = 4;
            }

            grade_calculated = (grade_calculated*10 / counter) + agePoints(year) + extraPoints + realFagPoints;

            return grade_calculated;

        }

    }


    //not fixed if the user selects some courses, presses ok, and opens it again; then it will be empty.
    private void alertCourses() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Add_information.this)
                .setTitle("Choose courses")
                .setMultiChoiceItems(courses, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        checked_state_courses[which] = isChecked;
                    }
                }).setPositiveButton("I have selected all my courses", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i < (courses.length); i++) {
                            if (checked_state_courses[i] == true) {
                                courses_array.add(courses[i].toString());
                            }
                        }

                        Toast.makeText(getApplicationContext(), "Selected courses: "
                                + courses_array.toString(), Toast.LENGTH_LONG).show();


                        //clears the array used to store checked state
                        for (int i = 0; i < checked_state_courses.length; i++) {
                            if (checked_state_courses[i] == true) {
                                checked_state_courses[i] = false;
                            }
                        }

                        //dialog.dismiss(); if we want it to be able to close the window if the user presses outside the alert
                    }
                });
        AlertDialog alertdialog1 = builder1.create();
        alertdialog1.show();
    }

    //not fixed if the user selects some courses, presses ok, and opens it again; then it will be empty.
    private void alertExtraEducation() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(Add_information.this)
                .setTitle("Choose education")
                .setMultiChoiceItems(extra_education, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        checked_state_education[which] = isChecked;
                    }
                }).setPositiveButton("I have selected all my education", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i < (extra_education.length); i++) {
                            if (checked_state_education[i] == true) {
                                extra_education_array.add(extra_education[i].toString());
                            }
                        }

                        Toast.makeText(getApplicationContext(), "Selected education: "
                                + extra_education_array.toString(), Toast.LENGTH_LONG).show();


                        //clears the array used to store checked state
                        for (int i = 0; i < checked_state_education.length; i++) {
                            if (checked_state_education[i] == true) {
                                checked_state_education[i] = false;
                            }
                        }

                        //dialog.dismiss(); if we want it to be able to close the window if the user presses outside the alert
                    }
                });
        AlertDialog alertdialog2 = builder2.create();
        alertdialog2.show();
    }

}
