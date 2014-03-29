package org.max.service;

import org.junit.Before;
import org.junit.Test;
import org.max.Book;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: add
 * Date: 29.03.14
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class BookServiceTest {

    private BookService bs;

    @Before
    public void setUp() throws Exception {
        bs = new BookService();

    }

    @Test
    public void testGetBook() throws Exception {
        Book book = bs.getBook(3);
        assertNotNull(book);


    }
}
