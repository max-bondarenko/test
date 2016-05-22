package org.max.spring.data.config.query.builder.dimension;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Maksym_Bondarenko on 4/21/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultDimension {
    String type = "default";
    String dimension;
    String outputName;

    DefaultDimension(String dimension, String outputName) {
        this.dimension = dimension;
        this.outputName = outputName;
    }

    public String getType() {
        return type;
    }

    public String getDimension() {
        return dimension;
    }

    public String getOutputName() {
        return outputName;
    }
}
