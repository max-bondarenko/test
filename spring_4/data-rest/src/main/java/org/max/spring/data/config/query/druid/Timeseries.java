package org.max.spring.data.config.query.druid;

import org.max.spring.data.config.query.druid.aggregations.Aggregation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 3/1/2016.
 */
public class Timeseries extends Base<Builder> {

    public Timeseries(Map<String, Object> b, Builder parent) {
        super(b, parent);
    }

    public Filter<Timeseries, ?> filter() {
        HashMap<String, Object> v = new HashMap<>();
        b.put("filter", v);
        return new Filter<>(v, this);
    }

    public Aggregation.Aggregations<Timeseries, ?> aggregations() {
        HashMap<String, Object> v = new HashMap<>();
        b.put("aggregation", v);
        return new Aggregation.Aggregations<>(v, this);
    }

    public Interval.Intervals<Timeseries, ?> intervals() {
        return new Interval.Intervals<>(b, this);
    }

    public Timeseries dataSource(String ds) {
        b.put("dataSource", ds);
        return this;
    }

    public Timeseries granularity(Builder.Granularity granularity) {
        b.put("granularity", granularity.name());
        return this;
    }
}
