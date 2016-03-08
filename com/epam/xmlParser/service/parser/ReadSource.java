package com.epam.xmlParser.service.parser;


import com.epam.xmlParser.service.parser.exeption.ReadSourceException;

public interface ReadSource {

    public String nextString() throws ReadSourceException;
}
