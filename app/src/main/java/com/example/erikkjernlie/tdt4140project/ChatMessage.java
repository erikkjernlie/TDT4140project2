/*  ChatMessage
 *
 *  This is a chat-message object. Contains the message and if the message is from
 *  uniBOT or the user (boolean value)
 *
 *  Created by Erik Kjernlie
 *  Copyright © uniBOT
 */

package com.example.erikkjernlie.tdt4140project;

public class ChatMessage {
    public boolean left; //left equals unibot, false -> right -> the user
    public String message;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }
    @Override
    public String toString() {
        return message;
    }
}