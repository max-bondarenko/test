package com.max;

import com.max.entities.Record;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/test2-context.xml")
public class PostRepository2Test {

    private static final Logger log = LoggerFactory.getLogger(PostRepository2Test.class);

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sf;


    @Test
    public void testSameSession() throws Exception {
        Session s = sf.openSession();

        Record one = (Record) s.get(Record.class, 3);
        assertNotNull(one);

        List list = s.createQuery(" from Record where id = 3 ").list();
        assertEquals(1, list.size());
        Record o = (Record) list.get(0);
        log.info(" is it equals:  " + o.equals(one));
        assertTrue(o.equals(one));
    }

    @Test
    public void testTwoDifferentSession() throws Exception {
        Session s = sf.openSession();
        Record one = (Record) s.get(Record.class, 3);
        assertNotNull(one);

        s.close();

        assertNotNull(one);
        s = sf.openSession();
        List list = s.createQuery(" from Record where id = 3 ").list();
        assertEquals(1, list.size());
        Record o = (Record) list.get(0);
        log.info(" is it equals:  " + o.equals(one));

        assertTrue(!o.equals(one));
        assertEquals(o.getId(), one.getId());
        assertEquals(o.getValue(), one.getValue());
    }


}
