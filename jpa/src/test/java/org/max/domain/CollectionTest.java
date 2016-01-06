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

    @Test
    public void testSet() throws Exception {
        em.getTransaction().begin();
        Core c = new Core();
        em.persist(c);
        log.info("core id: {}", c.getId());
        c.getSet().add(new SetItem("a1"));
        c.getSet().add(new SetItem("b1"));
        c.getSet().add(new SetItem("b2"));
        em.getTransaction().commit();

        em.getTransaction().begin();
        Core core = em.find(Core.class, 1L);
        /* for Core --> One
          SELECT
         core0_.id     AS id1_0_0_,
         core0_.one_id AS one_id2_0_0_,
         one1_.id      AS id1_1_1_,
         one1_.type    AS type2_1_1_
         FROM
         Core core0_
         LEFT OUTER JOIN
         One one1_
         ON core0_.one_id = one1_.id
         WHERE
         core0_.id = 1
         */

        /* for Core --> SetItem
         SELECT
         set0_.core_id AS core_id3_0_0_,
         set0_.id      AS id1_2_0_,
         set0_.id      AS id1_2_1_,
         set0_.name    AS name2_2_1_
         FROM
         SetItem set0_
         WHERE
         set0_.core_id = 1
         ORDER BY
         set0_.name

         */

        core.getSet().remove(new SetItem("b1"));
        /*
         UPDATE SetItem SET core_id = NULL WHERE core_id = 1 AND id = 2;
         DELETE FROM SetItem WHERE id = 2;
         */

        em.getTransaction().commit();

    }

    @After
    public void tearDown() throws Exception {
        if (em != null) {
            em.close();
        }
    }
}