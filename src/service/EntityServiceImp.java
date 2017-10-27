package service;

import dao.DAOFactory;
import dao.XMLDao;
import dao.exceptions.DAOException;
import entity.XMLNode;
import service.exceptions.ServiceException;

public class EntityServiceImp implements EntityService {

    private XMLDao xmlDao;

    public EntityServiceImp() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        xmlDao = daoFactory.getXmlDao();
    }

    @Override
    public void setFileAddress(String fileAddress) {
        xmlDao.setFileAddress(fileAddress);
    }

    @Override
    public void parseFile() throws ServiceException {
        try {
            xmlDao.readFile();
        } catch (DAOException e) {
            throw new ServiceException("Parse error!");
        }
    }

    @Override
    public XMLNode valueOf() {
        return xmlDao.valueOf();
    }
}
