package org.max.update;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Assert;
import org.junit.Before;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Maksym_Bondarenko on 5/23/2016.
 */
public class DbUpdateExampleTest {

    private DbUpdateExample test;
    private JdbcDataSource ds;

    @Before
    public void setUp() throws Exception {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test");
        ds.setUser("sa");
        ds.setPassword("sa");

        test = new DbUpdateExample();
        test.setDataSource(ds);

        Connection connection = ds.getConnection();
        PreparedStatement createTable = connection.prepareStatement("CREATE TABLE User (" +
                " id INTEGER ," +
                " comment VARCHAR , " +
                " name VARCHAR , " +
                " email VARCHAR , " +
                " supervisorName VARCHAR , " +
                " supervisorEmail VARCHAR , " +
                " supervisorPhone VARCHAR , " +
                " belongsToId VARCHAR , " +
                " type VARCHAR , " +
                " roleId VARCHAR , " +
                " isActive VARCHAR(1) " +
                ")"
        );
        createTable.executeUpdate();
        createTable.close();
        PreparedStatement insert = connection.prepareStatement(" insert into User  VALUES(1," +
                "'comment'," +
                "'name'," +
                "'email'," +
                "'supervisorName'," +
                "'supervisorEmail'," +
                "'supervisorPhone'," +
                "'belongsToId'," +
                "'type'," +
                "'roleId'," +
                "'Y')  ");
        insert.executeUpdate();
        insert.close();
    }

    @org.junit.Test
    public void update() throws Exception {
        User e = new User();
        e.setId(1L);
        String comment = "another comment";
        e.setComment(comment);

        test.update(e);

        Connection connection = ds.getConnection();
        PreparedStatement select = connection.prepareStatement("SELECT  comment from User where id = 1 ");
        ResultSet rs = select.executeQuery();
        rs.first();
        String string = rs.getString(1);
        Assert.assertEquals(comment, string);
        select.close();
        connection.close();
    }

}