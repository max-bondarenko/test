package org.max.spr.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.max.spr.core.bean.SimpleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class RootConfigTest {

    @Autowired
    private SimpleResource resource;

    @Test
    public void testName() throws Exception {
        assertNotNull(resource.getSomething());
    }
}