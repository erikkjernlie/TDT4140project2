package com.unibot.erikkjernlie.tdt4140project;


import com.google.gson.JsonElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ai.api.model.AIResponse;

import static com.unibot.erikkjernlie.tdt4140project.StudyProgramInfo.studyPrograms;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jonas on 01.04.2017.
 */
@RunWith(Enclosed.class)
public class ProcessAiResponse {

    private ProcessAiResponse aiResponse;

    public ProcessAiResponse(HashMap<String, StudyProgramInfo> studyPrograms, UserInfo userInfo, HashMap<String, Union> unions) {

    }

    public String processAiRespons(AIResponse aiResponse) {
        String action = aiResponse.getResult().getAction().toString();
        String method = action.substring(0, action.indexOf('('));
        String ut = "Sorry, you have just found a bug. Please don't tell Pekka. "; // Må legges til i ChatBot: Hvis "NoAnswer" så skal den bare legge til svaret den får fra APIAI
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
            case "getStudiesFromInterest":
                ut = this.getStudiesFromInterest(aiResponse.getResult().getParameters().get("Interests").toString());
                break;
            case "getStudyHasInterest":
                ut = this.getStudyHasInterest(aiResponse.getResult().getParameters().get("StudyProgram").toString(), aiResponse.getResult().getParameters().get("Interests").toString());
                break;
            case "getInfoNTNU":
                ut = this.getInfoNtnu();
                break;
            case "getInfoTrondheim":
                ut = this.getInfoTrondheim();
                break;
            case "getInfoUnibot":
                ut = this.getInfoUnibot();
                break;
            case "getSalary":
                ut = this.getSalary();
                break;

        }
        return ut;
    }

    // Method for getting study grade
    private String getGrade(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        double ordinaryGrade = studyPrograms.get(studyProgram).getOrdinaryGrade();
        double primaryGrade = studyPrograms.get(studyProgram).getFirstTimeGrade();
        String s1 = "Last year you needed a grade of " + primaryGrade + " / " + ordinaryGrade + " to get into " + studyProgram + ".";
        String s2 = "The grade to get into " + studyProgram + " last year was " + primaryGrade + " / " + ordinaryGrade + ".";
        String s3 = "To get into " + studyProgram + " last year you needed a grade of " + primaryGrade + " / " + ordinaryGrade + ".";
        String s4 = primaryGrade + " / " + ordinaryGrade + " was the grade requirement for " + studyProgram + " last year.";
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
            return studyProgram + " does give girlpoints.";
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
        String s3 = "Some of the common work fields for " + studyProgram + " are " + ut + ".";
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
            studyPrograms.get(study).getStudentUnion().toString().equals(union);
            if (studyPrograms.get(study).getStudentUnion().toString().equals(union)) {
                return union + " is the student union at " + study + ".";
            }
        }
        return "Sorry, I could not find any informations about " + union + ".";
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
        return Union.unions.get(union).getInfo();
    }

    // Method for comparing studies
    private String getCompareStudies(String studyProgram, String studyProgram1) {
        // Displays similar keywords, and nonsimilar keywords from the given studyprograms

        studyProgram = studyProgram.replace("\"", ""); // removes ""
        studyProgram1 = studyProgram1.replace("\"", ""); // removes ""

        ArrayList<String> keyWordsStudyProgram = StudyProgramInfo.studyPrograms.get(studyProgram).getKeywords();
        ArrayList<String> keyWordsStudyProgram1 = StudyProgramInfo.studyPrograms.get(studyProgram1).getKeywords();

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
                if (keyWord != null) {
                    ut += keyWord + ", ";
                }
            }
            ut = ut.substring(0, ut.length() - 2) + ".";
            ut += "\n\nHowever, some differences are: \n\n" + studyProgram + " is associated with: ";
            for (String keyWord : keyWordsStudyProgram) {
                if (keyWord != null) {
                    ut += keyWord + ", ";
                }
            }
            ut = ut.substring(0, ut.length() - 2) + ".";

            ut += "\n\n" + studyProgram1 + " related to: ";
            for (String keyWord : keyWordsStudyProgram1) {
                if (keyWord != null) {
                    ut += keyWord + ", ";
                }
            }
            ut = ut.substring(0, ut.length() - 2) + ".";
        }


        for (String keyword : similarKeyWords) {
            StudyProgramInfo.studyPrograms.get(studyProgram).getKeywords().add(keyword);
            StudyProgramInfo.studyPrograms.get(studyProgram1).getKeywords().add(keyword);
        }


        return ut;
    }

    // Method for getting all studies
    private String getAllStudies() {
        String ut = "";
        Set<String> keys = studyPrograms.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            ut += iterator.next() + ", ";
        }
        ut = ut.substring(0, ut.length() - 2);
        String s1 = "The studies I support are: " + ut + ".";
        String s2 = "I support the following studies: " + ut + ".";
        String s3 = ut + " are the studies I currently support.";
        Random random = new Random();
        List<String> arr = Arrays.asList(s1, s2, s3);
        int index = random.nextInt(arr.size());
        return arr.get(index);
    }

    // Method for telling the user about himself
    private String getUserInfo() {
        String ut = "";
        if (UserInfo.userInfo.getGender() != '\u0000' && UserInfo.userInfo.getGender() == 'M') {
            ut += "You are a male";
        } else if (UserInfo.userInfo.getCalculatedGrade() != '\u0000') {
            ut += "You are a female";
        }
        if (ut.equals("")) {
        }
        ut += " born in " + UserInfo.userInfo.getBirthYear() + " and";
        ut += " you have " + UserInfo.userInfo.calculatedFirstTimeGrade + " / " + UserInfo.userInfo.getCalculatedGrade() + " points to apply with.";

        if (!(UserInfo.userInfo.getInterests().size() == 1)) {
            ut += "\nYour interests are: ";
        }
        // interests har alltid 'studies' så trenger ikke sjekke
        for (String interest : UserInfo.userInfo.getInterests()) {
            if (!interest.equals("Studies")) {
                ut += interest + ", ";
            }
        }
        if (!(UserInfo.userInfo.getInterests().size() == 1)) {
            return ut.substring(0, ut.length() - 2) + ".";
        } else {
            return ut;
        }
    }

    // Method for adding an interest to the user
    private String addInterest(ArrayList<String> interests) {
        ArrayList<String> existingInterest = UserInfo.userInfo.getInterests();

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
            ut = "I've just added ";
            for (String i : interests) {
                UserInfo.userInfo.addInterests(i);
                ut += i + ", ";
            }
            UserInfo.userInfo.updateFirebase();
            ut = ut.substring(0, ut.length() - 2) + " to your interests. ";
            if (alreadyAddedList.size() > 0) {
                ut += "I did not add ";
                for (String interest : alreadyAddedList) {
                    ut += interest + ", ";
                }
                ut = ut.substring(0, ut.length() - 2) + ", because it was already added to your interests. ";
            }
            for (String interest : interests) {
                ut += this.getStudiesFromInterest(interests.get(0));
            }
            return ut;
        }

        if (alreadyAddedList.size() > 0) {
            ut += "I have already added: ";
            for (String interest : alreadyAddedList) {
                ut += interest + ", ";
            }
            ut = ut.substring(0, ut.length() - 2) + ".";
        }

        for (String interest : interests) {
            ut += this.getStudiesFromInterest(interests.get(0));
        }
        return ut;
    }

    // Method for checking if user gets into
    private String getInto(String studyProgram) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        String ut = "";
        double userOrdinaryGrade = UserInfo.userInfo.getCalculatedGrade();
        double userPrimaryGrade = UserInfo.userInfo.calculatedFirstTimeGrade;

        double ordinaryGrade = StudyProgramInfo.studyPrograms.get(studyProgram).getOrdinaryGrade();
        double primaryGrade = StudyProgramInfo.studyPrograms.get(studyProgram).getFirstTimeGrade();

        if (!UserInfo.userInfo.getCourses().contains("Fysikk 1") && !studyProgram.equals("Informatics")) {
            ut += "\nYou lack the required course Fysikk 1. You need to take this exam to get into " + studyProgram + ". \n";
        }

        if (!studyProgram.equals("Informatics") && UserInfo.userInfo.getR2Grade() < 4) {

            ut += "You need at least a grade of 4 in the Matematikk R2 course, while you have " + UserInfo.userInfo.getR2Grade() + ".\nTherefore you will need to retake the exam. \n";
            if (studyPrograms.get(studyProgram).isGirlPoints() && UserInfo.userInfo.getGender() == 'F') {
                userOrdinaryGrade += 2;
                userPrimaryGrade += 2;
            }
            if (ordinaryGrade < userOrdinaryGrade) {
                ut += "Your grade of " + userOrdinaryGrade + " is higher than last year's grade of " + ordinaryGrade + " at " + studyProgram + ".";
            } else if (primaryGrade < userPrimaryGrade) {
                ut += "Your grade of " + userPrimaryGrade + " is higher than last year's grade of " + primaryGrade + " at " + studyProgram + ".";
            } else {
                ut += "Your grade of " + userPrimaryGrade + " / " + userOrdinaryGrade + " is lower than last year grade of " + primaryGrade + " / " + ordinaryGrade + " at " + studyProgram + ".";
            }
            return ut;

        }
        if (studyPrograms.get(studyProgram).isGirlPoints() && UserInfo.userInfo.getGender() == 'F') {
            userOrdinaryGrade += 2;
            userPrimaryGrade += 2;
        }
        if (ordinaryGrade < userOrdinaryGrade) {
            ut += "Your grade of " + userOrdinaryGrade + " is higher than last year's grade of " + ordinaryGrade + " at " + studyProgram + ".";
        } else if (primaryGrade < userPrimaryGrade) {
            ut += "Your grade of " + userPrimaryGrade + " is higher than last year's grade of " + primaryGrade + " at " + studyProgram + ".";
        } else {
            ut += "Your grade of " + userPrimaryGrade + " / " + userOrdinaryGrade + " is lower than last year grade of " + primaryGrade + " / " + ordinaryGrade + " at " + studyProgram + ".";
        }
        return ut;

    }

    // Method for recommending the user a study
    private String recommendStudy() {
        // Henter alle interessene til brukeren, og sammenligner med keywordene til alle studiene.
        // Legger til en int til hvert studie, det studiet med høyest ints, blir anbefalt.

        HashMap<String, Integer> pointMap = new HashMap<>(); // hashmap som skal inneholder alle studienavnene, og koble det opp mot antall keywordstreff

        ArrayList<String> interests = UserInfo.userInfo.getInterests(); // interessene til brukeren

        Iterator<String> iterator = studyPrograms.keySet().iterator(); // iterator som går gjennom alle studienavnene

        HashMap<String, ArrayList<String>> keyWords = new HashMap<>(); // hashmap som skal holde alle interessene til hvert studie

        HashMap<String, ArrayList<String>> matchedInterests = new HashMap<>(); // hashmap som skal holde på alle interessene

        if (interests.size() == 1) {
            return "You have not told me any interests. If you tell me your interests, I could better help you find a suitable study";
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
                return "I could not find any suitable study. Please tell me more about your interests, so that I can help you find a study. ";
            }
            String ut = "I think you might like " + bestStudy + " because you have interests that match. The " +
                    "interests that match are: ";
            for (String interest : matchedInterests.get(bestStudy)) {
                ut += interest + ", ";
            }
            return ut.substring(0, ut.length() - 2) + ".";
        }
        return "You have not told me any of your interests. If you tell me your interests, I could better help you find a suitable study"; // Hvis den kommer hit, er interesselisten tom
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

    private String getStudiesFromInterest(String interest) {
        interest = interest.replace("\"", ""); // removes ""
        ArrayList<String> studiesWhichHasInterest = new ArrayList<>();

        for (String study : studyPrograms.keySet()) {
            if (studyPrograms.get(study).getKeywords().contains(interest)) {
                studiesWhichHasInterest.add(study);
            }
        }

        // Different answer for different amount of studies
        if (studiesWhichHasInterest.size() == 1) {
            return studiesWhichHasInterest.get(0) + " have the keyword " + interest + ".";
        } else if (studiesWhichHasInterest.size() == 0) {
            return "";
        } else {
            String ut = "The studies ";
            for (String study : studiesWhichHasInterest) {
                ut += study + ", ";
            }
            return ut.substring(0, ut.length() - 2) + " has " + interest + " as keyword.";
        }


    }

    private String getStudyHasInterest(String studyProgram, String interest) {
        studyProgram = studyProgram.replace("\"", ""); // removes ""
        interest = interest.replace("\"", ""); // removes ""
        System.out.println(studyPrograms);
        if (studyPrograms.get(studyProgram).getKeywords().contains(interest)) {
            return studyProgram + " has the interest " + interest + ".";
        }
        return studyProgram + " don't have the interest " + interest + ".";
    }

    private String getInfoNtnu() {
        return "The Norwegian University of Science and Technology is a public research university with campuses in the cities of Trondheim, Gjøvik and Ålesund. " +
                "\nNTNU has the main national responsibility for higher education in engineering and technology, and gather more than 30.000 students in Trondheim " +
                "alone, about half of which are connected to the technical subjects. \nSince its formation in 1996, the university has grown to become nationally and " +
                "internationally recognized, both within education and research. \nYou can read more about NTNU at www.ntnu.no";
    }

    private String getInfoTrondheim() {
        return "With about 170.000 inhabitants, of which over 40.000 are students, Trondheim is the third largest city in Norway. " +
                "\nThe city is dominated by its large institutions such as the Norwegian University of Science and Technology, SINTEF " +
                "Research Center and the St. Olavs University Hospital. \nTrondheim has several times been voted Norway’s best city for " +
                "students, with good reason. Social happenings such as ISFiT, UKA and many others make sure there’s always something new " +
                "to explore, in addition to other cultural meeting points like Trøndelag Teater and cinemas.";
    }

    private String getInfoUnibot() {
        return "I can help you with almost everything that has to do with studies at NTNU Gløshaugen, as well as various other student activities " +
                "like different unions and life in Trondheim. \n\nTry ask me anything, or check out the “HELP”-menu for tips. Perhaps you’d even want " +
                "to sit back and let me interview you and recommend a study? If so, just let me know! \n\nThe application was made by four students at " +
                "the Engineering and ICT program, with the goal of assisting possible applicants choose the right study based on their individual " +
                "preferences. You can read about them at the “About us”-page.";
    }

    private String getSalary() {
        return "Newly educated sivil engineers earn approximately 500 000 NOK.";
    }

    public static class ProcessAiResponseTest {
        ProcessAiResponse aiResponse;

        @Before
        public void setUp() throws Exception {
            StudyProgramInfo.studyPrograms.put("Informatics", new StudyProgramInfo(51.2, 52.4, false,
                    new ArrayList<String>(Arrays.asList("Data", "IKT", "IT", "HTML")),
                    new ArrayList<String>(Arrays.asList("Data", "Consultant", "Web design")), "Informatics is a 3-year bachelor program that deals with the planning, development, improvement, evaluation and use of programs and computer systems. After 3 years, the students may embark on their working life or apply for a 2 year Masters program.",
                    "The study evironment is focused around the student union Online. They arrange a lot of different activities, both related and unrealted to computers and data technology. Many students are engaged in unions such as Hackerspace, where you can get familiar with interesting subjects such as use of VR and building computers.",
                    "Online", new ArrayList<String>(Arrays.asList("EXPH0004", "TDT4110", "MA0001"))));

            StudyProgramInfo.studyPrograms.put("Computer Science", new StudyProgramInfo(57.0, 57.5, true,
                    new ArrayList<String>(Arrays.asList("Data", "IKT", "IT", "Programming")),
                    new ArrayList<String>(Arrays.asList("Data", "Consultant", "Programmer")), "The Computer Science programme of study is a 5 years Master of Science (sivilingeniør) programme. Computer Science is not only about excellent computer skills - it also deals with contributing to the social development. How can we improve existing systems? What could be useful in the future? With computer engineering skills you can create computer systems which people need, want, or not yet know that they need.",
                    "The student union Abakus is very popular among students and arrange many different activities for all students at Computer Science.",
                    "Abakus", new ArrayList<String>(Arrays.asList("EXPH0004", "TDT4110", "TMA4100"))));
            UserInfo.userInfo = new UserInfo(1996, 53.6, 0, 0, 0, 0, 0, 0, new ArrayList<>(Arrays.asList("Matematikk R1",
                    "Matematikk R2")), new ArrayList<>(Arrays.asList("Folkehøgskole")), 'M', 5,
                    new ArrayList<>(Arrays.asList("Studies", "web development")));
            aiResponse = new ProcessAiResponse(StudyProgramInfo.studyPrograms, UserInfo.userInfo, null);
        }

        @Test
        public void testConstructor() throws Exception {
            ProcessAiResponse res = new ProcessAiResponse(
                    studyPrograms, UserInfo.userInfo, null);
            assertEquals(studyPrograms, studyPrograms);
            assertEquals(studyPrograms, studyPrograms);
            assertTrue(UserInfo.userInfo.getInterests().contains("Studies"));
        }

        @Test
        public void testGetGrade() throws Exception {
            assertTrue(aiResponse.getGrade("Informatics").contains("51.2"));
            assertFalse(aiResponse.getGrade("Informatics").contains("51.0"));
        }

        @Test
        public void testGetGirlPoints() throws Exception {
            assertEquals(aiResponse.getGirlPoints("Computer Science"),
                    "Computer Science" + " does give girlpoints.");
            assertFalse(aiResponse.getGirlPoints("Computer Science").
                    equals("Computer Science" + " does not give girlpoints."));

            assertFalse(aiResponse.getGirlPoints("Informatics").
                    equals("Informatics" + " does give girlpoints"));
        }

        @Test
        public void testGetInfo() throws Exception {
            assertEquals(aiResponse.getInfo("Informatics"),
                    "Informatics is a 3-year bachelor program that deals with the planning, development, improvement, evaluation and use of programs and computer systems. After 3 years, the students may embark on their working life or apply for a 2 year Masters program.");

        }

        @Test
        public void testGetCommonWorkFields() throws Exception {
            assertTrue(aiResponse.getCommonWorkFields("Computer Science").contains("Data, Consultant, Programmer"));
        }

        @Test
        public void testGetStudyEnvironment() throws Exception {
            assertEquals(aiResponse.getStudyEnvironment("Informatics"),
                    "The study evironment is focused around the student union Online. They arrange a lot of different activities, both related and unrealted to computers and data technology. Many students are engaged in unions such as Hackerspace, where you can get familiar with interesting subjects such as use of VR and building computers.");
        }

        @Test
        public void testGetStudentUnion() throws Exception {
            assertTrue(aiResponse.getStudentUnion("Computer Science").contains("Abakus"));
        }

        @Test
        public void testGetCourses() throws Exception {
            assertTrue(aiResponse.getCourses("Informatics").contains("EXPH0004, TDT4110, MA0001"));
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
                    UserInfo.userInfo.getBirthYear() + ""));
            assertTrue(aiResponse.getUserInfo().contains(
                    UserInfo.userInfo.getCalculatedGrade() + ""));
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
            if (UserInfo.userInfo.getInterests().size() <= 1) {
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
