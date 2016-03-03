package org.max.spring.data.config.query.druid;

import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/3/2016.
 */
public class RegexFilter<T extends HasParent, Parent extends T> extends Base<Parent> {

    RegexFilter(Map<String, Object> b, Parent p) {
        super(b, p);
        b.put("type", "regex");
    }

    public RegexFilter<T, ?> dimension(String dim) {
        b.put("dimension", dim);
        return this;
    }

    public RegexFilter<T, ?> pattern(String val) {
        b.put("pattern", val);
        return this;
    }


}
