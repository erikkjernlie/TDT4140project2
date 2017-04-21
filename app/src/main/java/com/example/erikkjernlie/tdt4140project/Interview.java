package com.example.erikkjernlie.tdt4140project;

import com.google.firebase.FirebaseApp;

import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by jonas on 05.04.2017.
 */

public class Interview {
    // Class to handle interview in Chatbot
    // Needs to prompt user for interest, and call on necessary methods when the interview is finished
    // Made only to make Chatbot a more readable file

    private boolean active;
    private ArrayList<String> prompts; // prompts
    private ArrayList<String> interests;
    private ArrayList<String> usedInterests;
    private ArrayList<String> positiveResponse;
    private ArrayList<String> positiveInterests;
    private int questionCounter;
    private String lastInterest;


    public Interview() {
        interests = new ArrayList<>();
        usedInterests = new ArrayList<>();
        positiveResponse = new ArrayList<>();
        positiveInterests = new ArrayList<>();
        // adds prompts
        this.prompts = new ArrayList<>(Arrays.asList(new String[]{"Are you interested in ", "Do you like ", "Do you enjoy ", "Would you like to work with "})); // add more prompts here
        this.positiveResponse = new ArrayList<>(Arrays.asList(new String[]{"yes", "jepp", "a little bit", "yes, very much", "ja", "si", "definitely", "absolutely", "i think so",
                "yes sir", "yeah", "ye", "yeh"})); // add more positive responses

        // resets the questioncounter.
        this.questionCounter = 0;

        // adds the interest to interests
        for (String study : StudyProgramInfo.studyPrograms.keySet()) {
            for (String interest : StudyProgramInfo.studyPrograms.get(study).getKeywords()) {
                if (interest != null && !interests.contains(interest) && !UserInfo.userInfo.getInterests().contains(interest)) { // checks if its not already added, and if the user already have added interest
                    interests.add(interest);
                }
            }
        }

        if (interests.size() == 0) {
            lastInterest = "ict";
            return;
        }
        lastInterest = interests.get(new Random().nextInt(interests.size() - 1));
    }

    public String sendMessage(String message) {
        // Method to handle messages receives from user
        // Return either message to be printed to user, or null (?) if the interview is to be finished.
        usedInterests.add(lastInterest);
        if (positiveResponse.contains(message.toLowerCase())) {
            positiveInterests.add(lastInterest);
            UserInfo.userInfo.getInterests().add(lastInterest);
            UserInfo.userInfo.updateFirebase(); // do only the last time? If you do it everytime, you make sure that if they leave the chat, the interests gets saved
        }

        if (isFinished(message)) {
            this.active = false;
            String ut = "I now have enough information to try find a study you might like based on the following interests: ";
            for (String interst : UserInfo.userInfo.getInterests()) {
                if (!interst.equals("Studies")) {
                    ut += interst + ", ";
                }
            }
            return ut.substring(0, ut.length() - 2) + ".";
        }
        questionCounter++;
        return getQuestion();
    }

    private boolean isFinished(String message) {
        message = message.replace("\"", ""); // removes ""
        // checks if the user wants to stop the interview
        // if 'quit', or the user have said enough;
        if ((questionCounter > 10 && checkEnoughInfo()) || questionCounter > 40 || message.toLowerCase().equals("quit")) {
            return true;
        }
        return false;
    }

    public String getQuestion() {
        Random random = new Random();

        String prompt = prompts.get(random.nextInt(prompts.size() - 1));
        String interest = interests.get(random.nextInt(interests.size() - 1));

        int rand;
        while (interest.equals(lastInterest) || usedInterests.contains(interest)) {
            rand = random.nextInt(interests.size() - 1);
            interest = interests.get(rand);

        }
        lastInterest = interest;
        return prompt + interest + "?";
    }

    private boolean checkEnoughInfo() {
        // Method to check if we have enough info to recommend a suitable study
        // Will check if a study has a lead with at least 4. In that case we will stop the interview
        HashMap<String, Integer> pointMap = new HashMap<>(); // Hashmap that links the studyname to how many keywords thats similar to the user interests

        // This happens everytime the user answer a new question. Could be more effective
        for (String study : UserInfo.studyPrograms.keySet()) {
            pointMap.put(study, 0); // instansiate the pointMap. This could have been done in the constructor
            for (String interest : UserInfo.userInfo.getInterests()) {
                if (UserInfo.studyPrograms.get(study).getKeywords().contains(interest)) {
                    pointMap.put(study, pointMap.get(study) + 1);
                }
            }
        }

        Iterator<String> iterator = pointMap.keySet().iterator(); // Iterates over the studies
        String bestStudy = "";
        if (iterator.hasNext()) {
            bestStudy = iterator.next();
        }
        // finds the best study
        while (iterator.hasNext()) {
            String nextStudy = iterator.next();
            if (pointMap.get(nextStudy) > pointMap.get(bestStudy)) {
                bestStudy = nextStudy;
            }
        }
        for (String study : pointMap.keySet()) {
            if (pointMap.get(bestStudy) - 3 < pointMap.get(study) && !bestStudy.equals(study)) {
                return false; // if the best study don't have a great enough lead
            }
        }
        return true;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
