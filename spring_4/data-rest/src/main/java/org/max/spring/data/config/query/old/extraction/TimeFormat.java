package org.max.spring.data.config.query.old.extraction;


import org.joda.time.format.DateTimeFormatter;
import org.max.spring.data.config.query.old.Base;
import org.max.spring.data.config.query.old.HasParent;

import java.util.Locale;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/3/2016.
 */
public class TimeFormat<T extends HasParent, Parent extends T> extends Base<Parent> {

    public TimeFormat(Map<String, Object> b, Parent p) {
        super(b, p);
    }

    //JodaTime
    void format(DateTimeFormatter format) {
    }

    ;

    void format(String pattern) {
    }

    ;

    void timeZone(String timezone) {
    }

    ;
    ;

    void locale(String locale) {
    }

    ;

    void locale(Locale l) {
    }

    ;
}
