package jm.task.core.jdbc;


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
        // Реализация через jdbc
        UserService service = new UserServiceImpl();            // по идее открытие транзакции надо делать здесь
        service.createUsersTable();                             // и выполнять пачку запросов, но я не знаю как (новый метод?)
        service.saveUser("Иван", "Иванов", (byte) 34);
        service.saveUser("Петя", "Петров", (byte) 24);
        service.saveUser("Маша", "Иванова", (byte) 20);
        service.saveUser("Света", "Петрова", (byte) 27);
        for (User user : service.getAllUsers()) {
            System.out.println(user);
        }
        service.cleanUsersTable();
        service.dropUsersTable();

        // Hibernate (пока что не работает:с)
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        try (Session session =  new Util().getSessionFactory().openSession()) {
            session.beginTransaction();
            System.out.println("OK");
            User user = new User("Name", "LastName", (byte) 33);
            session.save(user);
            session.getTransaction().commit();
        }
    }
}
