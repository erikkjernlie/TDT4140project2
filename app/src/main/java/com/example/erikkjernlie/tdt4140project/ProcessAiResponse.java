package com.example.erikkjernlie.tdt4140project;


import android.test.suitebuilder.annotation.Suppress;

import com.google.gson.JsonElement;

import junit.framework.Assert;

import java.lang.reflect.Array;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static junit.framework.Assert.fail;
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
    private UserInfo userInfo;
    private HashMap<String, Union> unions;
    private ProcessAiResponse aiResponse;

    public ProcessAiResponse(HashMap<String, StudyProgramInfo> studyPrograms, UserInfo userInfo, HashMap<String, Union> unions) {
        this.studyPrograms = studyPrograms;
        this.userInfo = userInfo;
        this.unions = unions;
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
                if (aiResponse.getResult().getParameters().get("StudyProgram") == null) {
                    ut = this.getInfoStudentUnion(aiResponse.getResult().getParameters().get("StudentUnion").toString());
                } else {
                    ut = this.getStudentUnion(aiResponse.getResult().getParameters().get("StudyProgram").toString());
                }
                break;
            case "getCourses":
                ut = this.getCourses(aiResponse.getResult().getParameters().get("StudyProgram").toString());
                break;
            case "getUnion":
                ut = this.getUnion(aiResponse.getResult().getParameters().get("Union").toString());
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
            case "getIntoStudy":
                ut = this.getInto(aiResponse.getResult().getParameters().get("StudyProgram").toString());
                break;
            case "recommendStudy":
                ut = this.recommendStudy();
                break;
            case "startInterview":
                ut = this.interview();
                break;
            case "help":
                ut = this.help();
                break;
        }

        return ut;
    }

    // Method for getting study grade
    private String getGrade(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        double grade = studyPrograms.get(studyProgram).getGrade();
        String s1 = "Last year you needed a grade of " + grade + " to get into " + studyProgram + ".";
        String s2 = "The grade to get into " + studyProgram + " last year was " + grade + ".";
        String s3 = "To get into " + studyProgram + " last year you needed a grade of " + grade + ".";
        String s4 = grade + " was the grade requirement for " + studyProgram + " last year.";
        Random random = new Random();
        List<String> arr = Arrays.asList(s1, s2, s3, s4);
        int index = random.nextInt(arr.size());
        return arr.get(index);
    }

    // Method for getting girlPoints
    private String getGirlPoints(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        boolean girlPoints = studyPrograms.get(studyProgram).isGirlPoints();
        if (girlPoints) {
            return studyProgram + " does give girlspoints.";
        } else {
            return studyProgram + " does not give girlpoints";
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
        String ut = new String();
        for (String workField : commonWorkFields) {
            ut += workField + ", ";
        }
        ut = ut.substring(0, ut.length() - 2);
        String s1 = "At " + studyProgram + " you can work with: " + ut + ".";
        String s2 = ut + " are some common work fields for " + studyProgram + ".";
        String s3 = "Some of the common worlds fields for " + studyProgram + " er " + ut +".";
        Random random = new Random();
        List<String> arr = Arrays.asList(s1, s2, s3);
        int index = random.nextInt(arr.size());
        return arr.get(index);
    }

    // Method for getting studyenvironment
    private String getStudyEnvironment(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        return studyPrograms.get(studyProgram).getStudyEnvironment();
    }

    // Method for getting studentunion
    private String getStudentUnion(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        String s1 = "The student union at " + studyProgram + " is " + studyPrograms.get(studyProgram).getStudentUnion() + ".";
        String s2 = studyPrograms.get(studyProgram).getStudentUnion() + " is the student union at " + studyProgram + ".";
        String s3 = "At " + studyProgram + ", the student union is called " + studyPrograms.get(studyProgram).getStudentUnion() + ".";
        Random random = new Random();
        List<String> arr = Arrays.asList(s1, s2, s3);
        int index = random.nextInt(arr.size());
        return arr.get(index);
    }

    // Method for getting info about studentUnion
    private String getInfoStudentUnion(String union) {
        union = union.replace("\"", ""); // removes ""

        Iterator<String> iterator = studyPrograms.keySet().iterator(); // itererer gjennom studienavnene

        while (iterator.hasNext()) {
            String study = iterator.next();
            System.out.println(study);
            System.out.println(studyPrograms.get(study).getStudentUnion());
            studyPrograms.get(study).getStudentUnion().toString().equals(union);
            if (studyPrograms.get(study).getStudentUnion().toString().equals(union)) {
                return union + " is the student union at " + study + "." + " " + studyPrograms.get(study).getStudyEnvironment();
            }
        }


        return "Sorry, we could not find any informations about " + union + ".";
    }

    // Method for getting courses
    private String getCourses(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        String ut = new String();
        ArrayList<String> courses = studyPrograms.get(studyProgram).getCourses();
        for (String course : courses) {
            ut += course + ", ";
        }
        ut = ut.substring(0, ut.length() - 2);
        String s1 = "The courses at " + studyProgram + " are: " + ut + ".";
        String s2 = ut + " are some of the courses at " + studyProgram + ".";
        String s3 = "Some of the courses at " + studyProgram + " are " + ut + ".";
        Random random = new Random();
        List<String> arr = Arrays.asList(s1, s2, s3);
        int index = random.nextInt(arr.size());
        return arr.get(index);
    }

    // Method for getting union
    private String getUnion(String union) {
        union = union.replace("\"", ""); // removes ""
        return unions.get(union).getInfo();
    }

    // Method for comparing studies
    private String getCompareStudies(String studyProgram, String studyProgram1) {
        // Displays similar keywords, and nonsimilar keywords from the given studyprograms

        studyProgram = studyProgram.replace("\"", ""); // removes ""
        studyProgram1 = studyProgram1.replace("\"", ""); // removes ""

        ArrayList<String> keyWordsStudyProgram = studyPrograms.get(studyProgram).getKeywords();
        ArrayList<String> keyWordsStudyProgram1 = studyPrograms.get(studyProgram1).getKeywords();

        ArrayList<String> similarKeyWords = new ArrayList<>();

        for (String keyWord : keyWordsStudyProgram) {
            if (keyWordsStudyProgram1.contains(keyWord)) {
                similarKeyWords.add(keyWord);
            }
        }

        for (String keyWord : similarKeyWords) {
            keyWordsStudyProgram.remove(keyWord);
            keyWordsStudyProgram1.remove(keyWord);
        }

        String ut = "";

        if (similarKeyWords.size() == 0) {
            ut += "There are no similarities";
        } else {
            ut += "The following are some similarities between the studies: ";
            for (String keyWord : similarKeyWords) {
                ut += keyWord + ", ";
            }
            ut = ut.substring(0, ut.length() - 2) + ".";
            ut += "\n\nHowever, some differences are: \n\n" + studyProgram + " is associated with: ";
            for (String keyWord : keyWordsStudyProgram) {
                ut += keyWord + ", ";

            }
            ut = ut.substring(0, ut.length() - 2) + ".";

            ut += "\n\n" + studyProgram1 + " meanwhile relates to: ";
            for (String keyWord : keyWordsStudyProgram1) {
                ut += keyWord + ", ";
            }
            ut = ut.substring(0, ut.length() - 2) + ".";
        }

        return ut;
    }

    // Method for getting all studies
    private String getAllStudies() {
        String ut = new String();
        Set<String> keys = studyPrograms.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            ut += iterator.next() + ", ";
        }
        ut = ut.substring(0, ut.length() - 2);
        String s1 = "The studies we support are: " + ut + ".";
        String s2 = "We support the following studies: " + ut + ".";
        String s3 = ut + " are the studies we currently support.";
        Random random = new Random();
        List<String> arr = Arrays.asList(s1, s2, s3);
        int index = random.nextInt(arr.size());
        return arr.get(index);
    }

    // Method for telling the user about himself
    private String getUserInfo() {
        String ut = "";

        if (userInfo.getGender() != '\u0000' && userInfo.getGender() == 'M') {
            ut += "You are a Male";
        } else if (userInfo.getCalculatedGrade() != '\u0000') {
            ut += "You are a Female";
        }
        if (ut.equals("")) {

        }
        ut += " born in " + userInfo.getBirthYear() + " and";

        ut += " you have " + userInfo.getCalculatedGrade() + " points to apply with.\nYour interests are: ";

        // interests har alltid 'studies' så trenger ikke sjekke
        for (String interest : userInfo.getInterests()) {
            ut += interest + ", ";
        }

        return ut.substring(0, ut.length() - 2) + ".";
    }

    // Method for adding an interest to the user
    private String addInterest(ArrayList<String> interests) {
        ArrayList<String> existingInterest = userInfo.getInterests();

        // Removes "" from all interests
        for (String interest : interests) {
            interests.set(interests.indexOf(interest), interest.replace("\"", ""));
            //interests.set(interests.indexOf(i), i.replace("[", ""));
            // interests.set(interests.indexOf(i), i.replace("]", ""));
        }

        ArrayList<String> alreadyAddedList = new ArrayList<>();

        int size = interests.size();
        for (int i = 0; i < size; i++) {
            if (existingInterest.contains(interests.get(i))) {
                alreadyAddedList.add(interests.get(i));
            }
        }

        for (String i : alreadyAddedList) {
            interests.remove(i);
        }

        String ut = "";

        if (interests.size() > 0) {
            ut = "We've just added ";
            for (String i : interests) {
                userInfo.addInterests(i);
                ut += i + ", ";
            }

            userInfo.updateFirebase();

            ut = ut.substring(0, ut.length() - 2) + " to your interests. ";

            if (alreadyAddedList.size() > 0) {
                ut += "We did not add ";
                for (String interest : alreadyAddedList) {
                    ut += interest + ", ";
                }
                ut = ut.substring(0, ut.length() - 2) + ", because it was already added as your interests. ";
            }
            return ut;
        }


        if (alreadyAddedList.size() > 0) {
            ut += "We have already added: ";
            for (String interest : alreadyAddedList) {
                ut += interest + ", ";
            }
            ut = ut.substring(0, ut.length() - 2) + ".";
        }

        return ut;
    }

    // Method for checking if user gets into
    private String getInto(String studyProgram) {

        studyProgram = studyProgram.replace("\"", ""); // removes ""
        String ut = "";
        double grade = userInfo.getCalculatedGrade();

        if (!userInfo.getCourses().contains("Fysikk 1") && !studyProgram.equals("Informatics")) {
            ut += "\nYou need to pass the course Fysikk 1 in order to to get into " + studyProgram + ". \n";
        }

        if (!studyProgram.equals("Informatics") && userInfo.getR2Grade() < 4) {

            ut += "You need a grade of 4 or higher at the course R2, while you have " + userInfo.getR2Grade() + ". Therefore, you need to retake the exam in order to apply. \n";


            if (studyPrograms.get(studyProgram).isGirlPoints() && userInfo.getGender() == 'F') {
                grade += 2;
            }
            if (studyPrograms.get(studyProgram).getGrade() < grade) {
                ut += "Your grade of " + grade + " is higher than last years grade of " + studyPrograms.get(studyProgram).getGrade() + " at " + studyProgram + ".";
            } else {
                ut += "Your grade of " + grade + " is lower than last year grade of " + studyPrograms.get(studyProgram).getGrade() + " at " + studyProgram + ".";
            }
            return ut;

        }


        if (studyPrograms.get(studyProgram).isGirlPoints()) {
            grade += 2;
        }
        if (studyPrograms.get(studyProgram).getGrade() < grade) {
            ut = "Your grade of " + grade + " is higher than last years grade of " + studyPrograms.get(studyProgram).getGrade() + " at " + studyProgram + ".";
        } else {
            ut = "Your grade of " + grade + " is lower than last year grade of " + studyPrograms.get(studyProgram).getGrade() + " at " + studyProgram + ".";
        }

        return ut;
    }

    // Method for recommending the user a study
    private String recommendStudy() {
        // Henter alle interessene til brukeren, og sammenligner med keywordene til alle studiene.
        // Legger til en int til hvert studie, det studiet med høyest ints, blir anbefalt.

        HashMap<String, Integer> pointMap = new HashMap<>(); // hashmap som skal inneholder alle studienavnene, og koble det opp mot antall keywordstreff

        ArrayList<String> interests = userInfo.getInterests(); // interessene til brukeren

        Iterator<String> iterator = studyPrograms.keySet().iterator(); // iterator som går gjennom alle studienavnene

        HashMap<String, ArrayList<String>> keyWords = new HashMap<>(); // hashmap som skal holde alle interessene til hvert studie

        HashMap<String, ArrayList<String>> matchedInterests = new HashMap<>(); // hashmap som skal holde på alle interessene

        if (interests.size() == 1) {
            return "You have not told us any of your interests. If you tell us about some of your interests, we will be able to recommend a suitable study";
        }

        while (iterator.hasNext()) {
            String study = iterator.next();
            keyWords.put(study, studyPrograms.get(study).getKeywords());
            pointMap.put(study, 0);
            matchedInterests.put(study, new ArrayList<String>());
        }

        // går gjennom alle studiene, legger til poeng på pointsMap, om interessen er en av keywordsa
        for (String study : studyPrograms.keySet()) {
            for (String interest : interests) {
                if (interest != null) {
                    interest = interest.toLowerCase();
                }

                if (keyWords.get(study).contains(interest)) {
                    pointMap.put(study, pointMap.get(study) + 1); // legger til 1 verdi på det gitte studiet
                    matchedInterests.get(study).add(interest);  // legger til interessen til studiet
                }
            }
        }

        Iterator<String> iterator1 = studyPrograms.keySet().iterator();
        if (iterator1.hasNext()) {
            String bestStudy = iterator1.next();

            while (iterator1.hasNext()) {
                String nextStudy = iterator1.next();
                if (pointMap.get(bestStudy) < pointMap.get(nextStudy)) {
                    bestStudy = nextStudy;
                }
            }

            if (matchedInterests.get(bestStudy).size() == 0) {
                return "We could not find any suitable study. Please tell us more about your interests.";
            }

            String ut = "We think you might like " + bestStudy + " because of the following interests: ";


            for (String interest : matchedInterests.get(bestStudy)) {
                ut += interest + ", ";
            }

            return ut.substring(0, ut.length() - 2) + ".";
        }

        return "You have not told us any of your interests. If you tell us your interests, we could better help you find a suitable study"; // Hvis den kommer hit, er interesselisten tom

    }

    // Method for starting interview
    private String interview() {
        return "startInterview";
    }

    private String help() {

        String ut = "Here are some of the things I can help you with:\n" +
                "- Get a list of all studies\n" +
                "- Explore a specific study\n" +
                "- Learn grade requirements\n" +
                "- Submit interests\n" +
                "- Get study recommendations\n" +
                "- Compare studies to each other\n" +
                "- Know about study environments\n" +
                "- Learn about Trondheim or NTNU\n" +
                "- Get familiar with common work fields\n" +
                "- Explore unions and student unions";
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
            UserInfo user = new UserInfo(1996, 53.6, new ArrayList<>(Arrays.asList("Matematikk R1",
                    "Matematikk R2")), new ArrayList<>(Arrays.asList("Folkehøgskole")), 'M', 5,
                    new ArrayList<>(Arrays.asList("Studies", "web development")));
            aiResponse = new ProcessAiResponse(studyProgramInfoMap, user, null);
        }

        @Test
        public void testConstructor() throws Exception {
            ProcessAiResponse res = new ProcessAiResponse(
                    aiResponse.studyPrograms, aiResponse.userInfo, null);
            assertEquals(res.studyPrograms, aiResponse.studyPrograms);
            assertEquals(res.userInfo, aiResponse.userInfo);
            assertTrue(res.userInfo.getInterests().contains("Studies"));
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

        @Test
        public void testGetCompareStudies() throws Exception {
            assertTrue(!aiResponse.getCompareStudies("Informatics",
                    "Computer Science").isEmpty());
        }

        @Test
        public void testGetAllStudies() throws Exception {
            assertTrue(aiResponse.getAllStudies().contains("Informatics"));
            assertTrue(aiResponse.getAllStudies().contains("Computer Science"));
        }

        @Test
        public void testGetUserInfo() throws Exception {
            assertTrue(aiResponse.getUserInfo().contains(
                    aiResponse.userInfo.getBirthYear() + ""));
            assertTrue(aiResponse.getUserInfo().contains(
                    aiResponse.userInfo.getCalculatedGrade() + ""));
        }

        @Test(expected = NullPointerException.class)
        public void testGetUnion() throws Exception {
            fail(aiResponse.getUnion(null));
        }

        @Test(expected = NullPointerException.class)
        public void testProcessAiResponse() throws Exception {
            fail(aiResponse.processAiRespons(null));
        }

        @Test(expected = IllegalStateException.class)
        public void testAddInterest() throws Exception {
            fail(aiResponse.addInterest(new ArrayList<String>(
                    Arrays.asList("Computers", "Machines"))));
        }

        @Test
        public void testGetInto() throws Exception {
            assertTrue(aiResponse.getInto("Informatics").toLowerCase()
                    .contains("higher"));
            assertTrue(aiResponse.getInto("Computer Science").toLowerCase()
                    .contains("lower"));
        }

        @Test
        public void testRecommendStudy() throws Exception {
            if (aiResponse.userInfo.getInterests().size() <= 1) {
                assertTrue(aiResponse.recommendStudy().contains(
                        "You have not told us any of your interests. If you tell us your interests," +
                                "we could better help you find a suitable study"));
            }
        }

        @After
        public void tearDown() throws Exception {
            aiResponse = null;
        }

    }
}
