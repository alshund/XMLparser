package service;

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();
    private final EntityService parser = new EntityServiceImp();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public EntityService getParser() {
        return parser;
    }
}
