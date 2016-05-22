package org.max.spring.data.config.query.old;

import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/3/2016.
 */
public class JavascriptFilter<T extends HasParent, Parent extends T> extends Base<Parent> {
    protected Map<String, Object> b;
    Parent p;


    JavascriptFilter(Map<String, Object> b, Parent p) {
        super(b, p);
        b.put("type", "javascript");
    }

    public JavascriptFilter<T, ?> dimension(String dim) {
        b.put("dimension", dim);
        return this;
    }

    public JavascriptFilter<T, ?> function(String val) {
        b.put("function", val);
        return this;
    }
}
