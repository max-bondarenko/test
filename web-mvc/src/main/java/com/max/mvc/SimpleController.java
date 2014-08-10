package com.max.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by add on 10.08.2014.
 */
@Controller
public class SimpleController {

    @RequestMapping(value = "/get")
    @ResponseBody
    public String getData() {
        String s = "<h1>Responce</h1>";
        return s;
    }
}
