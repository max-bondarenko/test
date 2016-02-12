package org.max.data.config.query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.max.data.config.annotations.DruidQuery;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.RepositoryQuery;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class DruidLookUpStrategyTest {

    DruidLookUpStrategy test = new DruidLookUpStrategy();
    @Mock
    RepositoryMetadata metaData;

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

    @Test
    public void testResolveQueryPopulatedWithannotetion() throws Exception {
        RepositoryQuery repositoryQuery = base("get0");

        assertEquals("wiki", ((DruidTemplateQuery) repositoryQuery).getDataSource());
        assertEquals("templ_wiki", ((DruidTemplateQuery) repositoryQuery).getTemplateName());
    }

    @Test
    public void testResolveQueryPopulatedWithannotetionDefaultToClass() throws Exception {
        doReturn(ForMethodTest.class).when(metaData).getRepositoryInterface();

        RepositoryQuery repositoryQuery = base("get3");

        assertEquals("class_ds", ((DruidTemplateQuery) repositoryQuery).getDataSource());
        assertEquals("get3", ((DruidTemplateQuery) repositoryQuery).getTemplateName());
    }


    private RepositoryQuery base(String name) throws NoSuchMethodException {
        Class<ForMethodTest> t = ForMethodTest.class;
        Method getById = t.getMethod(name, String.class);

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