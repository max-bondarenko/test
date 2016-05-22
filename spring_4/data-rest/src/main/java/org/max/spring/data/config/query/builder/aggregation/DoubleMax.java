package org.max.spring.data.config.query.builder.aggregation;

/**
 * Created by Maksym_Bondarenko on 4/20/2016.
 */
public class DoubleMax extends SimpleAggregation {

    public DoubleMax(String name) {
        super("doubleMax", name);
    }

    public DoubleMax(String name, String fieldName) {
        super("doubleMax", name, fieldName);
    }
}
