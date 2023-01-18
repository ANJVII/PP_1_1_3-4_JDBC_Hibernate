package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Иван", "Иванов", (byte) 34);
        service.saveUser("Петя", "Петров", (byte) 24);
        service.saveUser("Маша", "Иванова", (byte) 20);
        service.saveUser("Света", "Петрова", (byte) 27);
        for(User user : service.getAllUsers()) {
            System.out.println(user);
        }
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
