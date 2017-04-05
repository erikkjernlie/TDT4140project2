package com.example.erikkjernlie.tdt4140project;


import com.google.android.gms.wearable.internal.StorageInfoResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.lang.reflect.Array;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import ai.api.model.AIResponse;

/**
 * Created by jonas on 01.04.2017.
 */

public class ProcessAiResponse {

    private HashMap<String, StudyProgramInfo> studyPrograms;
    private UserInfo userInfo;
    private HashMap<String, Union> unions; // <studieprogram, union>

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
                return union + " is the student union at " + study + ".";
            }
        }


        return "Sorry, we could not find any informations about " + union + ".";
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
            ut += "The similar keywords are: ";
            for (String keyWord : similarKeyWords) {
                ut += keyWord + ", ";
            }
            ut = ut.substring(0, ut.length() - 2) + ".";
            ut += "\n\nThe differences are: \n\n" + studyProgram + " have the keywords: ";
            for (String keyWord : keyWordsStudyProgram) {
                ut += keyWord + ", ";

            }
            ut = ut.substring(0, ut.length() - 2) + ".";

            ut += "\n\n" + studyProgram1 + " have the keywords: ";
            for (String keyWord : keyWordsStudyProgram1) {
                ut += keyWord + ", ";
            }
            ut = ut.substring(0, ut.length() - 2) + ".";
        }

        return ut;
    }

    // Method for getting all studies
    private String getAllStudies() {
        String ut = "The studies we support are: ";
        Set<String> keys = studyPrograms.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            ut += iterator.next() + ", ";
        }
        ut = ut.substring(0, ut.length() - 2) + ".";
        return ut;
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

        return ut.substring(0,ut.length()-2) + ".";
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

            if (alreadyAddedList.size() > 0 ) {
                ut += "We did not add ";
                for (String interest : alreadyAddedList) {
                    ut += interest + ", ";
                }
                ut = ut.substring(0, ut.length()-2) + ", because it was already added as your interests. ";
            }
            return ut;
        }


        if (alreadyAddedList.size() > 0) {
            ut += "We have already added: ";
            for (String interest : alreadyAddedList) {
                ut += interest + ", ";
            }
            ut = ut.substring(0, ut.length()-2 ) + ".";
        }

        return ut;
    }

    // Method for checking if user gets into
    private String getInto(String studyProgram) {

        studyProgram = studyProgram.replace("\"", ""); // removes ""
        String ut = "";
        double grade = userInfo.getCalculatedGrade();

        if (!userInfo.getCourses().contains("Fysikk 1") && !studyProgram.equals("Informatics")) {
            ut += "\nYou need to take the course Fysikk 1. Therefore you need to take the exam to get into " + studyProgram + ". \n";
        }

        if (!studyProgram.equals("Informatics") && userInfo.getR2Grade() < 4 ) {

            ut += "You need at least 4 at the course R2, you have " + userInfo.getR2Grade() + ".\nTherefore you need to retake the exam. \n";


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
            return "You have not told us any of your interests. If you tell us your interests, we could better help you find a suitable study";
        }

        while (iterator.hasNext()) {
            String study = iterator.next();
            keyWords.put(study, studyPrograms.get(study).getKeywords());
            pointMap.put(study, 0);
            matchedInterests.put(study, new ArrayList<String>());
        }

        // går gjennom alle studiene, legger til poeng på pointsMap, om interessen er en av keywordsa
        for (String study : studyPrograms.keySet()) {
            System.out.println(study);
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
                return "We could not find any suitable study. Please tell us more about your interests, so that we can help you find a study. ";
            }

            String ut = "We think you might like " + bestStudy + " because you have interests which the study might match. The " +
                    "interests that matched was: ";


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

    // Method for getting all studies which has that interest
    private String getStudiesFromInterest(String interest) {
        interest = interest.replace("\"", ""); // removes ""
        ArrayList<String> studiesWhichHasInterest = new ArrayList<>();

        for(String study : studyPrograms.keySet()) {
            if (studyPrograms.get(study).getKeywords().contains(interest)) {
                studiesWhichHasInterest.add(study);
            }
        }

        // Different answer for different amount of studies
        if (studiesWhichHasInterest.size() == 1) {
            return studiesWhichHasInterest.get(0) + " have the keyword " + interest + ".";
        } else if (studiesWhichHasInterest.size() == 0) {
            return "There is no study which has " + interest + " as a keyword.";
        } else {
            String ut = "The studies ";
            for (String study : studiesWhichHasInterest) {
                ut += study + ", ";
            }
            return ut.substring(0, ut.length()-2) + " has " + interest + " as keyword.";
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

    private String getInfoUnibot(){
        return "I can help you with almost everything that has to do with studies at NTNU Gløshaugen, as well as various other student activities " +
                "like different unions and life in Trondheim. \n\nTry ask me anything, or check out the “HELP”-menu for tips. Perhaps you’d even want " +
                "to sit back and let me interview you and recommend a study? If so, just let me know! \n\nThe application was made by four students at " +
                "the Engineering and ICT program, with the goal of assisting possible applicants choose the right study based on their individual " +
                "preferences. You can read about them at the “About us”-page.";
    }
}
