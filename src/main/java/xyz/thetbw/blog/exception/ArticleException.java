package xyz.thetbw.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public  class ArticleException extends RequestException{

    public ArticleException(){
        super();
    }
    public ArticleException(String message) {
        super(message);
    }
}
