package dao;

import dao.exceptions.DAOException;
import entity.XMLNode;

public interface XMLDao {
    void setFileAddress(String fileAddress);
    void readFile() throws DAOException;
    XMLNode valueOf();
}
