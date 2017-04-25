package com.unibot.erikkjernlie.tdt4140project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Jørgen on 19.04.2017.
 */

public class InterviewTest {

    Interview intView;

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
                new ArrayList<>(Arrays.asList("Studies", "Data", "ICT", "IT")));
        intView = new Interview();
    }

    @Test
    public void stateTest() throws Exception {
        assertNotNull(intView = new Interview());
    }

    @Test (expected = IllegalStateException.class)
    public void sendMessageFailTest() throws Exception {
        assertTrue(intView.sendMessage("Yes").contains("?"));
    }

    @Test
    public void getQuestionTest() throws Exception {
        assertTrue(intView.getQuestion().contains("?"));
    }

    @Test
    public void setGetActiveTest() throws Exception {
        intView.setActive(true);
        assertTrue(intView.isActive());
        intView.setActive(false);
        assertFalse(intView.isActive());
    }

    @After
    public void tearDown() throws Exception {
        intView = null;
    }

}
