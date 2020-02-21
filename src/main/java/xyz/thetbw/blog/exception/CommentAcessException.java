package xyz.thetbw.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ACCEPTED)
public class CommentAcessException extends CommentException {


    public CommentAcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentAcessException(Throwable cause) {
        super(cause);
    }

    protected CommentAcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CommentAcessException() {
        super();
    }

    public CommentAcessException(String msg) {
        super(msg);
    }
}
