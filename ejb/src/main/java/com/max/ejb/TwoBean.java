package com.max.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by add on 01.07.2015.
 */
@Stateless
public class TwoBean implements Two {
    @Inject
    private First first;

    @Override
    public Long getLong(String st) {
        String string = first.getString();
        return Long.valueOf(string);
    }
}
