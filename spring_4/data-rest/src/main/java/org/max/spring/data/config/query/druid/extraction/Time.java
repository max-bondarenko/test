package org.max.spring.data.config.query.druid.extraction;

import org.max.spring.data.config.query.druid.Base;
import org.max.spring.data.config.query.druid.HasParent;

import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/3/2016.
 */
public class Time<T extends HasParent, Parent extends T> extends Base<Parent> {

    Time(Map<String, Object> b, Parent p) {
        super(b, p);
    }

    void timeFormat(DateTimeFormatter format) {

    }

    ;

    void timeFormat(String pattern) {

    }

    ;

    void resultFormat(DateTimeFormatter format) {

    }

    ;

    void resultFormat(String pattern) {

    }

    ;

}
