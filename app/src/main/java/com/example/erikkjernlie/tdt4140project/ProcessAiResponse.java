package com.example.erikkjernlie.tdt4140project;

import java.util.HashMap;

import ai.api.model.AIResponse;

/**
 * Created by jonas on 01.04.2017.
 */

public class ProcessAiResponse {

    private HashMap<String, StudyProgramInfo> studyPrograms;

    public ProcessAiResponse(HashMap<String, StudyProgramInfo> studyPrograms) {
        this.studyPrograms = studyPrograms;
    }


    public String processAiRespons(AIResponse aiResponse) {

        

        switch (aiResponse.getResult().getFulfillment().getSpeech().toString()) {
            case
        }


        return null;
    }
}
