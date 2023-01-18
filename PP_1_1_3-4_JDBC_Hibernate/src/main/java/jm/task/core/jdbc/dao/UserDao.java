package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

public interface UserDao {
    void createUsersTable();
        //создание таблицы
    void dropUsersTable();
        //удаление таблицы
    void saveUser(String name, String lastName, byte age);
        //добавление пользователя
    void removeUserById(long id);
        //удаление пользователя по id
    List<User> getAllUsers();
        //получить список пользователей
    void cleanUsersTable();
    //очистить таблицу пользователей
}
