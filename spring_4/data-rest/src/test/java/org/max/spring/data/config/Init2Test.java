package org.max.spring.data.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.max.spring.data.config.repository.GetRepository;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class Init2Test {

    @Resource
    GetRepository simpleRepo;


    @Test
    public void testName() throws Exception {
        assertNotNull(simpleRepo);
        assertNotNull(simpleRepo.byId("ass"));

    }

    @Test
    public void testBackendInject() throws Exception {
        assertNotNull(simpleRepo);
        assertTrue(AopUtils.isAopProxy(simpleRepo));
    }
}