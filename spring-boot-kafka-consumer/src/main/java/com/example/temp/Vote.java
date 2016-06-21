package com.example.temp;

import com.example.model.Ipojo;

public class Vote implements Ipojo{
    private String name;
 
    public Vote(String name) {
        this.name = name;
    }
 
    public Vote() {
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
}