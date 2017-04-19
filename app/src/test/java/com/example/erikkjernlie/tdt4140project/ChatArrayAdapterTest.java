package com.example.erikkjernlie.tdt4140project;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Jørgen on 19.04.2017.
 */



public class ChatArrayAdapterTest {

    ChatArrayAdapter caa;
    Context context;

    @Before
    public void setUp() throws Exception {
        caa = new ChatArrayAdapter(context, 0);
    }

    @Test
    public void addChatMessageTest() throws Exception {
        ChatMessage message = new ChatMessage(true, "hey");
        caa.add(message);
        assertTrue(caa.getCount() == 1);
        assertTrue(message.equals(caa.getItem(0)));
    }

    @After
    public void tearDown() throws Exception {
        caa = null;
    }

}
