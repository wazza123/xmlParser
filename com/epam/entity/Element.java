package com.epam.entity;

import java.util.ArrayList;
import java.util.List;


public class Element extends Node {

    private String name;
    private Attribute attribute;
    private Text text;
    private List<Element> elements = new ArrayList<Element>();

    public Element() {}

    public Element(String name) {

        this.name = name;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public String getName() {
        return name;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Text getText() {
        return text;
    }

    public void addChildElement(Element child) {

        elements.add(child);
    }

    public List<Element> getChildElements() {

        return elements;
    }
}
