package com.example.dev_140_1_authentication;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionPool {
    private static ConnectionPool pool;
    private List<Connection> freeCon;
    private List<Connection> busyCon;
    private int size;
    private Properties properties = new Properties();

    private ConnectionPool() throws SQLException, IOException {
        freeCon = new ArrayList<>(size);
        busyCon = new ArrayList<>();
        size = 5;
        try (FileInputStream fileInputStream = new FileInputStream("application.properties")) {
            properties.load(fileInputStream);
            for (int i = 0; i < size; i++) {
                freeCon.add(DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password")));
            }
        }
    }

    public synchronized static ConnectionPool getInstance() throws SQLException, IOException {
        if (pool == null) pool = new ConnectionPool();
        return pool;
    }

    public Connection getConnection() throws SQLException {
        checkFreeConnection();
        Connection connection = freeCon.remove(freeCon.size() - 1);
        busyCon.add(connection);
        return connection;
    }

    public void resiveConnection(Connection connection) throws SQLException {
        busyCon.remove(connection);
        freeCon.add(connection);
        deleteConnection();
    }

    private void checkFreeConnection() throws SQLException {
        if (freeCon.size() < 2) {
            for (int i = 1; i < size; i++) {
                freeCon.add(DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password")));
            }
        }
    }

    private void deleteConnection() throws SQLException {
        if (freeCon.size() > size) {
            int iter = freeCon.size() - size;
            for (int i = 0; i < iter; i++) {
                freeCon.remove(i).close();
            }
        }
    }
}
