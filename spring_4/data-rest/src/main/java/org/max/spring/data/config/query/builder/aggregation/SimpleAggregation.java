package org.max.spring.data.config.query.builder.aggregation;

/**
 * Created by Maksym_Bondarenko on 4/20/2016.
 */
abstract class SimpleAggregation extends Aggregation {
    private String fieldName;

    protected SimpleAggregation(String type, String name) {
        this.type = type;
        this.name = name;
    }

    protected SimpleAggregation(String type, String name, String fieldName) {
        this.fieldName = fieldName;
        this.type = type;
        this.name = name;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


}
