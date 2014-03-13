package com.max.repositories;

import com.max.entities.Post;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mbondarenko on 13.03.14.
 */

public interface IPost2Repository {
    Post getPostById(Integer id) throws Exception;
}
