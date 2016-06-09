package com.max.repositories;

import com.max.entities.Post;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by mbondarenko on 13.03.14.
 */
@Repository
public class Post2Repository implements IPost2Repository {

    @PersistenceContext
    private EntityManager em;

    public Post getPostById(Integer id) throws Exception {
        Query nativeQuery = em.createNativeQuery("select * from POST where post_id = ?", Post.class);
        nativeQuery.setParameter(1, id);
        Post singleResult = (Post) nativeQuery.getSingleResult();
        return singleResult;
    }


}
