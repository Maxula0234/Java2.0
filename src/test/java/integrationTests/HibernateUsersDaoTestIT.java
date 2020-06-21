package integrationTests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.levelup.chat.dao.impl.HibernateUsersDao;
import org.levelup.chat.domain.User;

import java.io.IOException;

class HibernateUsersDaoTestIT {
    private static SessionFactory factory;
    private static HibernateUsersDao dao;

    @BeforeAll//будет вызван один раз в начале теста
    public static void setUpObjects() {
        factory = HibernateUtilsTest.getSessionFactory();
        dao = new HibernateUsersDao(factory);
    }

    @Test
    @DisplayName("findByLogin")
    public void testFindUserFromLogin() throws IOException {
        User us1 = createUser("test", "Pass", "ptest");
        User user = dao.findByLogin("test");
        Assertions.assertTrue(user != null);
    }

    @Test
    @DisplayName("findById")
    public void testFindByid() throws IOException {
        User user = createUser("findById", "byId", "find");
        User us = dao.findById(user.getId());
        Assertions.assertTrue(us != null);
    }

    @Test
    @DisplayName("findByIdNegative")
    public void testFindByidNeg() throws IOException {
        User user = createUser("findById", "byId", "find");
        User us = dao.findById(123);
        Assertions.assertTrue(us == null);
    }

    @Test
    @DisplayName("checkLogin")
    public void testCheckLoginPositive() {
        User user = createUser("findById", "byId", "find");
        User check = dao.checkLogin(user.getLogin());
        Assertions.assertTrue(check != null);
    }

    @Test
    @DisplayName("checkLoginN")
    public void testCheckLoginNegative() {
        User check = dao.checkLogin("test");
        Assertions.assertTrue(check == null);
    }

    @Test
    @DisplayName("removeByIdPositive")
    public void testRemoveByIdPositive() throws IOException {
        User user = createUser("test", "max", "mTesr");
        Assertions.assertTrue(user != null);
        dao.removeById(user.getId());
        User checkRemove = dao.findById(user.getId());
        Assertions.assertTrue(checkRemove == null);
    }

    @Test
    @DisplayName("removeByIdNegative")
    public void testRemoveByIdNegative() throws IOException {
        User user = createUser("test", "max", "mTesr");
        Assertions.assertTrue(user != null);
        dao.removeById(111);
        User check = dao.findById(user.getId());
        Assertions.assertTrue(check != null);
    }

    @Test
    @DisplayName("createUserPositive")
    public void testCreateUserPositive() throws IOException {
        User user = dao.createUsers("mkhor", "Maks", "Khor");
        User check = dao.findById(user.getId());
        Assertions.assertTrue(check != null);
    }

    @Test
    @DisplayName("updateUserFirstName")
    public void testUpdateUserFirst() throws IOException {
        User user = dao.createUsers("Maks", "Khor", "mkhor");
        Assertions.assertTrue(user != null);
        User check = dao.updateUser(user.getId(),"Maskim","Khor","mkhor");
        Assertions.assertTrue(check != null);
        Assertions.assertTrue(check.getFirstName().contains("Maskim"));
    }

    @Test
    @DisplayName("updateUserLastName")
    public void testUpdateUserLast() throws IOException {
        User user = dao.createUsers("Maks", "Khor", "mkhor");
        Assertions.assertTrue(user != null);
        User check = dao.updateUser(user.getId(),"Maks","Khorovinkin","mkhor");
        Assertions.assertTrue(check != null);
        Assertions.assertTrue(check.getLastName().contains("Khorovinkin"));
    }

    @Test
    @DisplayName("updateUserLogin")
    public void testUpdateUserLogin() throws IOException {
        User user = dao.createUsers("Maks", "Khor", "mkhor");
        Assertions.assertTrue(user != null);
        User check = dao.updateUser(user.getId(),"Maks","Khor","kkk");
        Assertions.assertTrue(check != null);
        Assertions.assertTrue(check.getLogin().contains("kkk"));
    }

    public User createUser(String login, String firstName, String lastName) {
        try (Session session = factory.openSession()) {
            Transaction tr = session.beginTransaction();
            User user = new User(login, firstName, lastName);
            session.persist(user);
            tr.commit();
            return user;
        }
    }
}