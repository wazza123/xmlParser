package com.company.xmlParser.service.parser;


import com.company.xmlParser.service.parser.exception.ReadSourceException;

public interface ReadSource {

    public String nextString() throws ReadSourceException;
}
