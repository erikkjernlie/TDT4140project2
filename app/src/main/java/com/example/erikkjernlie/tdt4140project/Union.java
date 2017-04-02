package com.example.erikkjernlie.tdt4140project;

import com.firebase.client.Firebase;

/**
 * Created by Jørgen on 01.04.2017.
 */

public class Union {

    private String name;
    private String info;
    private int members;
    private Firebase mRef = new Firebase("https://tdt4140project2.firebaseio.com/Unions/");

    public Union(String name, String info, int members) {
        this.name = name;
        this.info = info;
        this.members = members;
    }

    public Union(){}

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sendToFirebase() {
        mRef.child(this.name).setValue(this);
    }

    @Override
    public String toString() {
        return "Union{" +
                "members=" + members +
                ", info='" + info + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
