package com.example.erikkjernlie.tdt4140project;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jonas on 22.03.2017.
 */

public class StudyProgramInfo {

    private String info;
    private Double grade;
    private ArrayList<String> keywords;
    private boolean isGirlPoints;
    private double girPercentage;
    private ArrayList<String> commonWorkFields;
    private String studentUnion;
    private Map<String, StudySpecialization> specializations;
    private String studyEnvironment;

    public StudyProgramInfo(String info, Double grade, ArrayList<String> keywords,
                            boolean isGirlPoints, double girPercentage,
                            ArrayList<String> commonWorkFields, String studentUnion,
                            Map<String, StudySpecialization> specializations,
                            String studyEnvironment) {
        this.info = info;
        this.grade = grade;
        this.keywords = keywords;
        this.isGirlPoints = isGirlPoints;
        this.girPercentage = girPercentage;
        this.commonWorkFields = commonWorkFields;
        this.studentUnion = studentUnion;
        this.specializations = specializations;
        this.studyEnvironment = studyEnvironment;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public boolean isGirlPoints() {
        return isGirlPoints;
    }

    public void setGirlPoints(boolean girlPoints) {
        isGirlPoints = girlPoints;
    }

    public double getGirPercentage() {
        return girPercentage;
    }

    public void setGirPercentage(double girPercentage) {
        this.girPercentage = girPercentage;
    }

    public ArrayList<String> getCommonWorkFields() {
        return commonWorkFields;
    }

    public void setCommonWorkFields(ArrayList<String> commonWorkFields) {
        this.commonWorkFields = commonWorkFields;
    }

    public String getStudentUnion() {
        return studentUnion;
    }

    public void setStudentUnion(String studentUnion) {
        this.studentUnion = studentUnion;
    }

    public Map<String, StudySpecialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Map<String, StudySpecialization> specializations) {
        this.specializations = specializations;
    }

    public String getStudyEnvironment() {
        return studyEnvironment;
    }

    public void setStudyEnvironment(String studyEnvironment) {
        this.studyEnvironment = studyEnvironment;
    }
}