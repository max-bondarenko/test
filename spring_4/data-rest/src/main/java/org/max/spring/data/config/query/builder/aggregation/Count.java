package org.max.spring.data.config.query.builder.aggregation;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Maksym_Bondarenko on 4/20/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Count extends SimpleAggregation {

    public Count(String name) {
        super("count", name);
    }

    public Count(String name, String fieldName) {
        super("count", name, fieldName);
    }

}
