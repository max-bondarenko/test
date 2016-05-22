package org.max.spring.data.config.query.old;


import org.max.spring.data.config.query.old.extraction.ExtractionFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/3/2016.
 */
public class ExtractionFilter<T extends HasParent, Parent extends T> extends Base<Parent> {

    ExtractionFilter(Map<String, Object> b, Parent p) {
        super(b, p);
        b.put("type", "extraction");
    }

    public ExtractionFilter<T, ?> dimension(String dim) {
        b.put("dimension", dim);
        return this;
    }

    public ExtractionFilter<T, ?> value(String val) {
        b.put("value", val);
        return this;
    }

    public ExtractionFunction<ExtractionFilter<T, ?>, ?> extractionFn() {
        Map<String, Object> map = new HashMap<>();
        b.put("extractionFn", map);
        return new ExtractionFunction<>(map, this);
    }

}
