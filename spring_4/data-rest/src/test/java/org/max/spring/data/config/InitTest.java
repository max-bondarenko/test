package org.max.spring.data.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.max.spring.data.back.QueryBackend;
import org.max.spring.data.config.test_repos.DefaultBackedRepo;
import org.max.spring.data.config.test_repos.SimpleRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, InitTest.Conf.class})
public class InitTest {
    static boolean isMethodCall = false;
    @Resource
    SimpleRepo simpleRepo;
    @Resource
    private DefaultBackedRepo defaultBackedRepo;

    @Test
    public void testName() throws Exception {
        assertNotNull(simpleRepo);

        assertNotNull(simpleRepo.byId("ass"));
    }

    @Test
    public void testNameAndType() throws Exception {
        assertNotNull(simpleRepo);

        boolean isCorrectType = simpleRepo.byId("asdfas") instanceof Map;
        assertTrue(isCorrectType);
    }

    @Test
    public void testDefaultRepo() {
        assertNotNull(defaultBackedRepo);
        String asd = defaultBackedRepo.byId("asd");
        assertTrue(isMethodCall);
    }

    @Configuration
    public static class Conf {
        @Bean
        public QueryBackend orderService() {
            QueryBackend r = new QueryBackend() {
                @Override
                public String getBaseUrl() {
                    return "http://localhost";
                }

                @Override
                public String getTemplate(String templateName) {
                    return null;
                }

                @Override
                public Object executeByUrl(String url, HttpMethod method, Object[] parameters) {
                    isMethodCall = true;
                    return null;
                }

                @Override
                public Object execute(String template) {
                    return null;
                }

                @Override
                public Object execute(String templateName, Map placeholders) {
                    return null;
                }
            };
            return r;
        }

    }
}