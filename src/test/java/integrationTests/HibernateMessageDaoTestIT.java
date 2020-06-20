package integrationTests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.levelup.chat.dao.impl.HibernateMessagesDao;
import org.levelup.chat.domain.Channel;
import org.levelup.chat.domain.Message;
import org.levelup.chat.domain.User;
import org.opentest4j.AssertionFailedError;

import java.util.List;

public class HibernateMessageDaoTestIT {
    private static SessionFactory factory;
    private static HibernateMessagesDao dao;

    @BeforeAll//будет вызван один раз в начале теста
    public static void setUpObjects() {
        factory = HibernateUtilsTest.getSessionFactory();
        dao = new HibernateMessagesDao(factory);
    }

    @Test
    public void testSendMessage_whenDataIsValide_thenCreateMessageInDB() {
        User user = createUser("login1", "firstName1", "lastName1");
        Channel channel = createChannel("channel1", "CH1");
        dao.sendMessage(user, channel, "some message");
        Session session = factory.openSession();
        List<Message> messages = session.createQuery("from Message", Message.class)
                .getResultList();
        Message result = messages.stream().filter(message ->
                message.getUser().getLogin().equals(user.getLogin()) &&
                        message.getChannel().getName().equals(channel.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionFailedError("Message was't created."));
        Assertions.assertEquals("some message", result.getText());
    }

    private User createUser(String login, String firstName, String lastName) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            User user = new User(login, firstName, lastName);
            session.persist(user);
            tx.commit();
            return user;
        }
    }

    private Channel createChannel(String name, String displayName) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Channel channel = new Channel();
            channel.setName(name);
            channel.setDisplayName(displayName);
            session.persist(channel);
            tx.commit();
            return channel;
        }
    }
}

