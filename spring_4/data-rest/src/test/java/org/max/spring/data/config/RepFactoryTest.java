package org.max.spring.data.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.repository.query.QueryLookupStrategy;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class RepFactoryTest {

    RepFactory test = new RepFactory();

    @Test
    public void testGetQueryLookupStrategy() throws Exception {
        QueryLookupStrategy queryLookupStrategy = test.getQueryLookupStrategy(QueryLookupStrategy.Key.CREATE);
        assertNotNull(queryLookupStrategy);
    }
}