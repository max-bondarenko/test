package org.max.spring.data.config.query.druid;

import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/3/2016.
 */
public class SelectorFilter<T extends HasParent, Parent extends T> extends Base<Parent> {

    SelectorFilter(Map<String, Object> b, Parent p) {
        super(b, p);
        b.put("type", "selector");
    }

    public SelectorFilter<T, ?> dimension(String dim) {
        b.put("dimension", dim);
        return this;
    }

    public SelectorFilter<T, ?> value(String val) {
        b.put("value", val);
        return this;
    }

}
