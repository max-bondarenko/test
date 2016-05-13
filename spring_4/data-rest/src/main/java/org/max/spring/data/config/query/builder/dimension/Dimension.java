package org.max.spring.data.config.query.builder.dimension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Maksym_Bondarenko on 4/21/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dimension {
    String value;
    DefaultDimension internal;

    public Dimension(String value) {
        this.value = value;
    }

    public Dimension(String dimension, String outputName) {
        internal = new DefaultDimension(dimension, outputName);
    }


    @JsonValue
    Object getValue() {
        if (internal != null) {
            return internal;
        }
        return value;
    }

}
