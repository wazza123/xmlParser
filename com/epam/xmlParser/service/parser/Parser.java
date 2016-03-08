package com.epam.xmlParser.service.parser;


import com.epam.xmlParser.entity.Document;
import com.epam.xmlParser.service.parser.exeption.ReadSourceException;

public interface Parser {

    public Document parse() throws ReadSourceException;

}
