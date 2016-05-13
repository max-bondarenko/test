package org.max.spring.data.config.query.builder.filter;

/**
 * Created by Maksym_Bondarenko on 4/11/2016.
 */
public class JavascriptFilter extends Filter {

    protected String dimension;
    protected String function;

    public JavascriptFilter() {
        type = "javascript";
    }

    public JavascriptFilter(String dimension, String value) {
        this();
        setDimension(dimension);
        setFunction(value);
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        valid = dimension != null && dimension.length() > 0;
        this.dimension = dimension;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        if (function != null) {
            function = function.trim();
            valid &= function.startsWith("function");
            valid &= function.contains("{");
            valid &= function.endsWith("}");
            valid &= dimension.length() > 0;
        }
        this.function = function;
    }

    @Override
    public String toString() {
        return "{\"type\": \"" + type + "\"," +
                "\"dimension\": \"" + dimension + '\"' +
                ",\"function\": \"" + function + '\"' + '}';
    }
}
