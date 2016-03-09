package com.epam.xmlParser.main;

import com.epam.xmlParser.entity.Attribute;
import com.epam.xmlParser.entity.Document;
import com.epam.xmlParser.entity.Element;
import com.epam.xmlParser.service.parser.exeption.ReadSourceException;
import com.epam.xmlParser.service.parser.impl.FileReadSource;
import com.epam.xmlParser.service.parser.impl.XmlParser;

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

        File file = new File("C:\\Users\\Администратор\\Desktop\\ddd.xml");
        Document document;
        FileReadSource readSource;
        readSource = new FileReadSource(file);
        XmlParser parser = new XmlParser(readSource);

        try {

            document = parser.parse();
        }
        catch (ReadSourceException e) {

            e.printStackTrace();
            return;
        }

        if (document != null) {

            showDOMTreeStructure(document.getRootElement());
        }
    }
}
