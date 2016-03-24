package org.max.spr.core.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * Created by Maksym_Bondarenko on 1/25/2016.
 */
@Component
public class SimpleResource {

    private static Logger log = LoggerFactory.getLogger(SimpleResource.class);
    @Value(value = "${something.value}")
    private String appValue;
    @Autowired
    private Environment env;

    public String  getSomething(){
        log.debug("ret something");
        String envProperty = env.getProperty("something.value");
        log.debug("value from Env:{}",envProperty);
        return "Some name " + appValue;
    }


}
