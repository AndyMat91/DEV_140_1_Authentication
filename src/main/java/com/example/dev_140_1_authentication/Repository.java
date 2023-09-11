package com.example.dev_140_1_authentication;

import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class Repository {
    public Set<Users> findAllUsers() throws SQLException, IOException {
        List<Users> users = new LinkedList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(getAllUsersWithResultSet(rs));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) ConnectionPool.getInstance().resiveConnection(connection);
        }
        return new HashSet<>(users);
    }


    private Users getAllUsersWithResultSet(ResultSet resultSet) throws SQLException {
        Users users = null;
        int id = resultSet.getInt(1);
        String name = resultSet.getString(2);
        String password = resultSet.getString(3);
        users = new Users(id, name, password);
        return users;
    }
}
