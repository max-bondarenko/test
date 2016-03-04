package org.max.spring.data.config.query.druid;

import org.junit.Test;

import java.util.Date;

public class TimeseriesTest {
    @Test
    public void testName() throws Exception {
        Builder b = new Builder();

        Timeseries end = b.timeseries().intervals().add().from(new Date()).to(new Date()).end().end();


        System.out.println(end.build());

    }
}