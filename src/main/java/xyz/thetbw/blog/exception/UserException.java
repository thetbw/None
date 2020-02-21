package xyz.thetbw.blog.exception;

public abstract class UserException  extends RequestException{
    public UserException(){

    }

    public UserException(String msg){
        super(msg);
    }
}
