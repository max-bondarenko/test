package com.max.ejb;

import com.max.ejb.cont.Type;

import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by add on 26.06.2015.
 */
@Stateless
public class FirstBean implements First {

    @Any
    @Inject
    private List<Type> types;

    @Override
    public String getString() {
        return "32423333";
    }
}
