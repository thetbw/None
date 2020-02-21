package xyz.thetbw.blog.exception;

public class UserAccessErrorException extends UserException {
    public  UserAccessErrorException(){
        super("用户密码错误");
    }
}
