package org.max.spring.data.config.query.builder.aggregation;

/**
 * Created by Maksym_Bondarenko on 4/20/2016.
 */
public class DoubleMin extends SimpleAggregation {

    protected DoubleMin(String name) {
        super("doubleMin", name);
    }
}
