package com.company.xmlParser.service.parser.exception;


import java.io.IOException;

public class ReadSourceException extends Exception {

    private Exception exception;

    public ReadSourceException(IOException e) {

        this.exception = e;
    }

    @Override
    public void printStackTrace() {

        super.printStackTrace();
        exception.printStackTrace();
    }
}
