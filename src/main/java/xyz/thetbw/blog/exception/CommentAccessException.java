package xyz.thetbw.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CommentAccessException extends CommentException {


    public CommentAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentAccessException(Throwable cause) {
        super(cause);
    }

    protected CommentAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CommentAccessException() {
        super();
    }

    public CommentAccessException(String msg) {
        super(msg);
    }
}
