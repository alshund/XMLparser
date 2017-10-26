package dao;

import etity.Entity;

public interface XMLDao {
    void setFileAddress(String fileAddress);
    void readFile();
    Entity valueOf();
}
