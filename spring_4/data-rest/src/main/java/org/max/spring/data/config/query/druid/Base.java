package org.max.spring.data.config.query.druid;

import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/3/2016.
 */
public abstract class Base<Parent extends HasParent> implements HasParent<Parent> {

    protected Map<String, Object> b;
    protected Parent p;

    public Base(Map<String, Object> b, Parent p) {
        this.b = b;
        this.p = p;
    }

    @Override
    public Parent end() {
        return p;
    }

    @Override
    public String build() {
        return p.build();
    }
}
