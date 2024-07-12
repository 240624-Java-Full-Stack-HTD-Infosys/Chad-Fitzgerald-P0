package org.revature.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static Connection getConnection() throws SQLException, IOException {
        // load the properties from application.properties to avoid hard-coding credentials
        // the class loader accesses the "resources" which are files on the classpath
        // use a Properties object which can parse our key/value pairs; ask for key, get the value
        InputStream inputStream = ConnectionUtil.class.getClassLoader().getResourceAsStream("application.properties");
        Properties props = new Properties();
        props.load(inputStream);

        // we have our properties and can now use these to establish a connection
//        Class.forName("org.postgresql.Main");

        // we now have a connection to our DB, we can use it to create statements to execute
        return DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"), props.getProperty(("password")));
    }
}
