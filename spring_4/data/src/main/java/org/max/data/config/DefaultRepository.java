package org.max.data.config;

import org.springframework.beans.BeanUtils;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 2/11/2016.
 */
public class DefaultRepository<T, ID extends Serializable> implements Repository<T, ID> {

    private Class<T> returnType;

    public DefaultRepository(Class<T> domainType) {
        this.returnType = domainType;
    }

    public T byId(ID id) {
        //just for test
        if (returnType.isAssignableFrom(Map.class)) {
            Class<LinkedHashMap> linkedHashMapClass = LinkedHashMap.class;
            LinkedHashMap linkedHashMap = BeanUtils.instantiateClass(linkedHashMapClass);
            return (T) linkedHashMap;
        }
        return null;
    }
}
