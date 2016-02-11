package org.max.data.config;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

/**
 * Created by Maksym_Bondarenko on 2/9/2016.
 */

@NoRepositoryBean
public interface GetRepository<T, ID extends Serializable> extends Repository<T, ID> {
    /**
     * Imple get
     *
     * @param i
     * @return
     */
    T byId(ID i);
}
