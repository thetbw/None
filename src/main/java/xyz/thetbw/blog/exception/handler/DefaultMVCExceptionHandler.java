package xyz.thetbw.blog.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import xyz.thetbw.blog.data.model.DefaultMsgModel;
import xyz.thetbw.blog.exception.RequestException;
import xyz.thetbw.blog.web.ViewErrorResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class DefaultMVCExceptionHandler {
    private static Logger LOG = LoggerFactory.getLogger(DefaultMVCExceptionHandler.class);

//    @ExceptionHandler(value = Exception.class)
    public void defaultHandler(Exception e) {
        if (!LOG.isDebugEnabled()) return;
        LOG.debug("错误信息："+e.getMessage());
        e.printStackTrace();
    }
}
