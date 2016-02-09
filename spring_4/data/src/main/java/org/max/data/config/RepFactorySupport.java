package org.max.data.config;

import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;import java.lang.Class;import java.lang.Object;import java.lang.Override;

/**
 * Created by Maksym_Bondarenko on 2/5/2016.
 */
public class RepFactorySupport extends RepositoryFactoryBeanSupport {

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        return new RepositoryFactorySupport() {
            @Override
            public <T, ID extends Serializable> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
                return null;
            }

            @Override
            protected Object getTargetRepository(RepositoryMetadata metadata) {
                return null;
            }

            @Override
            protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
                return null;
            }
        };
    }
}
