package main;

import service.ServiceFactory;
import service.EntityService;
import service.exceptions.ServiceException;

public class Main {
    public static void main(String[] args) {

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        EntityService parser = serviceFactory.getParser();
        parser.setFileAddress("test.xml");
        try {
            parser.parseFile();
            PrintXML.printTree(parser.valueOf());
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }
}
