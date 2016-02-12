package org.max.data.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.max.data.rep.NotSimpleRepo;
import org.max.data.rep.SimpleRepo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Init.class})
public class InitTest {

    @Resource
    SimpleRepo simpleRepo;


    @Test
    public void testName() throws Exception {
        assertNotNull(simpleRepo);

        assertNotNull(simpleRepo.byId("ass"));
    }
}