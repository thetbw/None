package xyz.thetbw.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFountException extends RequestException {
    public CategoryNotFountException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryNotFountException(Throwable cause) {
        super(cause);
    }

    protected CategoryNotFountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CategoryNotFountException() {
        super();
    }

    public CategoryNotFountException(String msg) {
        super(msg);
    }
}
