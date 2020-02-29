package xyz.thetbw.blog.exception;

public class StringToShortException extends RequestException {
    public StringToShortException(String message, Throwable cause) {
        super(message, cause);
    }

    public StringToShortException(Throwable cause) {
        super(cause);
    }

    protected StringToShortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public StringToShortException() {
        super();
    }

    public StringToShortException(String msg) {
        super(msg);
    }
}
