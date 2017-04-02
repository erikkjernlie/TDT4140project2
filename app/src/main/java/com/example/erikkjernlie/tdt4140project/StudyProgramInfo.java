/*  StudyProgramInfo
 *
 *  Contains all the information that is needed about a study program.
 *
 *  Created by Jonas Sagild on 22.03.2017.
 *  Copyright © uniBOT
 */

package com.example.erikkjernlie.tdt4140project;

import java.util.ArrayList;



public class StudyProgramInfo {

    private String info;
    private Double grade;
    private boolean isGirlPoints;
    private double girPercentage;
    private String studentUnion;
    private ArrayList<String> specializations;
    private String studyEnvironment;

    public StudyProgramInfo(){

    }

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