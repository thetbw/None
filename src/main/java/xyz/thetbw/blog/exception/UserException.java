package xyz.thetbw.blog.exception;


public class UserException  extends RequestException{
    public UserException(){

    }

    public UserException(String msg){
        super(msg);
    }
}
