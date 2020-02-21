package xyz.thetbw.blog.exception;

public class StringNotExistException extends RequestException{

    public StringNotExistException() {
        super();
    }

    public StringNotExistException(String message) {
        super(message);
    }

    public StringNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public StringNotExistException(Throwable cause) {
        super(cause);
    }

    protected StringNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
