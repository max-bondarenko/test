package com.max.ejb;

import com.max.ejb.cont.Type;
import com.max.ejb.cont.Type1;
import com.max.ejb.cont.Type2;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

@RunWith(Arquillian.class)
public class TwoBeanTest {

    @BeforeClass
    public static void setUp() throws Exception {

    }

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClasses(First.class,
                        Two.class,
                        FirstBean.class,
                        TwoBean.class,
                        Type2.class,
                        Type1.class,
                        Type.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return jar;
    }

    @EJB
    Two two;

    @Test
    public void test() {
        Assert.assertEquals(Long.valueOf(1L), two.getLong(null));
    }

}