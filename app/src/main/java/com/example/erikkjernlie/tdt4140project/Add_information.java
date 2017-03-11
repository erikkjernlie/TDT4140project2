package com.example.erikkjernlie.tdt4140project;

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
    List<String> selectedCourses;
    List<String> selectedExtraPoints = new ArrayList<String>();
    String grades;
    private int extraPoints = 0;
    MultiSelectSpinner mySpin_extraPoints;
    MultiSelectSpinner mySpin_courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        String[] strings = {"Førstegangstjeneste", "Høgskole" };
        mySpin_extraPoints = (MultiSelectSpinner)findViewById(R.id.my_spin_extrapoints);
        mySpin_extraPoints.setItems(strings);

        String[] strings2 = { "Matematikk R1", "Matematikk R2", "Fysikk 1", "Fysikk 2", "Informasjonsteknologi","Matematikk R1", "Matematikk R2", "Fysikk 1", "Fysikk 2","Matematikk R1", "Matematikk R2", "Fysikk 1", "Fysikk 2" };
        mySpin_courses = (MultiSelectSpinner)findViewById(R.id.my_spin_courses);
        mySpin_courses.setItems(strings2);

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

                mySpin_courses.performClick();
                selectedCourses = mySpin_courses.getSelectedStrings();
            }
        });

        dropdownExtraPoints = (Button) findViewById(dropdownExtrapoints);
        dropdownExtraPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mySpin_extraPoints.performClick();
                selectedExtraPoints = mySpin_extraPoints.getSelectedStrings();
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


        if (selectedExtraPoints.size() != 0) {
            this.extraPoints = 2 * selectedExtraPoints.size();
        }
        grade_calculated = (grade_calculated*10 / counter) + agePoints(year) + extraPoints;
        return grade_calculated;
    }


}
