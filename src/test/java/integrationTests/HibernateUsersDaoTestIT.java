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
import java.util.List;

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
        User us1 = createUser("test","Pass","ptest");
        User user = dao.findByLogin("test");
        Assertions.assertTrue(user != null);
    }

    @Test
    @DisplayName("findById")
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