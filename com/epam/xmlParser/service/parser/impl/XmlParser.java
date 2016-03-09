package com.epam.xmlParser.service.parser.impl;


import com.epam.xmlParser.entity.Attribute;
import com.epam.xmlParser.entity.Document;
import com.epam.xmlParser.entity.Element;
import com.epam.xmlParser.entity.Text;
import com.epam.xmlParser.service.parser.Parser;
import com.epam.xmlParser.service.parser.exeption.ReadSourceException;

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

    private Document buildDOMTree() throws ReadSourceException {

        final String BLANK_STRING = "";
        final char OPENED_TAG_BRACKET = '<';
        final char SLASH = '/';
        final String XML_DECLARATION_BRACKET = "<?";

        Document document = new Document();
        Stack<Element> openedTags = new Stack<Element>();
        Element root = null;

        while (source.hasNext()) {

            String s = removeTabulation(source.nextString());

            //Ignore xml declaration
            if (!s.equals("")) {

                if (s.substring(0, 2).equals(XML_DECLARATION_BRACKET)) {

                    continue;
                }
            }

            if (!s.equals(BLANK_STRING)) {

                if (s.charAt(0) != OPENED_TAG_BRACKET) {

                    openedTags.peek().setText(new Text(s));
                }
                else if ((s.charAt(0) == OPENED_TAG_BRACKET) && (s.charAt(1) != SLASH)) {

                    openedTags.add(createElement(new Element(), s));

                    if (root == null) {

                        root = (openedTags.peek());
                    }

                }
                else if ((s.charAt(0) == OPENED_TAG_BRACKET) && (s.charAt(1) == SLASH)) {

                    if (openedTags.size() > 1) {

                        openedTags.get(openedTags.size() - 2).addChildElement(openedTags.peek());
                    }

                    openedTags.pop();
                }
            }

        }

        source.close();
        document.setRootElement(root);
        return document;
    }

    @Override
    public Document parse() throws ReadSourceException {

        return buildDOMTree();
    }
}
