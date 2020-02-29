package xyz.thetbw.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestParameterException extends RequestException{
    public RequestParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestParameterException(Throwable cause) {
        super(cause);
    }

    protected RequestParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RequestParameterException() {
        super();
    }

    public RequestParameterException(String msg) {
        super(msg);
    }
}
