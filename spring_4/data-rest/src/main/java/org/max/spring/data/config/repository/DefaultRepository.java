package org.max.spring.data.config.repository;

import org.springframework.beans.BeanUtils;
import org.springframework.data.repository.Repository;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 2/11/2016.
 */
public class DefaultRepository<T, ID extends Serializable> implements Repository<T, ID> {

    private Class<T> returnType;

    private RestTemplate template;


    public DefaultRepository(Class<T> domainType) {
        this.returnType = domainType;
    }

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public T byId(ID id) {
        Assert.notNull(template, "There is no RestTemplate injected.");
        //just for test
        if (returnType.isAssignableFrom(Map.class)) {
            Class<LinkedHashMap> linkedHashMapClass = LinkedHashMap.class;
            LinkedHashMap linkedHashMap = BeanUtils.instantiateClass(linkedHashMapClass);
            return (T) linkedHashMap;
        }
        return null;
    }
}
