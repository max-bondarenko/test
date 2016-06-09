package org.max.spring.data.config.query.builder.postaggregations;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Maksym_Bondarenko on 4/21/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class PostAggregation {
    protected String name;
    private Type type;

    protected PostAggregation(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    protected enum Type {
        arithmetic,
        fieldAccess,
        constant,
        javascript
    }
}
