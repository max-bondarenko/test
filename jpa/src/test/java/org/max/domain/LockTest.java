package org.max.domain;
/*
* SupplyOn CONFIDENTIAL
* __________________
* 
* [2015] - [2020] SupplyOn AG
* All Rights Reserved.
* 
* NOTICE:  All information contained herein is, and remains
* the property of SupplyOn AG.
* The intellectual and technical concepts contained
* herein are proprietary to SupplyOn AG and
* may be covered by European and Foreign Patents,
* patents in process, and are protected by trade secret or copyright law.
* Dissemination of this information or reproduction of this material
* is strictly forbidden unless prior written permission is obtained
* from SupplyOn AG.
* <p/>
* Created by : Maksym_Bondarenko
* Created at : 16-10-2015
*/

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.*;

import static org.junit.Assert.assertNotNull;

/**
 *
 */
public class LockTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testName() throws Exception {
        EntityManager em = Persistence.createEntityManagerFactory("unit").createEntityManager();

        thrown.expect(PessimisticLockException.class);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Core core = em.find(Core.class, 1L);
        assertNotNull(core);

        em.lock(core, LockModeType.PESSIMISTIC_WRITE);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                EntityManager em1 = Persistence.createEntityManagerFactory("unit").createEntityManager();
                EntityTransaction transaction1 = em1.getTransaction();
                transaction1.begin();

                Core core1 = em1.find(Core.class, 1L);
                em1.lock(core1, LockModeType.PESSIMISTIC_WRITE);

                transaction1.commit();


            }
        });
        thread.run();
        thread.join();

        transaction.commit();

        assertNotNull(core.getOne().getId());


    }
}
