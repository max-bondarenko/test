package com.max;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA._PolicyStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.max.entities.Post;
import com.max.repositories.PostRepository;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="classpath:META-INF/application-context.xml")
@ContextConfiguration(locations = "classpath:META-INF/test-context.xml")
public class PostRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(PostRepositoryTest.class);

    @Autowired
    PostRepository repository;

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

}
