import dao.DAOFactory;
import dao.XMLDaoImplements;
import service.ParserImplements;
import service.ServiceFactory;

public class Main {
    public static void main(String[] args) {

        DAOFactory daoFactory = DAOFactory.getInstance();
        XMLDaoImplements xmlDaoImplements = (XMLDaoImplements) daoFactory.getXmlDao();
        xmlDaoImplements.setFileAddress("/example.xml");
        xmlDaoImplements.readFile();

//        Parser parser = new Parser();
//
//        parser.parseFile(Main.class.getResource("example.xml"));
    }
}
