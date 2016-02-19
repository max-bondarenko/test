package org.max.spring.data.config.repository;

import org.max.spring.data.back.QueryBackend;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 2/11/2016.
 */
public class DefaultRepository<T, ID extends Serializable> implements GetRepository<T, ID> {

    private Class<T> returnType;

    private QueryBackend<T> backend;

    public DefaultRepository(Class<T> domainType) {
        this.returnType = domainType;
    }

    public void setBackend(QueryBackend backend) {
        this.backend = backend;
    }

    @Override
    public T byId(ID id) {
        Assert.notNull(backend, "There is no backend injected.");
        //just for test
        if (returnType.isAssignableFrom(Map.class)) {
            Class<LinkedHashMap> linkedHashMapClass = LinkedHashMap.class;
            LinkedHashMap linkedHashMap = BeanUtils.instantiateClass(linkedHashMapClass);
            return (T) linkedHashMap;
        }
        Object[] parameters = new Object[]{id};
        T ret = backend.executeByUrl(backend.getBaseUrl(), HttpMethod.GET, returnType, parameters);
        return ret;
    }
}
