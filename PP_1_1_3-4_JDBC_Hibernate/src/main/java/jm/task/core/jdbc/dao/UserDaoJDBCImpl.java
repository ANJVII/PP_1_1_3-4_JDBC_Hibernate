package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util util = new Util();
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
        try(Statement statement = util.getConnection().createStatement()) {
            statement.execute(creatTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String drop = "drop table users";
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(drop);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String selectUser = String.format("insert into users (name, lastName, age) values('%s', '%s', %d)", name, lastName, age);
        try(Statement statement = util.getConnection().createStatement()) {
            statement.execute(selectUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        String remove = String.format("delete from users where id = %d", id);
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(remove);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String select = "select * from users";
        try(Statement statement = util.getConnection().createStatement()) {
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
        try(Statement statement = util.getConnection().createStatement()) {
            statement.execute(cleanTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
