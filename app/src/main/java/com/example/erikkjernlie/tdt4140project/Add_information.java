/*  Add_information
 *
 *  This class is for adding information regarding the user's grades
 *  Allows the user to add gender, r2grade, average grade, separate grades,
 *  courses that gives extra points, age points and extra education that gives extra points.
 *
 *  Created by Jørgen Mortensen
 *  Copyright © uniBOT
 */


package com.example.erikkjernlie.tdt4140project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.erikkjernlie.tdt4140project.R.id.confirmr2;
import static com.example.erikkjernlie.tdt4140project.R.id.dropdownExtrapoints;

public class Add_information extends AppCompatActivity {

    private ImageButton female;
    private double calculatedGrade;
    boolean isPressedFemale = false;
    public Date today = new Date();
    boolean isPressedMan = false;
    private ImageButton man;
    private char gender;
    private int minimum_born_year = 1950; //makes sure that the user has a birthdate, if the user does not spin the numberspinner
    private int year = 1995;
    private Button dropdownCourses;
    private Button dropdownExtraPoints;
    private Button submit;
    private double temporaryGrade;
    private int R2Grade = 0;
    private int extraPoints = 0;
    private Firebase mRef;
    private FirebaseAuth firebaseAuth;
    private Button grade;
    private TextView r2grade_btn;
    private Button averageBtn;
    private Button plus1;
    private Button minus1;
    private Button plus2;
    private Button minus2;
    private Button plus3;
    private Button minus3;
    private Button plus4;
    private Button minus4;
    private Button plus5;
    private Button minus5;
    private Button plus6;
    private Button minus6;
    private TextView number1grade_textview;
    private TextView number2grade_textview;
    private TextView number3grade_textview;
    private TextView number4grade_textview;
    private TextView number5grade_textview;
    private TextView number6grade_textview;
    private int number1grade = 0;
    private int number2grade = 0;
    private int number3grade = 0;
    private int number4grade = 0;
    private int number5grade = 0;
    private int number6grade = 0;
    private TextView cancel;
    private TextView add_grades;
    private EditText add_grades_text;
    private TextView add_average_grade;


    private ArrayList<String> coursesArray = new ArrayList<String>(); //list for storing the courses
    private final CharSequence[] courses = {"Matematikk S1", "Matematikk S2", "Matematikk R1", "Matematikk R2", "Fysikk 1", "Fysikk 2", "Kjemi 1", "Kjemi 2", "Biologi 1", "Biologi 2", "Geofag 1", "Geofag 2", "Informasjonsteknologi 1", "Informasjonsteknologi 2", "Teknologi og forskningslære 1", "Teknologi og forskningslære 2", "VG3 Naturbruk", "Fremmedspråk 3"}; //items in the alertdialog that displays checkboxes
    private final boolean checkedStateCourses[] = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

