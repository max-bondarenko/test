package org.max.spring.data.config.query.druid;

import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/1/2016.
 */
public class Filter<T extends HasParent, Parent extends T> extends Base<Parent> {

    public Filter(Map<String, Object> b, Parent p) {
        super(b, p);
    }

    @Override
    public Parent end() {
        return p;
    }

    @Override
    public String build() {
        return p.build();
    }

    public SelectorFilter<Filter<T, ?>, ?> selector() {
        return new SelectorFilter<>(b, this);
    }

    public RegexFilter<Filter<T, ?>, ?> regex() {
        return new RegexFilter<>(b, this);
    }

    public JavascriptFilter<Filter<T, ?>, ?> javascript() {
        return new JavascriptFilter<>(b, this);
    }

    public SearchFilter<Filter<T, ?>, ?> search() {
        return new SearchFilter<>(b, this);
    }

    public LogicalFilter<Filter<T, ?>, ?> and() {
        b.put("type", "and");
        return new LogicalFilter<>(b, this);
    }

    public LogicalFilter<Filter<T, ?>, ?> or() {
        b.put("type", "or");
        return new LogicalFilter<>(b, this);
    }

    public LogicalFilter<Filter<T, ?>, ?> not() {
        b.put("type", "not");
        return new LogicalFilter<>(b, this);
    }


}
