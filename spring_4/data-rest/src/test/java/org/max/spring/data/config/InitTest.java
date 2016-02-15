package org.max.spring.data.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.max.spring.data.config.test_repos.SimpleRepo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class InitTest {
    @Resource
    SimpleRepo simpleRepo;

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
}