package org.max.data.config.query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.max.data.config.annotations.DruidQuery;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.RepositoryQuery;

import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class DruidLookUpStrategyTest {

    DruidLookUpStrategy test = new DruidLookUpStrategy();
    @Mock
    RepositoryMetadata metaData;

    public static class ForMethodTest {
        @DruidQuery(dataSource = "wiki")
        public String getById(String a) {
            return null; // do nothing just a type
        }
        @DruidQuery("wiki")
        public Integer get1(){
            return null;
        }
    }

    @Test
    public void testResolveQueryByAnnotationType() throws Exception {
        Class<ForMethodTest> t = ForMethodTest.class;
        Method getById = t.getMethod("getById", String.class);

        doReturn(String.class).when(metaData).getReturnedDomainClass(any(Method.class));

        RepositoryQuery repositoryQuery = test.resolveQuery(getById, metaData, null);
        assertTrue(repositoryQuery instanceof DruidTemplateQuery);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testResolveQueryRawType() throws Exception {
        Class<ForMethodTest> t = ForMethodTest.class;
        Method getById = t.getMethod("getById", String.class);

        doReturn(String.class).when(metaData).getReturnedDomainClass(any(Method.class));

        RepositoryQuery repositoryQuery = test.resolveQuery(getById, metaData, null);
        assertTrue(repositoryQuery instanceof DruidTemplateQuery);
    }
}