    private ArrayList<String> extraEducationArray = new ArrayList<String>(); //list for storing the courses
    private final CharSequence[] extraEducation = {"Folkehøgskole", "Militærtjeneste", "Siviltjeneste", "Høyere utdanning"}; //items in the alertdialog that displays checkboxes
    private final boolean checkedStateEducation[] = {false, false, false, false};
    private HashMap<String, Double> fagbase = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);


        Firebase.setAndroidContext(Add_information.this);

        firebaseAuth = firebaseAuth.getInstance();

        mRef = new Firebase("https://tdt4140project2.firebaseio.com/Users/" +
                firebaseAuth.getCurrentUser().getUid());

        LinearLayout l = (LinearLayout) findViewById(R.id.linear_add_information);
        l.requestFocus();

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
        fagbase.put("VG3 Naturbruk", 0.5);
        fagbase.put("Fremmedspråk 3", 1.0);
    }

    private void storeVariables() {
        UserInfo user = new UserInfo(this.year, this.calculatedGrade, this.coursesArray,
                this.extraEducationArray, this.gender, this.R2Grade);

        mRef.setValue(user);

        /*//Store averageGrade
        Firebase mRefChildGrade = mRootRef.child("CalculatedGrade");
        mRefChildGrade.setValue(this.calculatedGrade);

        //Store gender
        Firebase mRefChildGender = mRootRef.child("Gender");
        mRefChildGender.setValue(this.gender);

        //Store courses
        Firebase mRefChildCourses = mRootRef.child("Courses");
        mRefChildCourses.setValue(this.coursesArray);

        //Store extra education
        Firebase mRefChildExEd = mRootRef.child("Extra education");
        mRefChildExEd.setValue(this.extraEducationArray);

        //Store birthyear
        Firebase mRefChildYear = mRootRef.child("BirthYear");
        mRefChildYear.setValue(this.year);

        //Store R2Grade
        Firebase mRefChildR2Grade = mRootRef.child("R2Grade");
        mRefChildR2Grade.setValue(this.R2Grade);*/
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
                }

                calculatedGrade = grade_calculation();
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
                coursesArray = new ArrayList<String>();
                //resets if the user goes in and out
                alertCourses();
            }
        });

        dropdownExtraPoints = (Button) findViewById(dropdownExtrapoints);
        dropdownExtraPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraEducationArray = new ArrayList<String>();
                alertExtraEducation();
            }
        });
        grade = (Button) findViewById(R.id.gradeBtn);
        grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertGrades();
            }
        });
        averageBtn = (Button) findViewById(R.id.averageBtn);
        averageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertAverageGrades();
            }
        });
    }


    private void numberPicker() {
        NumberPicker np = (NumberPicker) findViewById(R.id.np);
        //Sets the minimum value of NumberPicker
        np.setMinValue(minimum_born_year);
        //Sets the maximum value of NumberPicker
        np.setMaxValue(2017);
        //Sets the start value of Numberpicker
        np.setValue(1995);
        np.setWrapSelectorWheel(true);
        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected number from picker
                year = newVal;

            }
        });

        np.clearFocus();

    }

    //calculates age points
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

    //round the calculated grade to two decimals
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    //calculates the enitre grade
    public double grade_calculation() {
        double grade_calculated = 0;
        double realFagPoints = 0;

        grade_calculated = (temporaryGrade) * 10;
        grade_calculated = round(grade_calculated, 2);

        if (extraEducationArray.size() > 0) {
            this.extraPoints = 2;
        }

        for (String course : coursesArray) {
            realFagPoints += fagbase.get(course);
        }

        if (realFagPoints > 4) {
            realFagPoints = 4;
        }
        grade_calculated += (agePoints(year) + extraPoints + realFagPoints);
        return grade_calculated;


    }

    //alertDialog for selecting courses
    private void alertCourses() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Add_information.this)
                .setTitle("Choose courses")
                .setMultiChoiceItems(courses, checkedStateCourses, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        checkedStateCourses[which] = isChecked;
                    }
                }).setPositiveButton("I have selected all my courses", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i < (courses.length); i++) {
                            if (checkedStateCourses[i] == true) {
                                coursesArray.add(courses[i].toString());
                            }
                        }

                        Toast.makeText(getApplicationContext(), "Selected courses: "
                                + coursesArray.toString(), Toast.LENGTH_SHORT).show();


                        if (coursesArray.contains("Matematikk R2")) {
                            alertR2Grade();
                        }

                        //alertdialog_r2grade.dismiss(); if we want it to be able to close the window if the user presses outside the alert
                    }
                });
        AlertDialog alertdialog1 = builder1.create();
        alertdialog1.show();
    }

    //alertDialog for selecting extra education
    private void alertExtraEducation() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(Add_information.this)
                .setTitle("Choose education")
                .setMultiChoiceItems(extraEducation, checkedStateEducation, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        checkedStateEducation[which] = isChecked;
                    }
                }).setPositiveButton("I have selected all my education", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i < (extraEducation.length); i++) {
                            if (checkedStateEducation[i] == true) {
                                extraEducationArray.add(extraEducation[i].toString());
                            }
                        }

                        Toast.makeText(getApplicationContext(), "Selected education: "
                                + extraEducationArray.toString(), Toast.LENGTH_SHORT).show();


                        //alertdialog_r2grade.dismiss(); if we want it to be able to close the window if the user presses outside the alert
                    }
                });
        AlertDialog alertdialog2 = builder2.create();
        alertdialog2.show();
    }


    //alertDialog for the R2-grade
    public void alertR2Grade() {
        final Dialog d = new Dialog(Add_information.this);
        d.setTitle("Choose your R2-character");
        d.setContentView(R.layout.alertdialog_r2grade);
        r2grade_btn = (TextView) d.findViewById(confirmr2);

        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(6); // max value 6
        np.setMinValue(1);   // min value 1
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                R2Grade = newVal;
            }
        });
        r2grade_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Grade " + R2Grade + " registered", Toast.LENGTH_SHORT).show();
                d.dismiss(); // dismiss the alertdialog_r2grade
            }
        });
        d.show();


    }

    //alertDialog for setting grades
    public void alertGrades() {
        final Dialog d = new Dialog(Add_information.this);
        d.setContentView(R.layout.alertdialog_grades);
        d.setTitle("Set your grade");

        add_grades = (TextView) d.findViewById(R.id.add_grades);
        cancel = (TextView) d.findViewById(R.id.cancel);

        add_grades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalGrades = number1grade + number2grade + number3grade + number4grade + number5grade + number6grade;

                if ((totalGrades) == 0) {
                    temporaryGrade = 0;
                } else {
                    double totalScore = (1 * number1grade + 2 * number2grade + 3 * number3grade + 4 * number4grade + 5 * number5grade + 6 * number6grade);
                    Toast.makeText(getApplicationContext(), "Grades saved.", Toast.LENGTH_SHORT).show();
                    temporaryGrade = (totalScore / (totalGrades));

                }
                d.dismiss();
            }
        });


        minus1 = (Button) d.findViewById(R.id.minus1);
        plus1 = (Button) d.findViewById(R.id.plus1);
        number1grade_textview = (TextView) d.findViewById(R.id.number1grade);
        number1grade_textview.setText(Integer.toString(number1grade));

        minus2 = (Button) d.findViewById(R.id.minus2);
        plus2 = (Button) d.findViewById(R.id.plus2);
        number2grade_textview = (TextView) d.findViewById(R.id.number2grade);
        number2grade_textview.setText(Integer.toString(number2grade));

        minus3 = (Button) d.findViewById(R.id.minus3);
        plus3 = (Button) d.findViewById(R.id.plus3);
        number3grade_textview = (TextView) d.findViewById(R.id.number3grade);
        number3grade_textview.setText(Integer.toString(number3grade));

        minus3 = (Button) d.findViewById(R.id.minus3);
        plus3 = (Button) d.findViewById(R.id.plus3);
        number3grade_textview = (TextView) d.findViewById(R.id.number3grade);
        number3grade_textview.setText(Integer.toString(number3grade));

        minus4 = (Button) d.findViewById(R.id.minus4);
        plus4 = (Button) d.findViewById(R.id.plus4);
        number4grade_textview = (TextView) d.findViewById(R.id.number4grade);
        number4grade_textview.setText(Integer.toString(number4grade));

        minus5 = (Button) d.findViewById(R.id.minus5);
        plus5 = (Button) d.findViewById(R.id.plus5);
        number5grade_textview = (TextView) d.findViewById(R.id.number5grade);
        number5grade_textview.setText(Integer.toString(number5grade));

        minus6 = (Button) d.findViewById(R.id.minus6);
        plus6 = (Button) d.findViewById(R.id.plus6);
        number6grade_textview = (TextView) d.findViewById(R.id.number6grade);
        number6grade_textview.setText(Integer.toString(number6grade));

        minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number1grade--;
                if (number1grade < 0) {
                    number1grade = 0;
                }
                number1grade_textview.setText(Integer.toString(number1grade));
            }
        });

        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number1grade++;
                number1grade_textview.setText(Integer.toString(number1grade));
            }
        });

        minus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number2grade--;
                if (number2grade < 0) {
                    number2grade = 0;
                }
                number2grade_textview.setText(Integer.toString(number2grade));
            }
        });

        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number2grade++;
                number2grade_textview.setText(Integer.toString(number2grade));
            }
        });

        minus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number3grade--;
                if (number3grade < 0) {
                    number3grade = 0;
                }
                number3grade_textview.setText(Integer.toString(number3grade));
            }
        });

        plus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number3grade++;
                number3grade_textview.setText(Integer.toString(number3grade));
            }
        });

        minus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number4grade--;
                if (number4grade < 0) {
                    number4grade = 0;
                }
                number4grade_textview.setText(Integer.toString(number4grade));
            }
        });

        plus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number4grade++;
                number4grade_textview.setText(Integer.toString(number4grade));
            }
        });

        minus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number5grade--;
                if (number5grade < 0) {
                    number5grade = 0;
                }
                number5grade_textview.setText(Integer.toString(number5grade));
            }
        });

        plus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number5grade++;
                number5grade_textview.setText(Integer.toString(number5grade));
            }
        });

        minus6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number6grade--;
                if (number6grade < 0) {
                    number6grade = 0;
                }
                number6grade_textview.setText(Integer.toString(number6grade));
            }
        });

        plus6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number6grade++;
                number6grade_textview.setText(Integer.toString(number6grade));
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number1grade = 0;
                number2grade = 0;
                number3grade = 0;
                number4grade = 0;
                number5grade = 0;
                number6grade = 0;
                number1grade_textview.setText(Integer.toString(number1grade));
                number2grade_textview.setText(Integer.toString(number2grade));
                number3grade_textview.setText(Integer.toString(number3grade));
                number4grade_textview.setText(Integer.toString(number4grade));
                number5grade_textview.setText(Integer.toString(number5grade));
                number6grade_textview.setText(Integer.toString(number6grade));

            }
        });


        d.show();


    }


    //alertDialog for writing in already calculated grade
    public void alertAverageGrades() {
        final Dialog d = new Dialog(Add_information.this);
        d.setContentView(R.layout.alertdialog_averagegrade);
        d.setTitle("Set your averageGrade");
        add_grades_text = (EditText) d.findViewById(R.id.add_grades_text);
        add_average_grade = (TextView) d.findViewById(R.id.add_average_grade);
        add_average_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String grade = add_grades_text.getText().toString();
                    if (grade.contains(",")) {
                        grade = grade.replaceAll(",", ".");

                    }
                    double num = Double.parseDouble(grade);
                    if (num > 0 && num <= 6.00) {
                        temporaryGrade = num;
                        Toast.makeText(getApplicationContext(), "Grades saved.", Toast.LENGTH_SHORT).show();
                        d.dismiss();
                    } else {
                        Toast.makeText(Add_information.this, "Type a number between 1 and 6", Toast.LENGTH_SHORT).show();
                    }
                    // is an integer!
                } catch (NumberFormatException e) {
                    // not an integer!
                    Toast.makeText(Add_information.this, "Type a number between 1 and 6", Toast.LENGTH_SHORT).show();
                }


            }
        });
        d.show();
    }

    //here comes getters and setters

    public double getCalculatedGrade() {
        return calculatedGrade;
    }

    public char getGender() {
        return gender;
    }

    public int getR2Grade() {
        return R2Grade;
    }

    public int getExtraPoints() {
        return extraPoints;
    }

    public List<String> getExtraEducationArray() {
        return extraEducationArray;
    }

    public List<String> getCoursesArray() {
        return coursesArray;
    }
}
