package com.company.xmlParser.service.parser.impl;


import com.company.xmlParser.service.parser.ReadSource;
import com.company.xmlParser.service.parser.exception.ReadSourceException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReadSource implements ReadSource {

    private File sourceFile;
    private FileInputStream fileInputStream;
    private int nextChar = 0;

    public FileReadSource(File sourceFile) {

        this.sourceFile = sourceFile;
    }

    @Override
    public String nextString() throws ReadSourceException {

        final char OPEN_TAG_BRACKET = '<';
        final char CLOSED_TAG_BRACKET = '>';
        final int EOF = -1;

        StringBuilder readChars = new StringBuilder();

        if (fileInputStream == null) {

            try {

                fileInputStream = new FileInputStream(sourceFile);
            }
            catch (IOException e) {

                throw new ReadSourceException(e);
            }
        }

        if (nextChar == OPEN_TAG_BRACKET) {

            readChars.append((char) nextChar);
        }

        try {

            while ((nextChar = fileInputStream.read()) != EOF) {

                if (nextChar == CLOSED_TAG_BRACKET) {

                    readChars.append((char) nextChar);
                    break;
                }
                else if (nextChar == OPEN_TAG_BRACKET) {

                    break;
                }

                readChars.append((char) nextChar);
            }

        }
        catch (IOException e) {

            throw new ReadSourceException(e);
        }

        return readChars.toString();
    }

    public boolean hasNext() {

        return !(nextChar == -1);
    }

    public void close() {

        try {

            fileInputStream.close();
        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }
}
