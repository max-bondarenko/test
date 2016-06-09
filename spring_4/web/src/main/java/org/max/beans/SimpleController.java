package org.max.beans;


import org.max.spr.core.bean.SimpleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Maksym_Bondarenko on 1/25/2016.
 */
@RestController
public class SimpleController {

    @Autowired
    private SimpleResource resource;

    @RequestMapping(method = RequestMethod.GET,path = "get")
    public String get(){
        return resource.getSomething();

    }

    @RequestMapping(method = RequestMethod.GET,path = "getsecured")
    public String getSecured(){
        return "secured";

    }
}
