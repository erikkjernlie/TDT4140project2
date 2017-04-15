package com.example.erikkjernlie.tdt4140project;

import java.util.HashMap;

/**
 * Created by Jørgen on 01.04.2017.
 */

public class Union {

    private String name;
    private String info;
    private int members;
    public static HashMap<String, Union> unions = new HashMap<>();


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



    @Override
    public String toString() {
        return "Union{" +
                "members=" + members +
                ", info='" + info + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
