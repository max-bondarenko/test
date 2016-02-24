package org.max.spring.data.config;

import org.max.spring.data.back.QueryBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

/**
 * Created by Maksym_Bondarenko on 2/5/2016.
 */
public class RepFactoryBeanSupport<T extends Repository<S, ID>, S, ID extends Serializable> extends RepositoryFactoryBeanSupport<T, S, ID> {

    @Autowired
    private QueryBackend backend;

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        RepFactory repFactory = new RepFactory();
        repFactory.setBackend(backend);
        return repFactory;
    }
}
