package org.max.spring.data.config.query.druid.extraction;

import org.max.spring.data.config.query.druid.Base;
import org.max.spring.data.config.query.druid.HasParent;

import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/3/2016.
 */
public class Simple<T extends HasParent, Parent extends T> extends Base<Parent> {
    public Simple(Map<String, Object> b, Parent p) {
        super(b, p);
    }

    public Simple<T, ?> expr(String expr) {
        b.put("expr", expr);
        return this;
    }

    ;
}
