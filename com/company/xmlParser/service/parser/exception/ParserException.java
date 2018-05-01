package com.company.xmlParser.service.parser.exception;


public class ParserException extends Exception {

    private Exception exception;

    public ParserException(ReadSourceException e) {

        exception = e;
    }

    @Override
    public void printStackTrace() {

        super.printStackTrace();
        exception.printStackTrace();
    }
}
