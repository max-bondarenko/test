package org.max.spring.data.config;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.Test;
import org.max.spring.data.config.query.template.TemplatePartTree;

import static org.junit.Assert.*;

/**
 * Created by Maksym_Bondarenko on 2/11/2016.
 */
public class PartTreeTest {
    @Test
    public void testPartTree() throws Exception {
        TemplatePartTree pt = new TemplatePartTree("findInWikiWithIdAndNameAndHits");
        Assert.assertThat(pt.getPropertyNames(), IsCollectionContaining.<String>hasItems("Id", "Name", "Hits"));
        assertNotNull(pt.getDataSource());
        assertEquals("wiki", pt.getDataSource());
    }

    @Test
    public void testNoDs() throws Exception {
        TemplatePartTree pt = new TemplatePartTree("findByIdAndNameAndHits");
        Assert.assertThat(pt.getPropertyNames(), IsCollectionContaining.<String>hasItems("Id", "Name", "Hits"));
        assertNull(pt.getDataSource());
    }

    @Test
    public void testNoPrefix() throws Exception {
        TemplatePartTree pt = new TemplatePartTree("idAndNameAndHits");
        Assert.assertThat(pt.getPropertyNames(), IsCollectionContaining.<String>hasItems("Id", "Name", "Hits"));
        assertNull(pt.getDataSource());
    }

}
