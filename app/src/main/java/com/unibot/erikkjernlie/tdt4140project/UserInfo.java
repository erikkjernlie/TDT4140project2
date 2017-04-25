package com.unibot.erikkjernlie.tdt4140project;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jørgen on 01.04.2017.
 */

public class UserInfo {

    private int birthYear;
    private double calculatedGrade;
    public static double calculatedFirstTimeGrade;
    private int number1grade;
    private int number2grade;
    private int number3grade;
    private int number4grade;
    private int number5grade;
    private int number6grade;
    private ArrayList<String> courses;
    private ArrayList<String> extraEducation;
    private char gender;
    private int R2Grade;
    private ArrayList<String> interests;
    private Firebase mRef;
    private FirebaseAuth firebaseAuth;
    public static UserInfo userInfo = new UserInfo();
    public static HashMap<String, StudyProgramInfo> studyPrograms = new HashMap<>();

    public static void setCalculatedFirstTimeGrade(double d) {
        calculatedFirstTimeGrade = d;
    }
    public static double getCalculatedFirstTimeGrade() {
        return calculatedFirstTimeGrade;
    }

    public UserInfo(int birthYear, double calculatedGrade, int number1grade,
                    int number2grade, int number3grade, int number4grade,
                    int number5grade, int number6grade, ArrayList<String> courses,
                    ArrayList<String> extraEducation, char gender, int r2Grade,
                    ArrayList<String> interests) {
        this.birthYear = birthYear;
        this.calculatedGrade = calculatedGrade;
        this.number1grade = number1grade;
        this.number2grade = number2grade;
        this.number3grade = number3grade;
        this.number4grade = number4grade;
        this.number5grade = number5grade;
        this.number6grade = number6grade;
        this.courses = courses;
        this.extraEducation = extraEducation;
        this.gender = gender;
        this.R2Grade = r2Grade;
        this.interests = interests;
    }

    public UserInfo() {}

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public double getCalculatedGrade() {
        return calculatedGrade;
    }

    public void setCalculatedGrade(double calculatedGrade) {
        this.calculatedGrade = calculatedGrade;
    }

    public int getNumber1grade() {
        return number1grade;
    }

    public void setNumber1grade(int number1grade) {
        this.number1grade = number1grade;
    }

    public int getNumber2grade() {
        return number2grade;
    }

    public void setNumber2grade(int number2grade) {
        this.number2grade = number2grade;
    }

    public int getNumber3grade() {
        return number3grade;
    }

    public void setNumber3grade(int number3grade) {
        this.number3grade = number3grade;
    }

    public int getNumber4grade() {
        return number4grade;
    }

    public void setNumber4grade(int number4grade) {
        this.number4grade = number4grade;
    }

    public int getNumber5grade() {
        return number5grade;
    }

    public void setNumber5grade(int number5grade) {
        this.number5grade = number5grade;
    }

    public int getNumber6grade() {
        return number6grade;
    }

    public void setNumber6grade(int number6grade) {
        this.number6grade = number6grade;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    public ArrayList<String> getExtraEducation() {
        return extraEducation;
    }

    public void setExtraEducation(ArrayList<String> extraEducation) {
        this.extraEducation = extraEducation;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getR2Grade() {
        return R2Grade;
    }

    public void setR2Grade(int r2Grade) {
        R2Grade = r2Grade;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void addInterests(String interest) {
        this.interests.add(interest);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "birthYear=" + birthYear +
                ", calculatedGrade=" + calculatedGrade +
                ", courses=" + courses +
                ", extraEducation=" + extraEducation +
                ", gender=" + gender +
                ", interests=" + interests +
                ", R2Grade=" + R2Grade +
                '}';
    }

    public void updateFirebase() {
        firebaseAuth = firebaseAuth.getInstance();
        mRef = new Firebase("https://tdt4140project2.firebaseio.com/Users/");
        mRef.child(firebaseAuth.getCurrentUser().getUid().toString()).setValue(this);
    }
}
