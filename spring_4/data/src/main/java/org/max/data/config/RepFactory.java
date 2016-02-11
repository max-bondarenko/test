package org.max.data.config;

import org.max.data.back.RepoBackRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.data.repository.query.parser.AbstractQueryCreator;
import org.springframework.data.repository.query.parser.Part;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Iterator;

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



                return new RepositoryQuery() {
                    @Override
                    public Object execute(Object[] parameters) {
                        if (parameters.length == 1) {
                            return dao.getSomeData((String) parameters[0]);
                        }
                        return null;
                    }

                    @Override
                    public QueryMethod getQueryMethod() {
                        return new QueryMethod(method, metadata);
                    }
                };
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
