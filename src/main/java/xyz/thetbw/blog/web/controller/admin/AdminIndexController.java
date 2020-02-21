package xyz.thetbw.blog.web.controller.admin;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminIndexController {

    @GetMapping("/admin/**")
    public String index(){
        return "index";
    }
}
