package com.company.xmlParser.service.parser;


import com.company.xmlParser.entity.Document;
import com.company.xmlParser.service.parser.exception.ParserException;

public interface Parser {

    public Document parse() throws ParserException;

}
