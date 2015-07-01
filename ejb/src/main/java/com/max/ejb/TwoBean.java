package com.max.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created by add on 01.07.2015.
 */
@Stateless
public class TwoBean implements Two {
    @EJB
    private First first;

    @Override
    public Long getLong(String st) {
        String string = first.getString();
        return Long.valueOf(string);
    }
}
