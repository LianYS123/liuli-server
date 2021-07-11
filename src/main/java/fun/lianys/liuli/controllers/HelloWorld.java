package fun.lianys.liuli.controllers;

import fun.lianys.liuli.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class HelloWorld {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    @ResponseBody
    public String hello(){
        userService.printUser(1);
        return "test";
    }
}
