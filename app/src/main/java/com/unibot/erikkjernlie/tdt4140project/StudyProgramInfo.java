/*  StudyProgramInfo
 *
 *  Contains all the information that is needed about a study program.
 *
 *  Created by Jonas Sagild on 22.03.2017.
 *  Copyright © uniBOT
 */

package com.unibot.erikkjernlie.tdt4140project;

import java.util.ArrayList;
import java.util.HashMap;


public class StudyProgramInfo {

    private double firstTimeGrade;
    private double ordinaryGrade;
    private boolean girlPoints;
    private ArrayList<String> keywords;
    private ArrayList<String> commonWorkFields;
    private String info;
    private String studyEnvironment;
    private String studentUnion;
    private ArrayList<String> courses;
    public static HashMap<String, StudyProgramInfo> studyPrograms = new HashMap<>();


    public StudyProgramInfo(double firstTimeGrade, double ordinaryGrade, boolean girlPoints, ArrayList<String> keywords,
                            ArrayList<String> commonWorkFields, String info,
                            String studyEnvironment, String studentUnion,
                            ArrayList<String> courses) {
        this.firstTimeGrade = firstTimeGrade;
        this.ordinaryGrade = ordinaryGrade;
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

    public double getFirstTimeGrade() {
        return firstTimeGrade;
    }

    public void setFirstTimeGrade(double firstTimeGrade) {
        this.firstTimeGrade = firstTimeGrade;
    }

    public double getOrdinaryGrade() {
        return ordinaryGrade;
    }

    public void setOrdinaryGrade(double ordinaryGrade) {
        this.ordinaryGrade = ordinaryGrade;
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
                "firstTimeGrade=" + firstTimeGrade +
                ", ordinaryGrade=" + ordinaryGrade +
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