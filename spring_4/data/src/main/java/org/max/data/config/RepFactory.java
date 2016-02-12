package org.max.data.config;

import org.max.data.back.RepoBackRegistry;
import org.max.data.config.query.DruidTemplateQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.RepositoryQuery;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Factory that actually responsible for creation of Repo or really backs Repo on behind.
 * Created by Maksym_Bondarenko on 2/11/2016.
 */
public class RepFactory extends RepositoryFactorySupport {

    private static final Logger log = LoggerFactory.getLogger(RepFactory.class);

    private RepoBackRegistry dao;

    public void setDao(RepoBackRegistry dao) {
        this.dao = dao;
    }

    @Override
    protected void validate(RepositoryMetadata repositoryMetadata) {
        log.info("validate it: {}", repositoryMetadata);
    }

    @Override
    protected QueryLookupStrategy getQueryLookupStrategy(QueryLookupStrategy.Key key) {
        return new QueryLookupStrategy() {
            @Override
            public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, NamedQueries namedQueries) {
                return new DruidTemplateQuery(method, metadata);
            }
        };
    }

    @Override
    public <T, ID extends Serializable> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        throw new UnsupportedOperationException("getEntityInfo not supported for this type of Repo");
    }

    @Override
    protected Object getTargetRepository(RepositoryMetadata metadata) {
        return new DefaultRepository();
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return DefaultRepository.class;
    }


}
