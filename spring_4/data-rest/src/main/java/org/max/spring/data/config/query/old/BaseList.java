package org.max.spring.data.config.query.old;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/3/2016.
 */
public abstract class BaseList<Parent extends HasParent> extends Base<Parent> {

    protected Map<String, Object> sub;

    private String ownName;


    public BaseList(Map<String, Object> b, Parent p, String fieldOwnName) {
        super(b, p);
        ownName = fieldOwnName;
    }

    @Override
    public Parent end() {
        if (!sub.isEmpty()) {
            getFields().add(sub);
        }
        return p;
    }

    protected void addInternal() {
        if (sub != null) {
            getFields().add(new HashMap<>(sub));
        }
        sub = new HashMap<>();
    }

    private Collection<Map<String, Object>> getFields() {
        Collection<Map<String, Object>> filters = (Collection<Map<String, Object>>) b.get(ownName);
        if (filters == null || filters.isEmpty()) {
            filters = new LinkedList<>();
            b.put(ownName, filters);
        }
        return filters;
    }
}
