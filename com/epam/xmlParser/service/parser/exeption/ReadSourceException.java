package com.epam.xmlParser.service.parser.exeption;


import java.io.IOException;

public class ReadSourceException extends Exception {

    private Exception exception;

    public ReadSourceException(IOException e) {

        this.exception = e;
    }

    @Override
    public void printStackTrace() {

        exception.printStackTrace();
    }
}
