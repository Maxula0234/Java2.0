package integrationTests;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.levelup.chat.dao.impl.HibernateChannelDao;
import org.levelup.chat.domain.Channel;


class HibernateChannelDaoTestIT {

    private static SessionFactory factory;
    private static HibernateChannelDao dao;

    @BeforeAll//будет вызван один раз в начале теста
    public static void setUpObjects() {
        factory = HibernateUtilsTest.getSessionFactory();
        dao = new HibernateChannelDao(factory);
    }


    @Test
    public void testFindByName_whenFind_thenFindChannel() throws IllegalAccessException {
        Channel channel = createChannel("test", "T");
        Channel ch = dao.findByName("test");
        Assertions.assertTrue(ch != null);
    }

    @Test
    public void testCreat_whenCreate_thenCreatChannel() throws IllegalAccessException {
        Channel ch = dao.createChannel("testik", "Tk");
        Channel checkChannel = dao.findByName("testik");
        Assertions.assertTrue(checkChannel != null);
    }

    public Channel createChannel(String name, String displayName) throws IllegalAccessException {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Channel channel = new Channel(name, displayName);
            session.persist(channel);
            tx.commit();
            return channel;
        }
    }


}