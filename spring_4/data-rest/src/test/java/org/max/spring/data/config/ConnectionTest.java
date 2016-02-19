package org.max.spring.data.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.max.spring.data.config.test_repos.DefaultBackedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Maksym_Bondarenko on 2/19/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class ConnectionTest {

    @Autowired
    private DefaultBackedRepo repo;

    @Test
    public void testRun() throws Exception {
        String query = repo.byId("");
        assertNotNull(query);
        Assert.assertFalse(query.isEmpty());
    }


}
