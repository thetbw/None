package xyz.thetbw.blog.exception;

public class UserNotFountException extends UserException{
    public UserNotFountException(String msg) {
        super(msg);
    }

    public UserNotFountException(){
        super("用户不存在，请检查输入");
    }
}
