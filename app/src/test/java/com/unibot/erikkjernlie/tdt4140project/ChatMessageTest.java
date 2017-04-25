package com.unibot.erikkjernlie.tdt4140project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by erikkjernlie on 01/04/17.
 */

import static org.junit.Assert.assertEquals;


public class ChatMessageTest {

    private ChatMessage cm;

    @Before
    public void setUp(){
        cm = new ChatMessage(true, "test");
    }

    @Test
    public void testChatMessage() throws Exception {
        assertEquals(true, cm.message.equals("test"));
        assertEquals(true, cm.left);
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(true, cm.toString().equals("test"));
    }

    @After
    public void tearDown() throws Exception {
        cm = null;
    }
}
