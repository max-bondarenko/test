package org.max.spring.data.config.query.old.aggregations;


import org.max.spring.data.config.query.old.Base;
import org.max.spring.data.config.query.old.HasParent;

import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/4/2016.
 */
public class Simple<T extends HasParent, Parent extends T> extends Base<Parent> {
    Simple(Map<String, Object> b, Parent p, String type) {
        super(b, p);
        b.put("type", type);
    }

    public Simple<T, ?> name(String name) {
        b.put("name", name);
        return this;
    }

    public Simple<T, ?> fieldName(String fName) {
        b.put("fieldName", fName);
        return this;
    }
}
