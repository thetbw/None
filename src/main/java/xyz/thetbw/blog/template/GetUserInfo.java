package xyz.thetbw.blog.template;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.dao.UserDao;
import xyz.thetbw.blog.data.entity.User;
import xyz.thetbw.blog.service.UserService;
import xyz.thetbw.blog.util.ReflectUtils;

import java.util.List;

@Component
public class GetUserInfo  implements TemplateMethodModelEx {
    private static final Logger LOG = LoggerFactory.getLogger(GetUserInfo.class);
    private static final String ADMIN = "admin";
    private static final String LOGIN="login";


    @Autowired
    UserService userService;

    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list.size()==0) return null;
        Object o = list.get(0);
        if (o instanceof TemplateScalarModel){
            return getUserFromString(((TemplateScalarModel) o).getAsString());
        }else if (o instanceof TemplateNumberModel){
            return getUserFromId(((TemplateNumberModel) o).getAsNumber().intValue());
        }else {
            LOG.error("错误的模板参数："+o.getClass().getName()+"\t"+o.toString());
            throw new RuntimeException("错误的参数");
        }
    }


    private User getUserFromString(String s){
        switch (s){
            case ADMIN:
                User user = userService.getAdminUser();
                if (user==null){
                    user=new User();
                    user.setUser_name("NUll");
                }
                return user;
            case LOGIN:
                User u = userService.getUser();
                return u;
        }
        return null;
    }

    private User getUserFromId(int id){
        User user = userService.getUser(id);
        return user;
    }
}
