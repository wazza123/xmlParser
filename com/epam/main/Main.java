package com.epam.main;

import com.epam.entity.Document;
import com.epam.service.parser.impl.FileReadSource;
import com.epam.service.parser.impl.XmlParser;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        File file = new File("C:\\Users\\Администратор\\Desktop\\pom1.xml");
        FileReadSource readSource = new FileReadSource(file);
        XmlParser parser = new XmlParser(readSource);
        Document document = parser.parse();

        System.out.println(document.getRootElement().getChildElements().get(5).getChildElements().get(0).getName());


    }
}
