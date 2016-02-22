package org.max.spring.data.config;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.Test;
import org.max.spring.data.config.query.template.PartTree;

import static org.junit.Assert.*;

/**
 * Created by Maksym_Bondarenko on 2/11/2016.
 */
public class PartTreeTest {
    @Test
    public void testPartTree() throws Exception {
        PartTree pt = new PartTree("findInWikiWithIdAndNameAndHits");
        Assert.assertThat(pt.getPropertyNames(), IsCollectionContaining.<String>hasItems("Id", "Name", "Hits"));
        assertNotNull(pt.getDataSource());
        assertEquals("wiki", pt.getDataSource());
    }

    @Test
    public void testNoDs() throws Exception {
        PartTree pt = new PartTree("findByIdAndNameAndHits");
        Assert.assertThat(pt.getPropertyNames(), IsCollectionContaining.<String>hasItems("Id", "Name", "Hits"));
        assertNull(pt.getDataSource());
    }

    @Test
    public void testNoPrefix() throws Exception {
        PartTree pt = new PartTree("idAndNameAndHits");
        Assert.assertThat(pt.getPropertyNames(), IsCollectionContaining.<String>hasItems("Id", "Name", "Hits"));
        assertNull(pt.getDataSource());
    }

}
