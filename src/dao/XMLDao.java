package dao;

import etity.Entity;
import etity.XMLElement;

public interface XMLDao {
    void setFileAddress(String fileAddress);
    void readFile();
    XMLElement valueOf();
}
