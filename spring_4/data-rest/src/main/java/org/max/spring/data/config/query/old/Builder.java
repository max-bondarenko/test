package org.max.spring.data.config.query.old;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

/**
 * Created by Maksym_Bondarenko on 3/1/2016.
 */
public class Builder implements HasParent<Builder> {

    HashMap<String, Object> b = new HashMap<>();

    public Timeseries timeseries() {
        b.put("queryType", "timeseries");
        return new Timeseries(b, this);
    }

    public Filter<Builder, ?> filter() {
        return new Filter<>(b, this);
    }

    @Override
    public Builder end() {
        return this;
    }

    @Override
    public String build() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(b);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static enum Granularity {
        day, hour
    }
}
