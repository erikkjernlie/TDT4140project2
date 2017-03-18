package com.example.erikkjernlie.tdt4140project;

public class ChatMessage {
    public boolean left;
    public String message;
 
    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }

    @Override
    public String toString(){
        return message;
    }
}