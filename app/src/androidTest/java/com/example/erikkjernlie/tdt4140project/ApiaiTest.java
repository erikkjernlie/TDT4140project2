package com.example.erikkjernlie.tdt4140project;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.ExecutionError;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ai.api.model.AIResponse;

import static org.junit.Assert.assertNotNull;

/**
 * Created by jonas on 22.03.2017.
 */
@RunWith(AndroidJUnit4.class)
public class ApiaiTest extends ActivityInstrumentationTestCase2<ChatBot> {

    private ChatBot chatBot;
    Activity activity;

    public ApiaiTest(String pkg, Class<ChatBot> activityClass) {
        super(ChatBot.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInsrumentation(InstrumentationRegistry.getInstrumentation());
        activity = getActivity();
    }
/*
    Får følgende feil:
    'getAiResponse' has private accesss
    Dersom man gjør metoden public får man feil:
    'incompatible type, AIResponse and void

    @Test
    public void testGetAiResponse() {
      AIResponse aiResponse = chatBot.getAiResponse("a");
      while (aiResponse == null) {
           aiResponse =  chatBot.getAiResponse("a");
        }
      // (aiResponse.getResult().getFulfillment().getSpeech().toString()); // tests if it returns an actual string
      assertNotNull(aiResponse.getResult().getFulfillment().getSpeech().toString());
    }
*/
}
