package org.max.spring.data.config.query.builder.aggregation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maksym_Bondarenko on 4/20/2016.
 */

public class Cardinality extends Aggregation {
    private List<String> fieldNames = new LinkedList<>();
    private boolean byRow;

    public Cardinality(String name) {
        type = "cardinality";
        this.name = name;
    }

    public Cardinality(String name, String... args) {
        this(name);
        fieldNames.addAll(Arrays.asList(args));
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public boolean isByRow() {
        return byRow;
    }

    public void setByRow(boolean byRow) {
        this.byRow = byRow;
    }
}
