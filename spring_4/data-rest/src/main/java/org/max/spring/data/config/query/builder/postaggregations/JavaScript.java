package org.max.spring.data.config.query.builder.postaggregations;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maksym_Bondarenko on 4/29/2016.
 */
public class JavaScript extends PostAggregation {
    private List<String> fieldNames = new LinkedList<>();
    private String function;

    public JavaScript(String name, String function, String... fieldNames) {
        super(Type.javascript, name);
        this.fieldNames.addAll(Arrays.asList(fieldNames));
        setFunction(function);
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        function = function.trim();
        boolean valid = function.startsWith("function");
        valid &= function.contains("{");
        valid &= function.endsWith("}");
        if (!valid) {
            throw new IllegalArgumentException("Wrong function declaration");
        }
        this.function = function;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }
}
