package com.epam.service.parser.impl;


import com.epam.entity.Document;
import com.epam.entity.Element;
import com.epam.entity.Text;
import com.epam.service.parser.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser implements Parser {

    private FileReadSource source;

    public XmlParser(FileReadSource readSource) {

        this.source = readSource;
    }

    /**
     * analyzes file content and split it on elements
    */
    private List<String> analyze(FileReadSource readSource) {

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
      return parsedFile;
    }

    private Document buildDOMTree() throws IOException {

        Document document = new Document();
        List<String> parsedElements = analyze(source);
        Stack<Element> openedTags = new Stack<Element>();

        Element root = null;
        Element element = new Element();

        for (String s: parsedElements) {

            if (s.charAt(0) != '<') {
                openedTags.peek().setText(new Text(s));
            }
             else if ( (s.charAt(0) == '<') && (s.charAt(1) != '/') ) {

                element = new Element(s);
                openedTags.add(element);

                if (root == null)
                    root = (openedTags.peek());

             }

               else if ((s.charAt(0) == '<') && (s.charAt(1) == '/')) {

                if ((openedTags.size() - 2) >= 0) {
                    openedTags.get(openedTags.size() - 2).addChildElement(openedTags.peek());
                }
                else
                    openedTags.get(openedTags.size() - 1).addChildElement(openedTags.peek());
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
