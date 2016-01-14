package com.max.ejb.cont;

import javax.ejb.Stateless;

/**
 * Created by add on 01.07.2015.
 */
@Stateless
public class Type1 implements Type<String> {
    @Override
    public String getTypeT() {
        return "null";
    }
}
