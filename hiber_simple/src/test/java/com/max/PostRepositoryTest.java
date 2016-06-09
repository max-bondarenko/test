package com.max;

import com.max.entities.Like;
import com.max.entities.Post;
import com.max.entities.Record;
import com.max.repositories.IPost2Repository;
import com.max.repositories.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.TransactionManager;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="classpath:META-INF/application-context.xml")
@ContextConfiguration(locations = "classpath:META-INF/test-context.xml")
public class PostRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(PostRepositoryTest.class);

    @Autowired
    PostRepository repository;
    @Autowired
    IPost2Repository repository2;

    @Autowired
    PlatformTransactionManager ptm;

    @Test
    public void testRead() throws Exception {
        Post one = repository.findOne(1);
        assertNotNull(one);

        assertEquals("title_1", one.getTitle());
        log.info(one.getTitle());
    }

    @Test
    public void testRW() {
        Post post = new Post();
        post.setPostDate(new Date());
        post.setTitle("First Post");

        repository.save(post);

        Post dbpost = repository.findOne(post.getPostId());
        assertNotNull(dbpost);
        log.info(dbpost.getTitle());
    }

    @Test
    public void testAssociation() throws Exception {
        TransactionStatus transaction = ptm.getTransaction(new DefaultTransactionDefinition());
        Post one = repository.findOne(1);
        assertNotNull(one);
        List<Record> records = one.getRecords();
        assertNotNull(records);
        assertEquals(3, records.size());
        log.debug("got some records for post_id: {} --> {}",one.getPostId(),  records);

        Record record = records.get(1);
        List<Like> likes = record.getLikes();
        assertNotNull(likes);

        ptm.rollback(transaction);
    }

    @Test
    public void testCustomRepo() throws Exception {
        TransactionStatus transaction = ptm.getTransaction(new DefaultTransactionDefinition());
        Post one = repository2.getPostById(1);
        assertEquals(3, one.getRecords().size());
        assertNotNull(one);
        ptm.rollback(transaction);
    }
}
