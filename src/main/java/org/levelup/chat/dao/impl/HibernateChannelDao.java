package org.levelup.chat.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.chat.dao.ChannelDao;
import org.levelup.chat.domain.Channel;
import org.levelup.chat.domain.Users;
import org.levelup.chat.hibernate.HibernateUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

// DAO - Data Access Object
//  -> 5 methods - CRUD (create, read, update, delete)
//          -> create
//          -> update
//          -> delete
//          -> findById (getById)
//          -> findAll (getAll)

// Repository
//  -> find data in database
//      -> findByName (getByName)
//      -> findByField (getByField)

public class
HibernateChannelDao implements ChannelDao {

    private final SessionFactory factory;

    public HibernateChannelDao() {
        this.factory = HibernateUtils.getFactory();
    }

    @Override
    public Channel createChannel(String name, String displayName) throws IOException {
        Session session = factory.openSession(); // open new connection to database
        AtomicReference<Channel> channel = new AtomicReference<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // Insert, update, delete
        Channel checkName = findByName(name);
        if (checkName == null){
            Transaction transaction = session.beginTransaction();
            channel.get().setName(name);
            channel.get().setDisplayName(displayName);
            session.persist(channel); // insert into channel, add new row into table "channel"
            transaction.commit();
            session.close(); // close connection to database
            System.out.println(String.format("[log] Создан клиент: name = [%s], displayNam = [%s]",
                    channel.get().getName(), channel.get().getDisplayName()));
        } else {
            System.out.println(String.format("Канал с именем [%s] уже существует.", name));
            System.out.println("[log] Введите новое имя канала - ");
            String nameChannel2 = reader.readLine();
            System.out.println("[log] Введите новое displayName канала - ");
            String displayName2 = reader.readLine().toLowerCase();
            createChannel(nameChannel2,displayName2);
        }
        return channel.get();
    }

    @Override
    public Collection<Channel> findAll() {
        Session session = factory.openSession();
        // HQL - Hibernate Query Language, similar to SQL
        // SQL: select * from channel
        // HQL: from Channel <- class name
        Collection<Channel> allChannels = session.createQuery("from Channel", Channel.class)
                .getResultList();
        session.close();
        allChannels.stream().forEach(channel -> System.out.println(String.format("[log] [name = %s],[displayName = %s]",
                channel.getName(),channel.getDisplayName())));
        return allChannels;
    }

    @Override
    public Channel findByName(String name) {
        try (Session session = factory.openSession()) {
            // SQL:  select * from channel where name =
            List<Channel> channels = session.createQuery("from Channel where name = :channelName", Channel.class)
                    .setParameter("channelName", name)
                    .getResultList();
            if (channels.isEmpty()){
                System.out.println(String.format("[log] Channel с name = [%s] не найден.",name));
            }
            return channels.isEmpty() ? null : channels.get(0);
        }
    }
    @Override
    public void removeByName(String name) {
        try (Session session = factory.openSession()) {
            Channel channel = findByName(name);
            if (channel != null) {
                Transaction transaction = session.beginTransaction();
                session.createQuery("DELETE Channel where name = :nameChannel")
                        .setParameter("nameChannel", name)
                        .executeUpdate();
                transaction.commit();
                System.out.println(String.format("[log] Удалили канал: " +
                        "name = [%s], " +
                        "DisplayName = [%s], "
                        , channel.getName(), channel.getDisplayName()));
            }
        }
    }

}
