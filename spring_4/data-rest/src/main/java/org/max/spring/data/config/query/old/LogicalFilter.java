package org.max.spring.data.config.query.old;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/3/2016.
 */
public class LogicalFilter<T extends HasParent, Parent extends T> extends Base<Parent> {

    private static final String FIELDS = "fields";

    protected Map<String, Object> sub;

    public LogicalFilter(Map<String, Object> b, Parent p) {
        super(b, p);
    }

    public Filter<LogicalFilter<T, ?>, ?> add() {
        if (sub != null) {
            getFields().add(new HashMap<String, Object>(sub));
        }
        sub = new HashMap<>();
        return new Filter<>(sub, this);
    }

    ;

    @Override
    public Parent end() {
        if (!sub.isEmpty()) {
            getFields().add(sub);
        }
        return p;
    }

    private Collection<Map<String, Object>> getFields() {
        Collection<Map<String, Object>> filters = (Collection<Map<String, Object>>) b.get(FIELDS);
        if (filters == null || filters.isEmpty()) {
            filters = new LinkedList<>();
            b.put(FIELDS, filters);
        }
        return filters;
    }


}
