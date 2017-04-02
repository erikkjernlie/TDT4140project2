package com.example.erikkjernlie.tdt4140project;

import android.app.ProgressDialog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ai.api.model.AIResponse;
import ai.api.model.Fulfillment;
import ai.api.model.Result;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Jørgen on 02.04.2017.
 */

public class ProcessAiResponseTest {

    private ProcessAiResponse aiResponse;

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
    public void testProcessAiResponse(){
        AIResponse response = new AIResponse();
    }

    @After
    public void tearDown() throws Exception {
        aiResponse = null;
    }
}
