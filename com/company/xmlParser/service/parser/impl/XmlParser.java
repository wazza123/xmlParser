package com.company.xmlParser.service.parser.impl;


import com.company.xmlParser.entity.Element;
import com.company.xmlParser.service.parser.exception.ReadSourceException;
import com.company.xmlParser.entity.Attribute;
import com.company.xmlParser.entity.Document;
import com.company.xmlParser.entity.Text;
import com.company.xmlParser.service.parser.exception.ParserException;
import com.company.xmlParser.service.parser.Parser;

import java.util.ArrayDeque;
import java.util.Queue;

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

        //stores element name, attributes name and value, in order they exists in tag
        Queue<String> elementProperties = new ArrayDeque<String>();
        String parsedTag[] = tag.split("[<\\s='\">]");

        for (String s : parsedTag) {

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
        ArrayDeque<Element> openedTags = new ArrayDeque<Element>();
        Element root = null;

        while (source.hasNext()) {

            String s = removeTabulation(source.nextString());

            //Ignore xml declaration
            if (!s.equals(BLANK_STRING)) {

                if (s.substring(0, 2).equals(XML_DECLARATION_BRACKET)) {

                    continue;
                }
            }

            if (!s.equals(BLANK_STRING)) {

                if (s.charAt(0) != OPENED_TAG_BRACKET) {

                    openedTags.peekLast().setText(new Text(s));
                }
                else if ((s.charAt(0) == OPENED_TAG_BRACKET) && (s.charAt(1) != SLASH)) {

                    openedTags.addLast(createElement(new Element(), s));

                    if (root == null) {

                        root = (openedTags.peekLast());
                    }

                }
                else if ((s.charAt(0) == OPENED_TAG_BRACKET) && (s.charAt(1) == SLASH)) {

                    if (openedTags.size() > 1) {

                        Element element = openedTags.removeLast();
                        openedTags.peekLast().addChildElement(element);
                    }
                }
            }

        }

        source.close();
        document.setRootElement(root);
        return document;
    }

    @Override
    public Document parse() throws ParserException {

        try {

            return buildDOMTree();
        }
        catch (ReadSourceException e) {

            throw new ParserException(e);
        }
    }
}
