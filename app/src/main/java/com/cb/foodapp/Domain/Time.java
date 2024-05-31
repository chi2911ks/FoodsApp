package com.cb.foodapp.Domain;

public class Time {
    int Id;
    String Value;
    @Override
    public String toString(){
        return Value;
    }
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
