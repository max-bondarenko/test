package org.max.spring.data.config.query.builder.postaggregations;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Maksym_Bondarenko on 4/21/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldAccess extends PostAggregation {
    private String fieldName;

    public FieldAccess(String name, String fieldName) {
        super(Type.fieldAccess, name);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
