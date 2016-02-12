package org.max.data.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.max.data.rep.NotSimpleRepo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Init.class})
public class Init2Test {

    @Resource
    GetRepository simpleRepo;
    @Resource
    NotSimpleRepo notSimpleRepo;

    @Test
    public void testName() throws Exception {
        assertNotNull(simpleRepo);
        assertNotNull(notSimpleRepo);
        assertNotNull(simpleRepo.byId("ass"));
        assertNotNull(notSimpleRepo.byName("asfas"));
    }
}