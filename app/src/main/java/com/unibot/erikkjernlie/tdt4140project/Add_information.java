/*  Add_information
 *
 *  This class is for adding information regarding the user's grades
 *  Allows the user to add gender, r2grade, average grade, separate grades,
 *  courses that gives extra points, age points and extra education that gives extra points.
 *
 *  Created by Jørgen Mortensen
 *  Copyright © uniBOT
 */
package com.unibot.erikkjernlie.tdt4140project;

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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.unibot.erikkjernlie.tdt4140project.R.id.confirmr2;
import static com.unibot.erikkjernlie.tdt4140project.R.id.dropdownExtrapoints;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
@RunWith(Enclosed.class)
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
    private NumberPicker np;
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
    private int number1grade = UserInfo.userInfo.getNumber1grade();
    private int number2grade = UserInfo.userInfo.getNumber2grade();
    private int number3grade = UserInfo.userInfo.getNumber3grade();
    private int number4grade = UserInfo.userInfo.getNumber4grade();
    private int number5grade = UserInfo.userInfo.getNumber5grade();
    private int number6grade = UserInfo.userInfo.getNumber6grade();
    private TextView cancel;
    private TextView add_grades;
    private EditText add_grades_text;
    private TextView add_average_grade;
    private double old_grade;
    private ArrayList<String> old_coursesArray;
    private ArrayList<String> old_extraEducationArray;
    private int old_birthyear;

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
        LinearLayout l = (LinearLayout) findViewById(R.id.linear_add_information);

        l.requestFocus();
        initFagbase();
        initButtons();
        numberPicker();

        old_coursesArray = UserInfo.userInfo.getCourses();
        old_extraEducationArray = UserInfo.userInfo.getExtraEducation();
        old_birthyear = UserInfo.userInfo.getBirthYear();
            old_grade = UserInfo.userInfo.getCalculatedGrade();
            try {
                if (old_extraEducationArray.size() > 0) {
                    old_grade -= 2;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            double maxReal = 0;
            try {
                for (String course : old_coursesArray) {
                    maxReal += fagbase.get(course);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (maxReal > 4){
                maxReal = 4;
            }
            old_grade -= maxReal;
            old_grade -= agePoints(old_birthyear);
        if (old_grade < 1) {
            old_grade = 0;
        }
        temporaryGrade = old_grade;
        if (UserInfo.userInfo.getGender() == 'F'){
            female.setImageResource(R.drawable.female_selected);
            isPressedFemale = true;
        } else if (UserInfo.userInfo.getGender() == 'M'){
            man.setImageResource(R.drawable.man_selected);
            isPressedMan = true;
        }
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
        firebaseAuth = firebaseAuth.getInstance();
        mRef = new Firebase("https://tdt4140project2.firebaseio.com/Users/" +
                firebaseAuth.getCurrentUser().getUid());
        UserInfo user = new UserInfo(this.year, this.calculatedGrade, this.number1grade,
                this.number2grade, this.number3grade, this.number4grade,
                this.number5grade, this.number6grade, this.coursesArray,
                this.extraEducationArray, this.gender, this.R2Grade,
                new ArrayList<>(Arrays.asList("Studies")));
        UserInfo.userInfo = user;
        mRef.setValue(user);
    }

    public void initButtons() {
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressedMan) {
                    gender = 'M';
                    UserInfo.userInfo.setGender('M');
                } else if (isPressedFemale) {
                    gender = 'F';
                    UserInfo.userInfo.setGender('F');
                } else {
                    Toast.makeText(Add_information.this, "You must pick a gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (UserInfo.userInfo.getR2Grade() != 0){
                    R2Grade = UserInfo.userInfo.getR2Grade();
                }
                ArrayList<String> chosen_courses = new ArrayList<String>();
                chosen_courses = UserInfo.userInfo.getCourses();
                if ((chosen_courses != null)) {
                    for (int i = 0; i < chosen_courses.size(); i++) {
                        for (int k = 0; k < courses.length; k++) {
                            if (chosen_courses.get(i).equals(courses[k])) {
                                checkedStateCourses[k] = true;
                            }
                        }
                    }
                }
                ArrayList<String> chosen_education = new ArrayList<String>();
                chosen_education = UserInfo.userInfo.getExtraEducation();
                if ((chosen_education != null)) {
                    for (int i = 0; i < chosen_education.size(); i++) {
                        for (int k = 0; k < extraEducation.length; k++) {
                            if (chosen_education.get(i).equals(extraEducation[k])) {
                                checkedStateEducation[k] = true;
                            }
                        }
                    }
                }
                for (int i = 0; i < (courses.length); i++) {
                    if (checkedStateCourses[i] == true && !coursesArray.contains(courses[i].toString())) {
                        coursesArray.add(courses[i].toString());
                    }
                }
                for (int i = 0; i < (extraEducation.length); i++) {
                    if (checkedStateEducation[i] == true && !extraEducationArray.contains(extraEducation[i].toString())) {
                        extraEducationArray.add(extraEducation[i].toString());
                    }
                }
                calculatedGrade = grade_calculation();

                storeVariables();
                Toast.makeText(Add_information.this, "Your average grade is: " + UserInfo.userInfo.getCalculatedFirstTimeGrade() + " / " + calculatedGrade, Toast.LENGTH_LONG).show();
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
        np = (NumberPicker) findViewById(R.id.np);
        //Sets the minimum value of NumberPicker
        np.setMinValue(minimum_born_year);
        //Sets the maximum value of NumberPicker
        np.setMaxValue(2017);
        //Sets the start value of Numberpicker
        int b = UserInfo.userInfo.getBirthYear();
        if (b != 0){
            np.setValue(b);
            year = b;
        }
        np.setWrapSelectorWheel(true);
        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected number from picker
                year = newVal;
                UserInfo.userInfo.setBirthYear(year);
            }
        });
        np.clearFocus();

    }

    //calculates age points
    protected int agePoints(int birthYear) {
        if (birthYear < minimum_born_year) {
            return 8;
        }
        int points = 0;
        points = 2 * ((today.getYear() - birthYear + 1900) - 19);
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

    public static class Add_informationTest{
        private Add_information add_information;
        @Before
        public void setUp(){
            add_information = new Add_information();
        }
        @Test
        public void testRound(){
            assertTrue(143.46 == add_information.round(143.45912,2));
        }
        @Test
        public void constructor_test() throws Exception{
            assertEquals(true, add_information.getCalculatedGrade() == 0);
            assertEquals(true, add_information.getGender() ==  '\u0000');
            assertEquals(true, add_information.getR2Grade() == 0);
            assertEquals(true, add_information.getExtraPoints() == 0);
            assertEquals(true, add_information.getExtraEducationArray().isEmpty());
            assertEquals(true, add_information.getCoursesArray().isEmpty());
        }
        @Test
        public void testCalculation() throws Exception{
            ArrayList<String> c = new ArrayList<>(Arrays.asList("Kjemi 1"));
            add_information.setYear(2002);
            add_information.setCourses_array(c);
            assertEquals(false, add_information.getCalculatedGrade() == 0.5);
            c.add("Matematikk R2");
            add_information.setCourses_array(c);
            assertEquals(false, add_information.getCalculatedGrade() == 1.5);

        }
        @Test
        public void agePoints_test() throws Exception {
            assertEquals(true, add_information.agePoints(1985) == 8);
            assertEquals(true, add_information.agePoints(1994) == 8);
            assertEquals(true, add_information.agePoints(1995) == 6);
            assertEquals(true, add_information.agePoints(1996) == 4);
            assertEquals(true, add_information.agePoints(1997) == 2);
            assertEquals(true, add_information.agePoints(1998) == 0);
            assertEquals(true, add_information.agePoints(2016) == 0);
        }
        @Test
        public void testGetGender(){
            add_information.setGender('F');
            assertTrue(add_information.getGender() == 'F');
            assertFalse(add_information.getGender() == 'M');
        }
        @Test
        public void testGetCalculatedGrade(){
            add_information.setCalculatedGrade(5.0);
            assertTrue(add_information.getCalculatedGrade() == 5.0);
            assertFalse(add_information.getCalculatedGrade() == 4.9);
        }
        @Test
        public void testGetExtraPoints(){
            add_information.setExtraPoints(2);
            assertTrue(add_information.getExtraPoints() == 2);
            assertFalse(add_information.getExtraPoints() == 3);
        }
        @Test
        public void testGetExtraEducation(){
            ArrayList<String> c = new ArrayList<>();
            c.add("IKT");
            add_information.setExtra_education_array(c);
            assertTrue(add_information.getExtraEducationArray().equals(c));
        }
        @After
        public void tearDown(){
            add_information = null;
        }
    }
    //calculates the enitre grade
    public double grade_calculation() {
        initFagbase();
        double grade_calculated = 0;
        double realFagPoints = 0;
        this.extraPoints = 0;
        grade_calculated = round(temporaryGrade, 2);
        if (temporaryGrade == 0 && old_grade != 0 && number1grade == 0 && number2grade == 0 && number3grade == 0 && number4grade == 0 && number5grade == 0 && number6grade == 0){
            try {
                if (old_extraEducationArray.size() > 0) {
                    old_grade -= 2;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            double maxReal = 0;
            try {
                for (String course : old_coursesArray) {
                    maxReal += fagbase.get(course);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (maxReal > 4){
                maxReal = 4;
            }
            old_grade -= maxReal;
            old_grade -= agePoints(old_birthyear);
            grade_calculated = old_grade;
        } else if (grade_calculated == 0 && number1grade == 0 && number2grade == 0 && number3grade == 0 && number4grade == 0 && number5grade == 0 && number6grade == 0){
            return 0;
        } else if (grade_calculated == 0){
            grade_calculated = 10*(number1grade*1 + number5grade*5 + number6grade*6 + number4grade*4 + number3grade*3 + number2grade*2)/(number2grade + number5grade + number3grade +number4grade + number6grade + number1grade);
        }
        if (extraEducationArray.size() > 0) {
            this.extraPoints = 2;
        }
        for (String course : coursesArray) {
            realFagPoints += fagbase.get(course);
        }
        if (realFagPoints > 4) {
            realFagPoints = 4;
        }
        calculatedGrade = (agePoints(year) + extraPoints + realFagPoints) + grade_calculated;
        UserInfo.userInfo.setCalculatedGrade(calculatedGrade);
        double calculatedFirstTimeGrade = grade_calculated + realFagPoints;
        UserInfo.userInfo.calculatedFirstTimeGrade = calculatedFirstTimeGrade;
        return calculatedGrade;
    }

    //alertDialog for selecting courses
    private void alertCourses() {
        ArrayList<String> chosen_courses = new ArrayList<String>();
        coursesArray = new ArrayList<String>();
        chosen_courses = UserInfo.userInfo.getCourses();
        for (int j = 0; j < checkedStateCourses.length;j++){
            checkedStateCourses[j] = false;
        }
        if ((chosen_courses != null)) {
            for (int i = 0; i < chosen_courses.size(); i++) {
                for (int k = 0; k < courses.length; k++) {
                    if (chosen_courses.get(i).equals(courses[k]) || (checkedStateCourses[k] == true)) {
                        checkedStateCourses[k] = true;
                    } else {
                        checkedStateCourses[k] = false;
                    }
                }
            }
        }
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
                            if (checkedStateCourses[i] == true && (!coursesArray.contains(courses[i]))) {
                                coursesArray.add(courses[i].toString());
                            } else {
                                coursesArray.remove(courses[i].toString());
                            }
                        }
                        UserInfo.userInfo.setCourses(coursesArray);
                        Toast.makeText(getApplicationContext(), "Selected courses: "
                                + coursesArray.toString(), Toast.LENGTH_SHORT).show();
                        if (coursesArray.contains("Matematikk R2")) {
                            alertR2Grade();
                        } else {
                            R2Grade = 0;
                            UserInfo.userInfo.setR2Grade(0);
                        }
                        //alertdialog_r2grade.dismiss(); if we want it to be able to close the window if the user presses outside the alert
                    }
                });
        AlertDialog alertdialog1 = builder1.create();
        alertdialog1.show();
    }

    //alertDialog for selecting extra education
    private void alertExtraEducation() {
        ArrayList<String> chosen_education = new ArrayList<String>();
        extraEducationArray = new ArrayList<String>();
        chosen_education = UserInfo.userInfo.getExtraEducation();
        for (int j = 0; j < checkedStateEducation.length;j++){
            checkedStateEducation[j] = false;
        }
        if ((chosen_education != null)) {
            for (int i = 0; i < chosen_education.size(); i++) {
                for (int k = 0; k < extraEducation.length; k++) {
                    if (chosen_education.get(i).equals(extraEducation[k]) || (checkedStateEducation[k] == true)) {
                        checkedStateEducation[k] = true;
                    } else {
                        checkedStateEducation[k] = false;
                    }
                }
            }
        }
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
                            if (checkedStateEducation[i] == true &&(!extraEducationArray.contains(extraEducation[i]))) {
                                extraEducationArray.add(extraEducation[i].toString());
                            } else {
                                extraEducationArray.remove(extraEducation[i].toString());
                            }
                        }
                        UserInfo.userInfo.setExtraEducation(extraEducationArray);
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
        np.setValue(3);
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
                UserInfo.userInfo.setR2Grade(R2Grade);
                if (R2Grade == 0){
                    R2Grade = 3;
                }
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

                if ((totalGrades) != 0) {
                    double totalScore = (1 * number1grade + 2 * number2grade + 3 * number3grade + 4 * number4grade + 5 * number5grade + 6 * number6grade);
                    Toast.makeText(getApplicationContext(), "Grades saved", Toast.LENGTH_SHORT).show();
                    temporaryGrade = (totalScore / (totalGrades))*10;

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
                UserInfo.userInfo.setNumber1grade(number1grade);
                number1grade_textview.setText(Integer.toString(number1grade));
            }
        });
        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number1grade++;
                number1grade_textview.setText(Integer.toString(number1grade));
                UserInfo.userInfo.setNumber1grade(number1grade);
            }
        });
        minus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number2grade--;
                if (number2grade < 0) {
                    number2grade = 0;
                }
                UserInfo.userInfo.setNumber2grade(number2grade);
                number2grade_textview.setText(Integer.toString(number2grade));
            }
        });
        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number2grade++;
                number2grade_textview.setText(Integer.toString(number2grade));
                UserInfo.userInfo.setNumber2grade(number2grade);
            }
        });
        minus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number3grade--;
                if (number3grade < 0) {
                    number3grade = 0;
                }
                UserInfo.userInfo.setNumber3grade(number3grade);
                number3grade_textview.setText(Integer.toString(number3grade));
            }
        });
        plus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number3grade++;
                UserInfo.userInfo.setNumber3grade(number3grade);
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
                UserInfo.userInfo.setNumber4grade(number4grade);
                number4grade_textview.setText(Integer.toString(number4grade));
            }
        });
        plus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number4grade++;
                UserInfo.userInfo.setNumber4grade(number4grade);
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
                UserInfo.userInfo.setNumber5grade(number5grade);
                number5grade_textview.setText(Integer.toString(number5grade));
            }
        });
        plus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number5grade++;
                UserInfo.userInfo.setNumber5grade(number5grade);
                number5grade_textview.setText(Integer.toString(number5grade));
            }
        });
        minus6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number6grade--;
                if (number6grade < 0) {
                    number6grade = 0;
                    UserInfo.userInfo.setNumber6grade(number6grade);
                }
                number6grade_textview.setText(Integer.toString(number6grade));
            }
        });
        plus6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number6grade++;
                UserInfo.userInfo.setNumber6grade(number6grade);
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
        d.setTitle("Set your average grade");
        add_grades_text = (EditText) d.findViewById(R.id.add_grades_text);
        add_grades_text.setText(Double.toString(round(this.old_grade * 0.1, 2)));
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
                        temporaryGrade = num*10;
                        Toast.makeText(getApplicationContext(), "Grade saved", Toast.LENGTH_SHORT).show();
                        number1grade = 0;
                        number2grade = 0;
                        number3grade = 0;
                        number4grade = 0;
                        number5grade = 0;
                        number6grade = 0;
                        UserInfo.userInfo.setNumber1grade(0);
                        UserInfo.userInfo.setNumber2grade(0);
                        UserInfo.userInfo.setNumber3grade(0);
                        UserInfo.userInfo.setNumber4grade(0);
                        UserInfo.userInfo.setNumber5grade(0);
                        UserInfo.userInfo.setNumber6grade(0);
                        d.dismiss();
                    } else {
                        Toast.makeText(Add_information.this, "Must be a number between 1 and 6", Toast.LENGTH_SHORT).show();
                    }
                    // is an integer!
                } catch (NumberFormatException e) {
                    // not an integer!
                    Toast.makeText(Add_information.this, "Must be a number between 1 and 6", Toast.LENGTH_SHORT).show();
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
    public void setCalculatedGrade(double calculatedGrade) {
        this.calculatedGrade = calculatedGrade;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }
    public void setR2Grade(int r2Grade) {
        R2Grade = r2Grade;
    }
    public void setExtraPoints(int extraPoints) {
        this.extraPoints = extraPoints;
    }
    public void setCourses_array(ArrayList<String> courses_array) {
        this.coursesArray = courses_array;
        grade_calculation();
    }
    public void setExtra_education_array(ArrayList<String> extra_education_array) {
        this.extraEducationArray = extra_education_array;
        grade_calculation();
    }
    public void setYear(int year){
        this.year = year;
    }
}