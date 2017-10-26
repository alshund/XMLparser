package service;

import dao.DAOFactory;
import dao.XMLDao;
import dao.exceptions.DAOException;
import etity.XMLNode;

public class ParserImplements implements Parser {

    private XMLDao xmlDao;

    public ParserImplements() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        xmlDao = daoFactory.getXmlDao();
    }

    @Override
    public void setFileAddress(String fileAddress) {
        xmlDao.setFileAddress(fileAddress);
    }

    @Override
    public void parseFile() throws SecurityException {
        try {
            xmlDao.readFile();
        } catch (DAOException e) {
            throw new SecurityException("ServiceException");
        }
    }


    @Override
    public XMLNode valueOf() {
        return xmlDao.valueOf();
    }
}
