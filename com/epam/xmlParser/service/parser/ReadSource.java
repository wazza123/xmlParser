package com.epam.xmlParser.service.parser;


import com.epam.xmlParser.service.parser.exception.ReadSourceException;

public interface ReadSource {

    public String nextString() throws ReadSourceException;
}
