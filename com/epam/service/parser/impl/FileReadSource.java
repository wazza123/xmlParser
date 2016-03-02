package com.epam.service.parser.impl;


import com.epam.service.parser.ReadSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReadSource implements ReadSource {

    private File sourceFile;

    public FileReadSource(File sourceFile) {

        this.sourceFile = sourceFile;
    }

    @Override
    public String read() {

        StringBuilder charsFromFile = new StringBuilder();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(sourceFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int c = 0;

        try {
            while ( ( c = fileInputStream.read() ) != -1) {

                charsFromFile.append((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return charsFromFile.toString();

    }
}
