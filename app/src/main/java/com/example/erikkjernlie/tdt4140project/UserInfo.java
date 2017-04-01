package com.example.erikkjernlie.tdt4140project;

import java.util.ArrayList;

/**
 * Created by Jørgen on 01.04.2017.
 */

public class UserInfo {

    private int birthYear;
    private double calculatedGrade;
    private ArrayList<String> courses;
    private ArrayList<String> extraEducation;
    private char gender;
    private int R2Grade;
    private ArrayList<String> interests;

    public UserInfo(int birthYear, double calculatedGrade, ArrayList<String> courses,
                    ArrayList<String> extraEducation, char gender, int r2Grade,
                    ArrayList<String> interests) {
        this.birthYear = birthYear;
        this.calculatedGrade = calculatedGrade;
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
                ", R2Grade=" + R2Grade +
                '}';
    }
}
