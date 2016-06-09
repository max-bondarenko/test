package org.max.boot.ctrl;

import org.max.boot.model.ExampleDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
* Created by : Maksym_Bondarenko
* Created at : 21-01-2016
*/
@RestController
public class SimpleController {

    @RequestMapping("/")
    @ResponseBody
    public String hetHello(){
        return "Helo";
    }

    @RequestMapping("/example")
    @ResponseBody
    public ExampleDto retExampleDto(){
        return new ExampleDto("val",new Date(),11231212L);
    }
}
