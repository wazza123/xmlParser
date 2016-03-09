package com.epam.xmlParser.entity;


public class Text extends Node {

    private String text;

    public Text() {}

    public Text(String text) {

        this.text = text;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    @Override
    public String toString() {

        return text;
    }
}
