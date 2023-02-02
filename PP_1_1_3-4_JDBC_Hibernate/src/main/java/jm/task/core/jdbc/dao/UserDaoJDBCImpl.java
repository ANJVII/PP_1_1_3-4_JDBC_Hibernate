package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = new Util().getConnection();

    public UserDaoJDBCImpl() { //пустой конструктор
    }

    public void createUsersTable() {
        String creatTable = "create table if not exists users\n" +
                "(\n" +
                "    id       BIGINT auto_increment\n" +
                "        primary key,\n" +
                "    name     VARCHAR(20) not null,\n" +
                "    lastName VARCHAR(50) null,\n" +
                "    age      TINYINT     null\n" +
                ");";
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute(creatTable);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        String drop = "drop table if exists users";
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate(drop);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String selectUser = "insert into users (name, lastName, age) values(?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectUser)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        String remove = "delete from users where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(remove)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String select = "select * from users";
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(true);
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanTable = "delete from users";
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute(cleanTable);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
