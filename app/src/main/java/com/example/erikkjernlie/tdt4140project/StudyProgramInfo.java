/*  StudyProgramInfo
 *
 *  Contains all the information that is needed about a study program.
 *
 *  Created by Jonas Sagild on 22.03.2017.
 *  Copyright © uniBOT
 */

package com.example.erikkjernlie.tdt4140project;

import java.util.ArrayList;
import java.util.HashMap;


public class StudyProgramInfo {

    private double grade;
    private boolean girlPoints;
    private ArrayList<String> keywords;
    private ArrayList<String> commonWorkFields;
    private String info;
    private String studyEnvironment;
    private String studentUnion;
    private ArrayList<String> courses;
    public static HashMap<String, StudyProgramInfo> studyPrograms = new HashMap<>();


    public StudyProgramInfo(double grade, boolean girlPoints, ArrayList<String> keywords,
                            ArrayList<String> commonWorkFields, String info,
                            String studyEnvironment, String studentUnion,
                            ArrayList<String> courses) {
        this.grade = grade;
        this.girlPoints = girlPoints;
        this.keywords = keywords;
        this.commonWorkFields = commonWorkFields;
        this.info = info;
        this.studyEnvironment = studyEnvironment;
        this.studentUnion = studentUnion;
        this.courses = courses;
    }

    public StudyProgramInfo() {
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public boolean isGirlPoints() {
        return girlPoints;
    }

    public void setGirlPoints(boolean girlPoints) {
        this.girlPoints = girlPoints;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public ArrayList<String> getCommonWorkFields() {
        return commonWorkFields;
    }

    public void setCommonWorkFields(ArrayList<String> commonWorkFields) {
        this.commonWorkFields = commonWorkFields;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStudyEnvironment() {
        return studyEnvironment;
    }

    public void setStudyEnvironment(String studyEnvironment) {
        this.studyEnvironment = studyEnvironment;
    }

    public String getStudentUnion() {
        return studentUnion;
    }

    public void setStudentUnion(String studentUnion) {
        this.studentUnion = studentUnion;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }


    @Override
    public String toString() {
        return "StudyProgramInfo{" +
                "grade=" + grade +
                ", girlPoints=" + girlPoints +
                ", keywords=" + keywords +
                ", commonWorkFields=" + commonWorkFields +
                ", info='" + info + '\'' +
                ", studyEnvironment='" + studyEnvironment + '\'' +
                ", studentUnion='" + studentUnion + '\'' +
                ", courses=" + courses +
                '}';
    }
}