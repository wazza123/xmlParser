package com.epam.xmlParser.service.parser.impl;


import com.epam.xmlParser.entity.Attribute;
import com.epam.xmlParser.entity.Document;
import com.epam.xmlParser.entity.Element;
import com.epam.xmlParser.entity.Text;
import com.epam.xmlParser.service.parser.Parser;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class XmlParser implements Parser {

    private FileReadSource source;

    public XmlParser(FileReadSource readSource) {

        this.source = readSource;
    }


    private String removeTabulation(String string) {

        return string.replaceAll("  ", "").trim();
    }

    /*
      parses tag string and create element with name and attributes
     */
    private Element createElement(Element element, String tag) {

        //stores element name attributes name and value in order they exists in tag
        Queue<String> elementProperties = new ArrayDeque<String>();
        String a[] = tag.split("[<\\s='\">]");

        for (String s : a) {

            if (!s.equals("")) {

                elementProperties.add(s);
            }
        }

        element.setName(elementProperties.remove());

        while (!elementProperties.isEmpty()) {

            element.setAttribute(new Attribute(elementProperties.remove(), elementProperties.remove()));
        }

        return element;
    }

    private Document buildDOMTree() {

        Document document = new Document();
        Stack<Element> openedTags = new Stack<Element>();
        Element root = null;

        while (source.hasNext()) {

            String s = removeTabulation(source.nextString());

            if (!s.equals("")) {

                if (s.charAt(0) != '<') {

                    openedTags.peek().setText(new Text(s));

                } else if ((s.charAt(0) == '<') && (s.charAt(1) != '/')) {

                    openedTags.add(createElement(new Element(), s));

                    if (root == null)
                        root = (openedTags.peek());

                } else if ((s.charAt(0) == '<') && (s.charAt(1) == '/')) {

                    if (openedTags.size() > 1) {

                        openedTags.get(openedTags.size() - 2).addChildElement(openedTags.peek());
                    }

                    openedTags.pop();

                }
            }
        }

        document.setRootElement(root);
        return document;
    }

    @Override
    public Document parse() {

        return buildDOMTree();
    }
}
