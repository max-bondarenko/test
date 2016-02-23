package org.max.data.init;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.max.data.rep.SimpleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Init.class})
public class InitTest {

    @Autowired
    SimpleRepo repo;

    @Test
    public void test() throws Exception {
        List<Map> inWikipediaWithDate = repo.findInWikipediaWithDate(null);
        assertNotNull(inWikipediaWithDate);

    }
}
