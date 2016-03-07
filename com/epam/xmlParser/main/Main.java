package com.epam.xmlParser.main;

import com.epam.xmlParser.entity.Attribute;
import com.epam.xmlParser.entity.Document;
import com.epam.xmlParser.entity.Element;
import com.epam.xmlParser.service.parser.impl.FileReadSource;
import com.epam.xmlParser.service.parser.impl.XmlParser;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void showDOMTreeStructure(Element root) {

       System.out.print("<" + root.getName());

        if (root.getAttributes() != null) {

            for (Attribute a : root.getAttributes()) {

                System.out.print(" " + a.getName() + "=" + a.getValue());
            }
        }

        System.out.println(">");

        if (root.getText() != null)
        System.out.println(root.getText());

        for (Element elements : root.getChildElements()) {

            showDOMTreeStructure(elements);
        }
        System.out.println("</" + root.getName() + ">");
    }

    public static void main(String[] args) throws IOException {

        File file = new File("C:\\Users\\home\\Desktop\\pom.xml");
        Document document = null;

        if (file.exists()) {
            FileReadSource readSource = new FileReadSource(file);
            XmlParser parser = new XmlParser(readSource);
            document = parser.parse();
        }

        else {

            System.out.println("file does not exist");
        }

        if (document != null)
        showDOMTreeStructure(document.getRootElement());


    }
}
