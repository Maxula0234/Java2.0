import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.chat.dao.impl.HibernateChannelDao;
import org.levelup.chat.domain.Channel;
import org.mockito.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

class HibernateChannelDaoTest {

    @Mock // == factoty = Mockito.mock(SessionFactory.class)
    private SessionFactory factory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @InjectMocks // new HibernateChannelDao(factory)
    private HibernateChannelDao dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(factory.openSession()).thenReturn(session);
        Mockito.when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void testCreateChannelWithNameExists_() throws IOException {
        Query query = Mockito.mock(Query.class);
        Mockito.when(session.createQuery("from Channel where name = :channelName", Channel.class))
                .thenReturn(query);

        Mockito.when(query.setParameter(ArgumentMatchers.eq("channelName"), ArgumentMatchers.any(String.class)))
                .thenReturn(query);

        List<Channel> channels = Collections.singletonList(new Channel());

        Mockito.when(query.getResultList()).thenReturn(channels);

        Assertions.assertThrows(IllegalAccessException.class, () -> dao.createChannel("someone", "displayName"));
    }
    @Test
    public void testCreateChannel_whenChannelNameExistsAndUsingSpy_thenThrowException(){
        //spy objects - реальный объект , у которого можно переопределить поведение некоторых методов/

        HibernateChannelDao spyDao = Mockito.spy(dao);
        Mockito.doReturn(new Channel()).when(spyDao).findByName("someone");
        Mockito.when(spyDao.findByName("someone")).thenReturn(new Channel());
        Assertions.assertThrows(IllegalAccessException.class,()-> spyDao.createChannel("someone","display name"));
        Mockito.verify(factory).openSession();
    }

}