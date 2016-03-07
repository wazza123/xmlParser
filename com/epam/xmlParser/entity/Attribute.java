package com.epam.xmlParser.entity;


public class Attribute extends Node {

    private String name;
    private String value;

    public Attribute() {}

    public Attribute(String name, String value) {

        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
