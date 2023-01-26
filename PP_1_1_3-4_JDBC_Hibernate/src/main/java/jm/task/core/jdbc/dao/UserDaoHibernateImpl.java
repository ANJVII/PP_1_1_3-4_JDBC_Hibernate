package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory = new Util().getSessionFactory();

    public UserDaoHibernateImpl() { //пустой конструктор
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            if (transaction.getStatus().equals(TransactionStatus.NOT_ACTIVE)) {  //проверка в необходимости запуска
                transaction.begin();                                             //транзакции
            }
            session.createSQLQuery("create table if not exists users\n" +
                    "(\n" +
                    "    id       BIGINT auto_increment\n" +
                    "        primary key,\n" +
                    "    name     VARCHAR(20) not null,\n" +
                    "    lastName VARCHAR(50) null,\n" +
                    "    age      TINYINT     null\n" +
                    ");").executeUpdate();
            System.out.println(transaction.getStatus());
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            if (transaction.getStatus().equals(TransactionStatus.NOT_ACTIVE)) {
                transaction.begin();
            }
            session.createSQLQuery("drop table if exists users").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            if (transaction.getStatus().equals(TransactionStatus.NOT_ACTIVE)) {
                transaction.begin();
            }
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            if (transaction.getStatus().equals(TransactionStatus.NOT_ACTIVE)) {
                transaction.begin();
            }
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            users = session.createQuery("select a from User a", User.class).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            if (transaction.getStatus().equals(TransactionStatus.NOT_ACTIVE)) {
                transaction.begin();
            }
            session.createQuery("delete from User a").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
