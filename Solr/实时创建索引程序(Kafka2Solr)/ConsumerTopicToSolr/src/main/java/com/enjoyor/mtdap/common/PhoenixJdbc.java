package com.enjoyor.mtdap.common;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;

public class PhoenixJdbc {

    private static Logger logger = Logger.getLogger(PhoenixJdbc.class.getName());
    private static Properties phoenixProp = InitPropertiesUtil.initPhoenixProp();
    private static LinkedList<Connection> connections = new LinkedList<>();

    static {
        try {
            for (int i = 0; i < Integer.parseInt(phoenixProp.getProperty("initPoolSize")); i++) {
                connections.push(DriverManager.getConnection(phoenixProp.getProperty("phoenixJdbc") + ":" + phoenixProp.getProperty("zkQuorum")));
            }
            logger.info("********** Initialize Phoenix JDBC Connection Pool.");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            if (connections.size() == 0) {
                connection = DriverManager.getConnection(phoenixProp.getProperty("phoenixJdbc") + ":" + phoenixProp.getProperty("zkQuorum"));
                logger.info("********** Create A Phoenix Connection");
            } else {
                connection = connections.poll();
                logger.info("********** Get Phoenix JDBC Connection From Connection Pool.");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return connection;
    }

    public void releaseConnection(Connection connection) {
        connections.push(connection);
    }

}
