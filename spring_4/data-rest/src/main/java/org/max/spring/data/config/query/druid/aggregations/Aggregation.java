package org.max.spring.data.config.query.druid.aggregations;

import org.max.spring.data.config.query.druid.Base;
import org.max.spring.data.config.query.druid.BaseList;
import org.max.spring.data.config.query.druid.HasParent;

import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/4/2016.
 */
public class Aggregation<T extends HasParent, Parent extends T> extends Base<Parent> {

    public Aggregation(Map<String, Object> b, Parent p) {
        super(b, p);
    }

    public Count<Aggregation<T, ?>, ?> count() {
        return new Count<>(b, this);
    }

    public Simple<Aggregation<T, ?>, ?> longSum() {
        return new Simple<>(b, this, "longSum");
    }

    public Simple<Aggregation<T, ?>, ?> doubleSum() {
        return new Simple<>(b, this, "doubleSum ");
    }

    public Simple<Aggregation<T, ?>, ?> doubleMax() {
        return new Simple<>(b, this, "doubleMax");
    }

    public Simple<Aggregation<T, ?>, ?> doubleMin() {
        return new Simple<>(b, this, "doubleMin");
    }

    public Simple<Aggregation<T, ?>, ?> longMax() {
        return new Simple<>(b, this, "doubleMax");
    }

    public Simple<Aggregation<T, ?>, ?> longMin() {
        return new Simple<>(b, this, "longMin");
    }

    public static class Aggregations<T extends HasParent, Parent extends T> extends BaseList<Parent> {

        public Aggregations(Map<String, Object> b, Parent p) {
            super(b, p, "aggregations");
        }

        public Aggregation<Aggregations<T, ?>, ?> add() {
            super.addInternal();
            return new Aggregation<>(sub, this);
        }
    }

}
