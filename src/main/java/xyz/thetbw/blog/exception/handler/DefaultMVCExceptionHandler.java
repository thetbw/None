package xyz.thetbw.blog.exception.handler;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import xyz.thetbw.blog.data.model.DefaultMsgModel;
import xyz.thetbw.blog.exception.RequestException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class DefaultMVCExceptionHandler {

//    @ExceptionHandler(value = ResourceNotFoundException.class)
//    @ResponseBody
//    public Object defaultHandler(Exception e) {
//        e.printStackTrace();
//        DefaultMsgModel defaultMsgModel = new DefaultMsgModel();
//        defaultMsgModel.setMassage(e.getMessage());
//        defaultMsgModel.setSuccessful(false);
//        return defaultMsgModel;
//    }
}
