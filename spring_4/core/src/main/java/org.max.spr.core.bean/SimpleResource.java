package org.max.spr.core.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Created by Maksym_Bondarenko on 1/25/2016.
 */
@Component
public class SimpleResource {

    private static Logger log = LoggerFactory.getLogger("SImple");

    @Value(value = "${something.value}")
    private String appValue;

    public String  getSomething(){
        log.debug("ret something");
        return "Some name " + appValue;
    }


}
