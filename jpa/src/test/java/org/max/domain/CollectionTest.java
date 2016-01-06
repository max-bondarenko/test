package org.max.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class CollectionTest {
    private static final Logger log = LoggerFactory.getLogger(CollectionTest.class);

    private EntityManager em;

    @Before
    public void setUp() throws Exception {
        em = Persistence.createEntityManagerFactory("unit").createEntityManager();
    }


    @Test
    public void testEmebb() throws Exception {
        em.getTransaction().begin();
        Core c = new Core();

        List<ListItem> i = new ArrayList<>(3);
        i.add(new ListItem("5555"));
        i.add(new ListItem("5556"));
        i.add(new ListItem("5557"));
        c.setList(i);
        /*
        INSERT INTO Core (id, one_id) VALUES (NULL, 1);

        INSERT INTO items (Core_id, type) VALUES (1, '5555');
        INSERT INTO items (Core_id, type) VALUES (1, '5556');
        INSERT INTO items (Core_id, type) VALUES (1, '5557');
         */

        em.persist(c);

        em.getTransaction().commit();
        em.getTransaction().begin();
        Long id = c.getId();
        log.info("id: {}:", id);

        Core core = em.find(Core.class, id);

        ListItem listItem = core.getList().get(0);
        core.getList().remove(listItem);
        em.getTransaction().commit();
       /* DELETE FROM items WHERE Core_id = 1
        INSERT INTO items (Core_id, type) VALUES (1, '5556');
        INSERT INTO items (Core_id, type) VALUES (1, '5557');
        */

    }

    @After
    public void tearDown() throws Exception {
        if (em != null) {
            em.close();
        }
    }
}