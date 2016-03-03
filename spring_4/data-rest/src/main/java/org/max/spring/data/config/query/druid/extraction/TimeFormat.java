package org.max.spring.data.config.query.druid.extraction;

import org.max.spring.data.config.query.druid.Base;
import org.max.spring.data.config.query.druid.HasParent;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    void timeZone(ZoneId timezone) {
    }

    ;

    void timeZone(String timezoneName) {
    }

    ;

    void locale(String locale) {
    }

    ;

    void locale(Locale l) {
    }

    ;
}
