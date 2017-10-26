package service;

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();
    private final Parser parser = new ParserImplements();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public Parser getParser() {
        return parser;
    }
}
