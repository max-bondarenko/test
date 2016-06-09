package org.max.spring.data.config.query.builder.filter;

import com.google.common.base.Joiner;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maksym_Bondarenko on 4/13/2016.
 */

public abstract class LogicalFilter extends Filter {

    private List<Filter> fields = new LinkedList<>();

    public LogicalFilter(String type) {
        this.type = type;
    }

    public void add(Filter filter) {
        if (filter.isValid()) {
            fields.add(filter);
        }
    }

    public List<Filter> getFields() {
        return fields;
    }

    @Override
    public boolean isValid() {
        return !fields.isEmpty();
    }

    @Override
    public String toString() {
        return "{ \"type\": \"" + type + "\"," +
                "\"fields\": [" + Joiner.on(',').join(fields) +
                "]}";
    }
}
