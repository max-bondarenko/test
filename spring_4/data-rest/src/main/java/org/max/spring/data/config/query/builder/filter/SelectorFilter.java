package org.max.spring.data.config.query.builder.filter;

/**
 * Created by Maksym_Bondarenko on 4/11/2016.
 */
public class SelectorFilter extends Filter {

    protected String dimension;
    protected String value;

    public SelectorFilter() {
        type = "selector";
    }

    public SelectorFilter(String dimension, String value) {
        this();
        setDimension(dimension);
        setValue(value);
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        valid = dimension != null && dimension.length() > 0;
        this.dimension = dimension;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        valid = value != null && dimension.length() > 0;
        this.value = value;
    }

    @Override
    public String toString() {
        return "{\"type\": \"" + type + "\"," +
                "\"dimension\": \"" + dimension + '\"' +
                ",\"value\": \"" + value + '\"' + '}';
    }
}
