package org.max.data.config;

import org.springframework.data.repository.Repository;

import java.io.Serializable;

/**
 * Created by Maksym_Bondarenko on 2/11/2016.
 */
public class DefaultRepository<T, ID extends Serializable> implements Repository<T, ID> {
}
