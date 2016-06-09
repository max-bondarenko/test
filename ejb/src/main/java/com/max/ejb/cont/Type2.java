package com.max.ejb.cont;

import javax.ejb.Stateless;

/**
 * Created by add on 01.07.2015.
 */
@Stateless
public class Type2 implements Type<Long> {
    @Override
    public Long getTypeT() {
        return 233L;
    }
}
