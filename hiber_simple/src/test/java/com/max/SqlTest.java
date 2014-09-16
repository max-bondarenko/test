package com.max;

import com.max.entities.Post;
import com.max.repositories.IPost2Repository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;

import static junit.framework.Assert.*;

/**
 * Created by mbondarenko on 31.07.2014.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/test-context.xml")
@TransactionConfiguration
public class SqlTest {

    @Resource
    DataSource dataSource;
    @Resource
    ApplicationContext ctx;
    @Resource
    IPost2Repository repo;


    @Test
    @Transactional
    public void testName() throws Exception {
        ResourceDatabasePopulator p = new ResourceDatabasePopulator();
        final org.springframework.core.io.Resource resource = ctx.getResource("classpath:s2.sql");
        assertNotNull(resource);

        p.addScript(resource);
        DatabasePopulatorUtils.execute(p, dataSource);
        final Post po = repo.getPostById(1000);

        assertNotNull(po);
        assertEquals("title_1000", po.getTitle());
        assertTrue(!po.getRecords().isEmpty());
        assertEquals("23222", po.getRecords().get(0).getValue());
    }
}
