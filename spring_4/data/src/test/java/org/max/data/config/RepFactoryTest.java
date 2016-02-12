package org.max.data.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.max.data.config.query.DruidLookUpStrategy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.repository.query.QueryLookupStrategy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class RepFactoryTest {

    RepFactory test = new RepFactory();

    @Test
    public void testGetQueryLookupStrategy() throws Exception {

        QueryLookupStrategy queryLookupStrategy = test.getQueryLookupStrategy(QueryLookupStrategy.Key.CREATE);

        assertNotNull(queryLookupStrategy);
        assertTrue("Wrong class type", queryLookupStrategy instanceof DruidLookUpStrategy);
    }
}