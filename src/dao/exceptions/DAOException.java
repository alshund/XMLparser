package dao.exceptions;

public class DAOException extends Exception {
    private String message;

    public DAOException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
