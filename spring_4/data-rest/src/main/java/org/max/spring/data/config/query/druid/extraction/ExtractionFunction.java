package org.max.spring.data.config.query.druid.extraction;

import org.max.spring.data.config.query.druid.Base;
import org.max.spring.data.config.query.druid.HasParent;

import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/3/2016.
 */
public class ExtractionFunction<T extends HasParent, Parent extends T> extends Base<Parent> {
    public ExtractionFunction(Map<String, Object> b, Parent p) {
        super(b, p);
    }

    public TimeFormat<ExtractionFunction<T, ?>, ?> timeFormat() {
        return new TimeFormat<>(b, this);
    }

    ;

    public Time<ExtractionFunction<T, ?>, ?> time() {
        return new Time<>(b, this);
    }

    ;

    public Simple<ExtractionFunction<T, ?>, ?> regex() {
        b.put("type", "regex");
        Simple<ExtractionFunction<T, ?>, ExtractionFunction<T, Parent>> ret = new Simple<>(b, this);
        return ret;
    }

    public Simple<ExtractionFunction<T, ?>, ?> partial() {
        b.put("type", "partial");
        Simple<ExtractionFunction<T, ?>, ExtractionFunction<T, Parent>> ret = new Simple<>(b, this);
        return ret;
    }

}
