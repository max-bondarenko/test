package org.max.spring.data.config.query.druid;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Maksym_Bondarenko on 3/1/2016.
 */
public class Builder implements HasParent<Builder> {

    HashMap<String, Object> b = new HashMap<>();

    Timeseries timeseries() {
        b.put("queryType", "timeseries");
        return new Timeseries(b, this);
    }

    @Override
    public Builder end() {
        return this;
    }

    @Override
    public String build() {
        return new Gson().toJson(b);
    }

    public static enum Granularity {
        day, hour
    }
}
