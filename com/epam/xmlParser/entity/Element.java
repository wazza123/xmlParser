package com.epam.xmlParser.entity;

import java.util.ArrayList;
import java.util.List;


public class Element extends Node {

    private String name;
    private List<Attribute> attributes;
    private Text text;
    private List<Element> elements = new ArrayList<Element>();

    public Element() {}

    public Element(String name) {

        this.name = name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public String getName() {
        return name;
    }

    public void setAttribute(Attribute attribute) {

        if (attributes == null)
            attributes = new ArrayList<Attribute>();

        this.attributes.add(attribute);
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
