package service.exceptions;

public class ServiceException extends Exception {
    private String message;

    public ServiceException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
