package com.example.erikkjernlie.tdt4140project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {


    private TextView textView;
    private char gender;
    private double grade;
    private Firebase mRef;
    public Date today = new Date();
    private Map<String, Double> studyFirst = new HashMap<>();
    private Map<String, Double> studyOrd = new HashMap<>();
    private Button gradeBtn;
    private double calculatedGrade;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> availableStudies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initGenderBtns();
        initStudies();

        /*gradeSingle = (EditText) findViewById(R.id.gradeSingle);
        birthYear = (EditText) findViewById(R.id.birthYear);
        extraPoints = (EditText) findViewById(R.id.extraPoints);

        gradeSingleBtn = (Button) findViewById(R.id.gradeSingleBtn);
        birthYearBtn = (Button) findViewById(R.id.birthYearBtn);
        extraPointsBtn = (Button) findViewById(R.id.extraPointsBtn);*/


        Firebase.setAndroidContext(Register.this);

        mRef = new Firebase("https://tdt4140project2.firebaseio.com/");

    }

    public void initStudies() {
        studyFirst.put("Applied Physics and Mathematics", 59.1);
        studyFirst.put("Architecture", 53.9);
        studyFirst.put("Chemical Engineering and Biotechnology", 54.0);
        studyFirst.put("Civil and Environmental Engineering", 53.9);
        studyFirst.put("Communication Technology", 53.2);
        studyFirst.put("Computer Science", 55.2);
        studyFirst.put("Cybernetics and Robotics", 56.9);
        studyFirst.put("Electronics System Design and Innovation", 53.9);
        studyFirst.put("Energy and Environmental Engineering", 55.2);
        studyFirst.put("Engineering and ICT", 54.4);
        studyFirst.put("Geotechnology", 51.9);
        studyFirst.put("Industrial Design Engineering", 54.8);
        studyFirst.put("Industrial Economics and Technology Management", 59.4);
        studyFirst.put("Marine Technology", 52.8);
        studyFirst.put("Materials Science and Engineering", 54.0);
        studyFirst.put("Mechanical Engineering", 53.6);
        studyFirst.put("Nanotechnology", 60.8);
        studyFirst.put("Petroleum Geoscience and Engineering", 50.7);

        studyOrd.put("Applied Physics and Mathematics", 58.6);
        studyOrd.put("Architecture", 59.2);
        studyOrd.put("Chemical Engineering and Biotechnology", 53.6);
        studyOrd.put("Civil and Environmental Engineering", 55.5);
        studyOrd.put("Communication Technology", 56.4);
        studyOrd.put("Computer Science", 57.0);
        studyOrd.put("Cybernetics and Robotics", 58.2);
        studyOrd.put("Electronics System Design and Innovation", 55.2);
        studyOrd.put("Energy and Environmental Engineering", 55.4);
        studyOrd.put("Engineering and ICT", 55.9);
        studyOrd.put("Geotechnology", 54.3);
        studyOrd.put("Industrial Design Engineering", 58.8);
        studyOrd.put("Industrial Economics and Technology Management", 64.0);
        studyOrd.put("Marine Technology", 53.9);
        studyOrd.put("Materials Science and Engineering", 54.9);
        studyOrd.put("Mechanical Engineering", 55.8);
        studyOrd.put("Nanotechnology", 62.5);
        studyOrd.put("Petroleum Geoscience and Engineering", 53.4);
    }

    public double getCalculatedGrade() {
        return calculatedGrade;
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
                infoType();

            }
        });

    }

    public ArrayList<String> findAvailableStudies() {
        ArrayList<String> array = new ArrayList<>();
        for (Map.Entry<String, Double> hash : studyFirst.entrySet()) {
            if (calculatedGrade >= hash.getValue()) {
                array.add(hash.getKey());
            }
        }
        return array;
    }

    public void writeAvailableStudies() {

        /*TextView text = (TextView) findViewById(R.id.testId);

        for (int i = 0; i < findAvailableStudies().size(); i++) {
            text.append(findAvailableStudies().get(i));
            text.append("\n");
        }*/

        /*ArrayList<String> array = new ArrayList<>();
        array.add("hei");
        array.add("he");
        array.add("ho");*/


        listView = (ListView) findViewById(R.id.avaiStud);

        arrayAdapter= new ArrayAdapter<String>(this,
                R.layout.avaiable_study_item, R.id.studyItem);

        for (int i = 0; i < findAvailableStudies().size(); i++) {
            arrayAdapter.add(findAvailableStudies().get(i));
        }

        listView.setAdapter(arrayAdapter);



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

    public void infoType() {
        final EditText gradeSingle = (EditText) findViewById(R.id.gradeSingle);
        final EditText birthYear = (EditText) findViewById(R.id.birthYear);
        final EditText extraPoints = (EditText) findViewById(R.id.extraPoints);

        /*Button gradeSingleBtn = (Button) findViewById(R.id.gradeSingleBtn);
        Button birthYearBtn = (Button) findViewById(R.id.birthYearBtn);
        Button extraPointsBtn = (Button) findViewById(R.id.extraPointsBtn);*/
        Button gradeCalcBtn = (Button) findViewById(R.id.gradeCalcBtn);

        /*gradeSingleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String grades = gradeSingle.getText().toString();
            }
        });

        birthYearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase mRefChild2 = mRef.child("BirthYear");
                int year = Integer.valueOf(birthYear.getText().toString());
                mRefChild2.setValue(year);
            }
        });

        extraPointsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase mRefChild3 = mRef.child("ExtraPoints");
                int pointsExtra = Integer.valueOf(extraPoints.getText().toString());
                mRefChild3.setValue(pointsExtra);
            }
        });*/

        gradeCalcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String grades = gradeSingle.getText().toString();

                Firebase mRefChild2 = mRef.child("BirthYear");
                int year = Integer.valueOf(birthYear.getText().toString());
                mRefChild2.setValue(year);

                Firebase mRefChild3 = mRef.child("ExtraPoints");
                int pointsExtra = Integer.valueOf(extraPoints.getText().toString());
                mRefChild3.setValue(pointsExtra);

                Firebase mRefChild4 = mRef.child("GradeCalculated");
                calculatedGrade = grade_calculation(gradeSingle.getText().toString(),
                        Integer.valueOf(birthYear.getText().toString()), Integer.valueOf(extraPoints.getText().toString()));
                Toast.makeText(Register.this, "Your average grade is: " + calculatedGrade, Toast.LENGTH_LONG).show();

                mRefChild4.setValue(calculatedGrade);
                setContentView(R.layout.activity_available_studies);
                writeAvailableStudies();
            }
        });
    }

    protected int agePoints(int birthYear) {
        if (birthYear <= 1900) {
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

    public double grade_calculation(String s, int birthYear, int pointsExtra) {
        double grade_calculated = 0;
        int counter = 0;
        String newS = s.replace(",", "").trim();
        for (int i = 0; i < newS.length(); i++) {
            grade_calculated += Character.getNumericValue(newS.charAt(i));
            counter++;
        }
        grade_calculated = (grade_calculated*10 / counter) + agePoints(birthYear) + pointsExtra;
        return grade_calculated;
    }

    public char getGender() {
        return gender;
    }
}