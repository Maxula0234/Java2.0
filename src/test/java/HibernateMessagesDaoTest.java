import Stub.StubSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.chat.dao.impl.HibernateChannelDao;
import org.levelup.chat.dao.impl.HibernateMessagesDao;
import org.levelup.chat.domain.Channel;
import org.levelup.chat.domain.User;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HibernateMessagesDaoTest {

    private HibernateMessagesDao messagesDao;
    private SessionFactory factory;

   @BeforeEach
    public void setupMocks(){
       //mock object
       //все методы которые имеют тип возвращаемого (не null)
       // то они возвращают значения по умолчанию
       factory = Mockito.mock(SessionFactory.class);
       messagesDao = new HibernateMessagesDao(factory);
   }

   @Test
    public void testSentMessage(){
       Session session = Mockito.mock(Session.class);
       Transaction transaction = Mockito.mock(Transaction.class);
       Mockito.when(factory.openSession()).thenReturn(session);
       Mockito.when(session.beginTransaction()).thenReturn(transaction);

       messagesDao.sendMessage(new User(), new Channel(),"some text");
   }
}