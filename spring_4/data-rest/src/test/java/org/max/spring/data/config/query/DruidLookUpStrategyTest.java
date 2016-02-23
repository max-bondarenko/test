package org.max.spring.data.config.query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.max.spring.data.back.QueryBackend;
import org.max.spring.data.config.RepFactory;
import org.max.spring.data.config.annotations.DruidQuery;
import org.max.spring.data.config.query.template.DruidTemplateQuery;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.RepositoryQuery;

import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DruidLookUpStrategyTest {

    @Mock
    RepositoryMetadata metaData;
    @Mock
    QueryBackend backend;

    RepFactory test = new RepFactory();

    @Test
    public void testResolveQueryByAnnotationType() throws Exception {
        base("get0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testResolveQueryRawTypeThrow() throws Exception {
        base("get1");
    }

    @Test
    public void testResolveQueryRawType() throws Exception {
        base("get0");
    }

    private RepositoryQuery base(String name) throws NoSuchMethodException {
        Class<ForMethodTest> t = ForMethodTest.class;
        Method getById = t.getMethod(name, String.class);
        test.setBackend(backend);

        RepositoryQuery repositoryQuery = test.resolveQuery(getById, metaData, null);

        assertTrue(repositoryQuery instanceof DruidTemplateQuery);
        return repositoryQuery;
    }

    @DruidQuery("class_ds")
    public static class ForMethodTest {
        @DruidQuery(dataSource = "wiki", templateName = "templ_wiki")
        public String get0(String a) {
            return null; // do nothing just a type
        }

        @DruidQuery("wiki")
        public Integer get1(String a) {
            return null;
        }

        /**
         * dataSource: from class annotation
         * template: method name
         */
        @DruidQuery
        public String get3(String a) {
            return null;
        }
    }
}