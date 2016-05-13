package org.max.spring.data.config.query.builder.granularity;


import com.fasterxml.jackson.annotation.JsonValue;

import static org.max.spring.data.config.query.builder.granularity.PeriodGranularity.Period.*;

/**
 * Created by Maksym_Bondarenko on 4/20/2016.
 */
public enum Granularity {
    //simple
    all, none, minute, fifteen_minute, thirty_minute, hour, day,
    //complex
    P_M(new PeriodGranularity(M)),
    P1M(new PeriodGranularity(M)),
    P_W(new PeriodGranularity(W)),
    P1W(new PeriodGranularity(W)),
    P_D(new PeriodGranularity(D)),
    P1D(new PeriodGranularity(D));


    private PeriodGranularity periodGranularity;

    Granularity() {
    }

    Granularity(PeriodGranularity periodGranularity) {
        this.periodGranularity = periodGranularity;
    }

    public Granularity setCount(int count) {
        if (this == P_D ||
                this == P_M ||
                this == P_W) {
            periodGranularity.setCount(count);
        }
        return this;
    }

    @JsonValue
    public Object get() {
        if (this.name().startsWith("P")) {
            return periodGranularity;
        } else {
            return this.name();
        }
    }

}
