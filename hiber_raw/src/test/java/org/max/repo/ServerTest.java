package org.max.repo;

import org.h2.server.TcpServer;
import org.h2.tools.Server;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

/**
 * Created by mbondarenko on 03.04.14.
 */
public class ServerTest {

    public static final String JDBC_H2 = "jdbc:h2:";
    /**
     * it works only if   there are slf & logback or log4j
     */
    public static final String TRACE_CONSTANT = "TRACE_LEVEL_FILE=4";

    @Test
    public void testServerForH2() throws Exception {

        TcpServer server = new TcpServer();
        Server s = new Server(server);
        try {
            s.start();

            Connection connection = DriverManager.getConnection(JDBC_H2 + server.getURL() + "/" + server.getName() + ";" + TRACE_CONSTANT);
            Statement statement = connection.createStatement();
            boolean execute = statement.execute("select 1;");
            if (execute) {
                statement.getResultSet().next();
                assertEquals(1, statement.getResultSet().getInt(1));
            }
            connection.close();
        } finally {
            s.stop();
        }
    }
}
