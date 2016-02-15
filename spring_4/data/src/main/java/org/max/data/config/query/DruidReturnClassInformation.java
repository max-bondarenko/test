package org.max.data.config.query;

import org.springframework.data.repository.core.support.AbstractEntityInformation;

import java.io.Serializable;

/**
 * Created by Maksym_Bondarenko on 2/15/2016.
 */
public class DruidReturnClassInformation<T, ID extends Serializable> extends AbstractEntityInformation<T, ID> {

    /**
     * Creates a new {@link org.springframework.data.repository.core.support.AbstractEntityInformation} from the given domain class.
     *
     * @param domainClass must not be {@literal null}.
     */
    public DruidReturnClassInformation(Class<T> domainClass) {
        super(domainClass);
    }

    @Override
    public ID getId(T entity) {
        return null;
    }

    @Override
    public Class<ID> getIdType() {
        return null;
    }
}
