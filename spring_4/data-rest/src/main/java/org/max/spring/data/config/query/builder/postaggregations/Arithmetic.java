package org.max.spring.data.config.query.builder.postaggregations;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maksym_Bondarenko on 4/21/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Arithmetic extends PostAggregation {
    private Fn fn;
    private List<PostAggregation> fields;

    public Arithmetic(Fn function, String name, PostAggregation... fields) {
        super(Type.arithmetic, name);
        this.fn = function;

        this.fields = new LinkedList<>();
        this.fields.addAll(Arrays.asList(fields));
    }

    public String getFn() {
        return fn.value;
    }

    public List<PostAggregation> getFields() {
        return fields;
    }


    public static enum Fn {
        add("+"),
        sub("-"),
        mul("*"),
        div("/"),
        quotient("quotient");
        String value;

        Fn(String value) {
            this.value = value;
        }
    }
}
