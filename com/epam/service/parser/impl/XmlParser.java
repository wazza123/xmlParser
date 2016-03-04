package com.epam.service.parser.impl;


import com.epam.entity.Attribute;
import com.epam.entity.Document;
import com.epam.entity.Element;
import com.epam.entity.Text;
import com.epam.service.parser.Parser;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser implements Parser {

    private FileReadSource source;

    public XmlParser(FileReadSource readSource) {

        this.source = readSource;
    }

    /*
      analyzes file content and split it on elements
    */
    private List<String> split(FileReadSource readSource) {

        String xmlString = readSource.read();
        StringBuilder stringBuilder = new StringBuilder(xmlString);

        Pattern pattern = Pattern.compile("(<.+?>)");
        Matcher matcher = pattern.matcher(xmlString);
        List<String> parsedFile = new ArrayList<String>();

        int previousEnd = 1;
        while (matcher.find()) {

            if (matcher.start() > previousEnd)
                if((matcher.start() != previousEnd)) {

                    parsedFile.add(xmlString.substring(previousEnd,matcher.start()));
                }

            parsedFile.add(matcher.group());
            previousEnd = matcher.end();
        }

      return removeTabulation(parsedFile);
    }

    private List<String> removeTabulation(List<String> list) {

        List<String> l = list;

        for (int i = 0; i < l.size(); i++) {

            String s = l.get(i);
            l.remove(i);
            l.add(i, s.replaceAll("  ", "").trim());
        }

        for (int i = 0; i < l.size(); i++) {

            if (l.get(i).equals(""))
                l.remove(i);
        }
       return l;
    }

    /*
      parses tag string and create element with name and attributes
     */
    private Element createElement(Element element, String tag) {

        Element el = element;

        //stores element name attributes name and value in order they exists in tag
        Queue<String> elementProperties = new ArrayDeque<String>();
        String a[] = tag.split("[<\\s='\">]");

        for (String s : a )
        if (!s.equals(""))
            elementProperties.add(s);

        element.setName(elementProperties.remove());

        while (!elementProperties.isEmpty()){

            el.setAttribute(new Attribute(elementProperties.remove(),elementProperties.remove()));
        }

        return el;
    }

    private Document buildDOMTree() throws IOException {

        Document document = new Document();
        List<String> parsedElements = split(source);
        Stack<Element> openedTags = new Stack<Element>();

        Element root = null;

        for (String s: parsedElements) {

            if (s.charAt(0) != '<') {
                openedTags.peek().setText(new Text(s));
            }
             else if ( (s.charAt(0) == '<') && (s.charAt(1) != '/') ) {

                openedTags.add(createElement( new Element() , s ));

                if (root == null)
                    root = (openedTags.peek());

             }

               else if ((s.charAt(0) == '<') && (s.charAt(1) == '/')) {

                if (openedTags.size() > 1) {
                    openedTags.get(openedTags.size() - 2).addChildElement(openedTags.peek());
                }

                openedTags.pop();

                }
        }

        document.setRootElement(root);
        return document;
    }

    @Override
    public Document parse() {

             Document document = null;

        try {
            document = buildDOMTree();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;

    }

}
