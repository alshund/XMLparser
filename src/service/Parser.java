package service;

import etity.XMLNode;

public interface Parser {
    void setFileAddress(String fileAddress);
    void parseFile() throws SecurityException;
    XMLNode valueOf();

}
