package com.max.ejb;

import javax.ejb.Stateless;

/**
 * Created by add on 26.06.2015.
 */
@Stateless
public class FirstBean implements First {

    @Override
    public String getString() {
        return "32423333";
    }
}
