package com.example.erikkjernlie.tdt4140project;


import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import ai.api.model.AIResponse;

/**
 * Created by jonas on 01.04.2017.
 */
@RunWith(Enclosed.class)
public class ProcessAiResponse {

    private HashMap<String, StudyProgramInfo> studyPrograms;
    UserInfo userInfo;
    ProcessAiResponse aiResponse;

    public ProcessAiResponse(HashMap<String, StudyProgramInfo> studyPrograms, UserInfo userInfo) {
        this.studyPrograms = studyPrograms;
        this.userInfo = userInfo;
    }


    public String processAiRespons(AIResponse aiResponse) {
        String action = aiResponse.getResult().getAction().toString();
        String method = action.substring(0, action.indexOf('('));

        String ut = "NoAnswer"; // Må legges til i ChatBot: Hvis "NoAnswer" så skal den bare legge til svaret den får fra APIAI
        // Evt kan den bare sjekke om svaret API.AI gir er tomt, og det er, skal den kalle på denne metoden. DETTE ER BEST

        switch (method) {
            case "getGrade":
                ut = this.getGrade(aiResponse.getResult().getParameters().get("StudyProgram").toString());
                break;
            case "getGirlPoints":
                ut = this.getGirlPoints(aiResponse.getResult().getParameters().get("StudyProgram").toString());
                break;
            case "getKeywords":
                // kall på metoden som håndterer dette tilfellet
                break;
            case "getCommonWorkFields":
                ut = this.getCommonWorkFields(aiResponse.getResult().getParameters().get("StudyProgram").toString());
                break;
            case "getInfo":
                ut = this.getInfo(aiResponse.getResult().getParameters().get("StudyProgram").toString());
                break;
            case "getStudyEnvironment":
                ut = this.getStudyEnvironment(aiResponse.getResult().getParameters().get("StudyProgram").toString());
                break;
            case "getStudentUnion":
                ut = this.getStudentUnion(aiResponse.getResult().getParameters().get("StudyProgram").toString());
                break;
            case "getCourses":
                ut = this.getCourses(aiResponse.getResult().getParameters().get("StudyProgram").toString());
                break;
            case "getUnion":
                ut = this.getUnion(aiResponse.getResult().getParameters().get("StudyProgram").toString());
                break;
            case "compareStudies":
                ut = this.getCompareStudies(aiResponse.getResult().getParameters().get("StudyProgram").toString(), aiResponse.getResult().getParameters().get("StudyProgram1").toString());
                break;
            case "getAllStudies":
                ut = this.getAllStudies();
                break;
            case "getUserInfo":
                ut = this.getUserInfo();
                break;
            case "addInterests":
                ArrayList<String> interests = new ArrayList<>();

                for (JsonElement interest : aiResponse.getResult().getParameters().get("Interests").getAsJsonArray()) {
                    interests.add(interest.toString());
                }
                ut = this.addInterest(interests);
                break;
        }

        return ut;
    }

    // Method for getting study grade
    private String getGrade(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        double grade = studyPrograms.get(studyProgram).getGrade();
        return "The grade to get into " + studyProgram + " is " + grade + ".";
    }

    // Method for getting girlPoints
    private String getGirlPoints(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        boolean girlPoints = studyPrograms.get(studyProgram).isGirlPoints();

        if (girlPoints) {
            return studyProgram + " has girlspoints.";
        } else {
            return studyProgram + " has not girlpoints";
        }
    }

    // Method for getting info
    private String getInfo(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        String info = studyPrograms.get(studyProgram).getInfo();
        return info;
    }

    // Method for getting commonworkfields
    private String getCommonWorkFields(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        ArrayList<String> commonWorkFields = studyPrograms.get(studyProgram).getCommonWorkFields();
        String ut = "At " + studyProgram + " you can work with: ";
        for (String workField : commonWorkFields) {
            ut += workField + ", ";
        }
        ut = ut.substring(0, ut.length() - 2);
        return ut + ".";
    }

    // Method for getting studyenvironment
    private String getStudyEnvironment(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        return studyPrograms.get(studyProgram).getStudyEnvironment();
    }

    // Method for getting studentunion
    private String getStudentUnion(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        return "The student union at " + studyProgram + " is " + studyPrograms.get(studyProgram).getStudentUnion() + ".";
    }

    // Method for getting courses
    private String getCourses(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        String ut = "The courses at " + studyProgram + " is: ";
        ArrayList<String> courses = studyPrograms.get(studyProgram).getCourses();
        for (String course : courses) {
            ut += course + ", ";
        }
        ut = ut.substring(0, ut.length() - 2);
        return ut + ".";
    }

    // Method for getting union
    private String getUnion(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        String ut = "The courses at " + studyProgram + " is: ";

        return "Denne er ikke ferdig";
    }

