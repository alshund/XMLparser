import service.ServiceFactory;
import service.Parser;
import service.exceptions.ServiceException;

public class Main {
    public static void main(String[] args) {

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        Parser parser = serviceFactory.getParser();
        parser.setFileAddress("/test.xml");
        try {
            parser.parseFile();
            System.out.println(parser.valueOf());
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }
}
