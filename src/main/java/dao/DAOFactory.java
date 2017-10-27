package dao;

public class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();
    private final XMLDao xmlDao = new XMLDaoImpl();

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        return instance;
    }

    public XMLDao getXmlDao() {
        return xmlDao;
    }
}
