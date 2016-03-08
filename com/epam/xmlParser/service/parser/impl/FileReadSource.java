package com.epam.xmlParser.service.parser.impl;


import com.epam.xmlParser.service.parser.ReadSource;
import com.epam.xmlParser.service.parser.exeption.ReadSourceException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReadSource implements ReadSource {

    private File sourceFile;
    private FileInputStream fileInputStream;
    private int nextChar = 0;

    public FileReadSource(File sourceFile) throws ReadSourceException {

        this.sourceFile = sourceFile;
    }

    private void openInputStream() {
    }

    @Override
    public String nextString() throws ReadSourceException {

        StringBuilder stringBuilder = new StringBuilder();

        if (nextChar == '<') {

            stringBuilder.append((char) nextChar);
        }

        if (fileInputStream == null) {

            try {

                fileInputStream = new FileInputStream(sourceFile);
            } catch (IOException e) {

                System.err.println("file " + sourceFile.getAbsolutePath() + " does not exist");
                throw new ReadSourceException(e);

            }
        }

        try {

            while ((nextChar = fileInputStream.read()) != -1) {

                if (nextChar == '>') {

                    stringBuilder.append((char) nextChar);
                    break;
                } else if (nextChar == '<') {

                    break;
                }

                stringBuilder.append((char) nextChar);
            }

        } catch (IOException e) {

            e.printStackTrace();
            throw new ReadSourceException(e);
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
