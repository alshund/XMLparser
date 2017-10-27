package service;

import entity.XMLNode;
import service.exceptions.ServiceException;

public interface EntityService {
    void setFileAddress(String fileAddress);
    void parseFile() throws SecurityException, ServiceException;
    XMLNode valueOf();
}
