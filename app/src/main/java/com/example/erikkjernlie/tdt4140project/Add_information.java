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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        initButtons();
        numberPicker();

    }


    public void initButtons() {
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText gradeSingle = (EditText) findViewById(R.id.gradeSingle);
                grades = gradeSingle.getText().toString();
                calculatedGrade = grade_calculation(grades);
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
            gender = 'M';
        } else if (isPressedFemale) {
            gender = 'F';
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

    public double grade_calculation(String s) {
        double grade_calculated = 0;
        int counter = 0;
        String newS = s.replace(",", "").trim();
        for (int i = 0; i < newS.length(); i++) {
            grade_calculated += Character.getNumericValue(newS.charAt(i));
            counter++;
        }


        if (extra_education_array.size() > 0) {
            this.extraPoints = 2;
        }
        grade_calculated = (grade_calculated*10 / counter) + agePoints(year) + extraPoints;
        return grade_calculated;
    }


    private List<String> courses_array = new ArrayList<String>(); //list for storing the courses
    private final CharSequence[] courses ={"Matematikk S1","Matematikk S2","Matematikk R1","Matematikk R2","Fysikk 1","Fysikk 2","Kjemi 1","Kjemi 2","Biologi 1","Biologi 2","Geofag 1", "Geofag 2", "Informasjonsteknologi 1", "Informasjonsteknologi 2", "Teknologi og forskningslære 1", "Teknologi og forskningslære 2", "VG3 naturbruk"}; //items in the alertdialog that displays checkboxes
    private final boolean checked_state_courses[]={false,false,false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};



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

    private List<String> extra_education_array = new ArrayList<String>(); //list for storing the courses
    private final CharSequence[] extra_education ={"Folkehøgskole", "Militærtjeneste", "Siviltjeneste", "Høyere utdanning"}; //items in the alertdialog that displays checkboxes
    private final boolean checked_state_education[]={false,false,false, false};



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
