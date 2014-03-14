package com.max;

import com.max.entities.Like;
import com.max.entities.Record;
import com.max.repositories.RecordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by mbondarenko on 14.03.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="classpath:META-INF/application-context.xml")
@ContextConfiguration(locations = "classpath:META-INF/test-context.xml")
public class RecordRepoTest {


    @Autowired
    private RecordRepository repository;
    @Autowired
    PlatformTransactionManager ptm;

    @Test
    public void testRecordAndLike() throws Exception {
        TransactionStatus transaction = ptm.getTransaction(new DefaultTransactionDefinition());
        Record one = repository.findOne(1);
        assertNotNull(one);
        List<Like> likes = one.getLikes();
        assertNotNull(likes);

        ptm.rollback(transaction);
    }
}
