package org.max.spring.data.config.query.druid;

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

    public Timeseries dataSource(String ds) {
        b.put("dataSource", ds);
        return this;
    }

    public Timeseries granularity(Builder.Granularity granularity) {
        b.put("granularity", granularity.name());
        return this;
    }
}
