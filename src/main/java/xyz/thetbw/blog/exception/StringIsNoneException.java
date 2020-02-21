package xyz.thetbw.blog.exception;

public class StringIsNoneException extends RequestException{
    public StringIsNoneException() {
        super();
    }

    public StringIsNoneException(String message) {
        super(message);
    }

    public StringIsNoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public StringIsNoneException(Throwable cause) {
        super(cause);
    }

    protected StringIsNoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
