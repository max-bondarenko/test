package com.max.mvc;

import com.max.JavaDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * Created by add on 10.08.2014.
 */
@Controller
public class SimpleController {

    @RequestMapping(value = "/get", produces = "application/json")
    @ResponseBody
    public JavaDto getData() {
        JavaDto ret = new JavaDto();

        ret.name = "Test 1 name";
        ret.ch = new ArrayList<>(3);
        ret.ch.add(new JavaDto("ch 1", 10L, null));
        ret.ch.add(new JavaDto("ch 2", 11L, null));
        ret.id = 1L;
        return ret;
    }
}
