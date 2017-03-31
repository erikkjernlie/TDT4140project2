package com.example.erikkjernlie.tdt4140project;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jonas on 22.03.2017.
 */

public class StudyProgramInfo {

    String info;
    Double grade;
    boolean isGirlPoints;
    double girPercentage;
    String studentUnion;
    ArrayList<String> specializations;
    String studyEnvironment;

    public StudyProgramInfo(String info, Double grade,
                            boolean isGirlPoints, double girPercentage, String studentUnion,
                            ArrayList<String> specializations,
                            String studyEnvironment) {
        this.info = info;
        this.grade = grade;
        this.isGirlPoints = isGirlPoints;
        this.girPercentage = girPercentage;
        this.studentUnion = studentUnion;
        this.specializations = specializations;
        this.studyEnvironment = studyEnvironment;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "StudyProgramInfo{" +
                "info='" + info + '\'' +
                ", grade=" + grade +
                ", isGirlPoints=" + isGirlPoints +
                ", girPercentage=" + girPercentage +
                ", studentUnion='" + studentUnion + '\'' +
                ", specializations=" + specializations +
                ", studyEnvironment='" + studyEnvironment + '\'' +
                '}';
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

    public String getStudentUnion() {
        return studentUnion;
    }

    public void setStudentUnion(String studentUnion) {
        this.studentUnion = studentUnion;
    }

    public ArrayList<String> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(ArrayList<String> specializations) {
        this.specializations = specializations;
    }

    public String getStudyEnvironment() {
        return studyEnvironment;
    }

    public void setStudyEnvironment(String studyEnvironment) {
        this.studyEnvironment = studyEnvironment;
    }
}