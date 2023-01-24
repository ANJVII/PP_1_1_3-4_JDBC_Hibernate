package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private final String USERNAME = "root123";
    private final String PASSWORD = "Root123!";

    private Connection connection;
    private SessionFactory sessionFactory;

    public Util() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Hibernate
        Properties properties = new Properties();
        properties.setProperty("connection.url", URL);
        properties.setProperty("connection.username", USERNAME);
        properties.setProperty("connection.password", PASSWORD);

        properties.setProperty("connection.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("show_sql", "true");
        properties.setProperty("format_sql", "true");

        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);
        sessionFactory = configuration.buildSessionFactory();

    }
    public Connection getConnection() {
        return connection;
    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
