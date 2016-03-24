package org.max.boot.ctrl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* Created by : Maksym_Bondarenko
* Created at : 21-01-2016
*/
@Controller
@EnableAutoConfiguration
public class SimpleController {

    @RequestMapping("/")
    @ResponseBody
    public String hetHello(){
        return "Helo";
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleController.class,args);
    }

}
