package org.max.spring.data.config;

import org.max.spring.data.back.QueryBackend;
import org.max.spring.data.config.query.DruidLookUpStrategy;
import org.max.spring.data.config.repository.DefaultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

/**
 * Factory that actually responsible for creation of Repo or really backs Repo on behind.
 * Created by Maksym_Bondarenko on 2/11/2016.
 */
public class RepFactory extends RepositoryFactorySupport {

    private static final Logger log = LoggerFactory.getLogger(RepFactory.class);

    private RestTemplate template;
    // may get from ctx by narrower types
    private QueryBackend backend;

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    @Override
    protected void validate(RepositoryMetadata repositoryMetadata) {
        log.info("validate it: {}", repositoryMetadata);
    }

    @Override
    protected QueryLookupStrategy getQueryLookupStrategy(QueryLookupStrategy.Key key) {
        return new DruidLookUpStrategy();
    }

    @Override
    public <T, ID extends Serializable> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        throw new UnsupportedOperationException("getEntityInfo not supported for this type of Repo");
    }

    @Override
    protected Object getTargetRepository(RepositoryMetadata md) {
        Class<?> intf = md.getRepositoryInterface();
        boolean isBareInterface = intf.getDeclaredMethods().length == 0;

        Class<?> domainType = md.getDomainType();
        DefaultRepository df = new DefaultRepository(domainType);
        df.setBackend(backend);
        return df;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return DefaultRepository.class;
    }


    public void setBackend(QueryBackend backend) {
        this.backend = backend;
    }
}
