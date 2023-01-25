package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        // Реализация через Hibernate
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Иван", "Иванов", (byte) 34);
        service.saveUser("Петя", "Петров", (byte) 24);
        service.saveUser("Маша", "Иванова", (byte) 20);
        service.saveUser("Света", "Петрова", (byte) 27);
        for (User user : service.getAllUsers()) {
            System.out.println(user);
        }
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
