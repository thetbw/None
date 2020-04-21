package xyz.thetbw.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticleNotFountException extends ArticleException {
    public ArticleNotFountException(){
        super("没有找到文章");
    }

    public ArticleNotFountException(String message) {
        super(message);
    }
}
