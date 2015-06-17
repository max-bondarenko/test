package org.max.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import static org.junit.Assert.assertNotNull;

public class CoreTest {

    private EntityManager em;

    @Before
    public void setUp() throws Exception {
        em = Persistence.createEntityManagerFactory("unit").createEntityManager();
    }

    @Test
    public void testName() throws Exception {
        One o = new One("type one");


        Core c = new Core(o);

        em.getTransaction().begin();

        em.persist(c);

        em.getTransaction().commit();
        assertNotNull(c.getId());
    }

    @Test
    public void testName2() throws Exception {
        em.getTransaction().begin();
        Core core = em.find(Core.class, 1L);
        assertNotNull(core);

        One two = new One("type2");
        core.setOne(two);
        em.getTransaction().commit();

        assertNotNull(core.getOne().getId());
    }

    @Test
    public void testMerge() throws Exception {
        em.getTransaction().begin();
        Core core = em.find(Core.class, 1L);
        assertNotNull(core);

        One two = new One("type3");
        core.setOne(two);

        em.merge(core);
        em.getTransaction().commit();

        assertNotNull(core.getOne().getId());
    }

    @After
    public void tearDown() throws Exception {
        if (em != null) {
            em.close();
        }
    }
}