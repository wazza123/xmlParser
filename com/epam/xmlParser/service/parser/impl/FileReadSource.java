package com.epam.xmlParser.service.parser.impl;


import com.epam.xmlParser.service.parser.ReadSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReadSource implements ReadSource {

    private File sourceFile;
    private FileInputStream fileInputStream;
    private boolean isEndOfFile = false;
    private int nextChar = 0;

    public FileReadSource(File sourceFile) {

        this.sourceFile = sourceFile;
    }

    private void openInputStream() {}

    public String nextString() {

        StringBuilder stringBuilder = new StringBuilder();

        if (nextChar == '<') {

            stringBuilder.append((char) nextChar);
        }

        if (fileInputStream == null) {

            try {
                fileInputStream = new FileInputStream(sourceFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
            try {

                while ( (nextChar = fileInputStream.read()) != -1) {

                    if (nextChar == '>') {

                        stringBuilder.append((char) nextChar);
                        break;
                    }

                    else if (nextChar == '<') {

                        break;
                    }

                    stringBuilder.append((char) nextChar);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        return stringBuilder.toString();
    }

    public boolean hasNext() {

        return !(nextChar == -1);
    }

    public void close() {

        try {

            fileInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