    // Method for comparing studies
    private String getCompareStudies(String studyProgram, String studyProgram1) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        studyProgram1 = studyProgram1.replace("\"", ""); // removes ""
        return "We will compare " + studyProgram + " and " + studyProgram1 + ".";
    }

    // Method for getting all studies
    private String getAllStudies() {
        String ut = "The studies we support are: ";
        Set<String> keys = studyPrograms.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            ut += iterator.next() + ", ";
        }
        ut = ut.substring(0, ut.length() - 2);
        return ut + ".";
    }

    // Method for telling the user about himself
    private String getUserInfo() {
        String ut = "";
        if (userInfo.getGender() == 'M') {
            ut += "You are a Male";
        } else {
            ut += "You are a Female";
        }

        int age = 2017 - userInfo.getBirthYear();

        ut += ", aged " + age;

        ut += "\n you have " + userInfo.getCalculatedGrade() + " points to apply with.";

        return ut;
    }

    // Method for adding an interest to the user
    private String addInterest(ArrayList<String> interests) {
        ArrayList<String> existingInterest = userInfo.getInterests();
        // Removes ""
        for (String i : interests) {
            interests.set(interests.indexOf(i), i.replace("\"", ""));
            //interests.set(interests.indexOf(i), i.replace("[", ""));
           // interests.set(interests.indexOf(i), i.replace("]", ""));
        }
        int size = interests.size();
        for (int i = 0; i < size; i++) {
            if (existingInterest.contains(interests.get(i))) {
                interests.remove(interests.indexOf(i));
            }
        }
        String ut = "We've just added ";
        for (String i : interests) {
            userInfo.addInterests(i);
            ut += interests + ", ";
        }
        ut = ut.substring(0, ut.length()-2) + " to your interests";

        return ut;
    }

    public static class ProcessAiResponseTest {
        ProcessAiResponse aiResponse;

        @Before
        public void setUp() throws Exception {
            HashMap<String, StudyProgramInfo> studyProgramInfoMap = new HashMap<>();
            studyProgramInfoMap.put("Informatics", new StudyProgramInfo(51.2, false,
                    new ArrayList<String>(Arrays.asList("Data", "IKT", "IT", "HTML")),
                    new ArrayList<String>(Arrays.asList("Data", "Consultant", "Web design")), "Informatics is a 3-year bachelor program that deals with the planning, development, improvement, evaluation and use of programs and computer systems. After 3 years, the students may embark on their working life or apply for a 2 year Masters program.",
                    "The study evironment is focused around the student union Online. They arrange a lot of different activities, both related and unrealted to computers and data technology. Many students are engaged in unions such as Hackerspace, where you can get familiar with interesting subjects such as use of VR and building computers.",
                    "Online", new ArrayList<String>(Arrays.asList("EXPH0004", "TDT4110", "MA0001"))));

            studyProgramInfoMap.put("Computer Science", new StudyProgramInfo(57.0, true,
                    new ArrayList<String>(Arrays.asList("Data", "IKT", "IT", "Programming")),
                    new ArrayList<String>(Arrays.asList("Data", "Consultant", "Programmer")), "The Computer Science programme of study is a 5 years Master of Science (sivilingeniør) programme. Computer Science is not only about excellent computer skills - it also deals with contributing to the social development. How can we improve existing systems? What could be useful in the future? With computer engineering skills you can create computer systems which people need, want, or not yet know that they need.",
                    "The student union Abakus is very popular among students and arrange many different activities for all students at Computer Science.",
                    "Abakus", new ArrayList<String>(Arrays.asList("EXPH0004", "TDT4110", "TMA4100"))));
            UserInfo user = new UserInfo(1996, 55.6, new ArrayList<>(Arrays.asList("Matematikk R1",
                    "Matematikk R2")),new ArrayList<>(Arrays.asList("Folkehøgskole")), 'M', 5,
                    new ArrayList<>(Arrays.asList("Studies", "Computers")));
            aiResponse = new ProcessAiResponse(studyProgramInfoMap, user);
        }

        @Test
        public void testConstructor() throws Exception {
            ProcessAiResponse res = new ProcessAiResponse(
                    aiResponse.studyPrograms, aiResponse.userInfo);
            assertEquals(res.studyPrograms, aiResponse.studyPrograms);
            assertEquals(res.userInfo, aiResponse.userInfo);
        }

        @Test
        public void testGetGrade() throws Exception {
            assertEquals(aiResponse.getGrade("Informatics"),
                    "The grade to get into " + "Informatics" + " is " + "51.2" + ".");
            assertFalse(aiResponse.getGrade("Informatics")
                    .equals("The grade to get into " + "Informatics" + " is " + "51.0" + "."));
        }

        @Test
        public void testGetGirlPoints() throws Exception {
            assertEquals(aiResponse.getGirlPoints("Computer Science"),
                    "Computer Science" + " has girlspoints.");
            assertFalse(aiResponse.getGirlPoints("Computer Science").
                    equals("Computer Science" + " has not girlspoints."));

            assertFalse(aiResponse.getGirlPoints("Informatics").
                    equals("Informatics" + " has girlspoints"));
        }

        @Test
        public void testGetInfo() throws Exception {
            assertEquals(aiResponse.getInfo("Informatics"),
                    "Informatics is a 3-year bachelor program that deals with the planning, development, improvement, evaluation and use of programs and computer systems. After 3 years, the students may embark on their working life or apply for a 2 year Masters program.");

        }

        @Test
        public void testGetCommonWorkFields() throws Exception {
            assertEquals(aiResponse.getCommonWorkFields("Computer Science"),
                    "At Computer Science you can work with: Data, Consultant, Programmer.");
        }

        @Test
        public void testGetStudyEnvironment() throws Exception {
            assertEquals(aiResponse.getStudyEnvironment("Informatics"),
                    "The study evironment is focused around the student union Online. They arrange a lot of different activities, both related and unrealted to computers and data technology. Many students are engaged in unions such as Hackerspace, where you can get familiar with interesting subjects such as use of VR and building computers.");
        }

        @Test
        public void testGetStudentUnion() throws Exception {
            assertEquals(aiResponse.getStudentUnion("Computer Science"),
                    "The student union at " + "Computer Science" + " is " + "Abakus" + ".");
        }

        @Test
        public void testGetCourses() throws Exception {
            assertEquals(aiResponse.getCourses("Informatics"),
                    "The courses at " + "Informatics" + " is: " +
            "EXPH0004, TDT4110, MA0001.");
        }

        @After
        public void tearDown() throws Exception{
            aiResponse = null;
        }

    }


}
