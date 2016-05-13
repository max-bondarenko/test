package org.max.spring.data.config.query.builder.aggregation;

/**
 * Created by Maksym_Bondarenko on 4/20/2016.
 */
public class DoubleSum extends SimpleAggregation {

    public DoubleSum(String name) {
        super("doubleSum", name);
    }

    public DoubleSum(String name, String fieldName) {
        super("doubleSum", name, fieldName);
    }
}
