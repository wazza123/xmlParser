package com.epam.xmlParser.service.parser;


import com.epam.xmlParser.entity.Document;
import com.epam.xmlParser.service.exception.ParserException;

public interface Parser {

    public Document parse() throws ParserException;

}
