package org.max.data.config;

import org.max.data.back.RepoBackRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

/**
 * Created by Maksym_Bondarenko on 2/5/2016.
 */
public class RepFactoryBeanSupport<T extends Repository<S, ID>, S, ID extends Serializable> extends RepositoryFactoryBeanSupport<T, S, ID> {

    @Autowired
    private RepoBackRegistry registry;
    @Autowired(required = false)
    private RestTemplate template;

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        RepFactory repFactory = new RepFactory();
        repFactory.setDao(registry);
        repFactory.setTemplate(template);
        return repFactory;
    }
}
