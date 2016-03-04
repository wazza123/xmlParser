package com.epam.main;

import com.epam.entity.Attribute;
import com.epam.entity.Document;
import com.epam.entity.Element;
import com.epam.service.parser.impl.FileReadSource;
import com.epam.service.parser.impl.XmlParser;

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

        File file = new File("C:\\Users\\Администратор\\Desktop\\pom.xml");
        FileReadSource readSource = new FileReadSource(file);
        XmlParser parser = new XmlParser(readSource);
        Document document = parser.parse();

        showDOMTreeStructure(document.getRootElement());


    }
}
