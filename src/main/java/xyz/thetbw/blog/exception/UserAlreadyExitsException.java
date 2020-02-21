package xyz.thetbw.blog.exception;

public class UserAlreadyExitsException extends RequestException{
    public UserAlreadyExitsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExitsException(Throwable cause) {
        super(cause);
    }

    protected UserAlreadyExitsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UserAlreadyExitsException() {
        super();
    }

    public UserAlreadyExitsException(String msg) {
        super(msg);
    }
}
