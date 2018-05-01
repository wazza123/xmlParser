package com.company.xmlParser.main;

import com.company.xmlParser.entity.Attribute;
import com.company.xmlParser.entity.Document;
import com.company.xmlParser.entity.Element;
import com.company.xmlParser.service.parser.Parser;
import com.company.xmlParser.service.parser.exception.ParserException;
import com.company.xmlParser.service.parser.impl.FileReadSource;
import com.company.xmlParser.service.parser.impl.XmlParser;

import java.io.File;

public class Main {

    public static void showDOMTreeStructure(Element root) {

        System.out.print("<" + root.getName());

        if (root.getAttributes() != null) {

            for (Attribute a : root.getAttributes()) {

                System.out.print(" " + a.getName() + "=" + a.getValue());
            }
        }

        System.out.println(">");

        if (root.getText() != null) {

            System.out.println(root.getText());
        }

        for (Element elements : root.getChildElements()) {

            showDOMTreeStructure(elements);
        }
        System.out.println("</" + root.getName() + ">");
    }

    public static void main(String[] args) {

        File file = new File("nodes.xml");
        Document document;
        FileReadSource readSource;
        readSource = new FileReadSource(file);
        Parser parser = new XmlParser(readSource);

        try {

            document = parser.parse();
        }
        catch (ParserException e) {

            e.printStackTrace();
            return;
        }

        if (document != null) {

            showDOMTreeStructure(document.getRootElement());
        }
    }
}
