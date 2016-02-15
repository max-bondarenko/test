package org.max.spring.data.config;

import org.junit.Test;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.data.repository.query.parser.PartTree;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Maksym_Bondarenko on 2/11/2016.
 */

public class PartTreeTest {

    @Test
    public void testPartTree() throws Exception {
//        AbstractQueryCreator

        PartTree pt = new PartTree("findByIdOrNameAndHits", Cl.class);

        Iterable<Part> parts = pt.getParts();
        Part next = parts.iterator().next();
        PropertyPath property = next.getProperty();
        Part.Type type = next.getType();

        assertNotNull(parts);

    }

    /**
     * Created by Maksym_Bondarenko on 2/11/2016.
     */
    public static class Cl {
        String id;
        String name;

        List hits;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List getHits() {
            return hits;
        }
    }
}
