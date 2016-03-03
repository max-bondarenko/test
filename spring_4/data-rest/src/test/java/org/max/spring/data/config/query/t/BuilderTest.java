package org.max.spring.data.config.query.t;

import org.junit.Test;
import org.max.spring.data.config.query.druid.Builder;
import org.max.spring.data.config.query.druid.Timeseries;

import static org.junit.Assert.assertNotNull;

public class BuilderTest {

    @Test
    public void test() throws Exception {
        Timeseries t = new Builder().timeseries();

        String build;


        t = new Builder().timeseries();
        build = t.dataSource("sd").filter()
                .and()
                .add()
                .selector().dimension("A").value("va").dimension("Asd").dimension("sadf").end()
                .end()
                .add()
                .selector().dimension("a").end()
                .end()
                .end().build();

        assertNotNull(build);

    }
}