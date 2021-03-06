package org.levelup.chat.dao.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.levelup.chat.dao.ChannelDao;
import org.levelup.chat.domain.Channel;
import org.levelup.chat.domain.User;
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

@RequiredArgsConstructor
public class HibernateChannelDao implements ChannelDao {

    private final SessionFactory factory;

    @Override
    public Channel createChannel(String name, String displayName) throws IllegalAccessException {
        Session session = factory.openSession(); // open new connection to database
        Channel channel = new Channel();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // Insert, update, delete
        Channel checkName = findByName(name);
        if (checkName == null) {
            Transaction transaction = session.beginTransaction();
            channel.setName(name);
            channel.setDisplayName(displayName);
            session.persist(channel); // insert into channel, add new row into table "channel"
            transaction.commit();
            session.close(); // close connection to database
            System.out.println(String.format("[log] Создан клиент: name = [%s], displayNam = [%s]",
                    channel.getName(), channel.getDisplayName()));
        } else {
            throw new IllegalAccessException("Канал с данным именем уже существует.");
        }
        return channel;
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
                channel.getName(), channel.getDisplayName())));
        return allChannels;
    }

    @Override
    public Channel findByName(String name) {
        try (Session session = factory.openSession()) {
            // SQL:  select * from channel where name =
            List<Channel> channels = session.createQuery("from Channel where name = :channelName", Channel.class)
                    .setParameter("channelName", name)
                    .getResultList();
            if (channels.isEmpty()) {
                System.out.println(String.format("[log] Channel с name = [%s] не найден.", name));
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

    @Override
    public Channel findById(Integer id) {
        Channel channel;
        try (Session session = factory.openSession()) {
            channel = session.get(Channel.class, id);
            System.out.println(String.format("Найден канал - [id = %s],[name = %s],[displayName = %s]", channel.getId(), channel.getName(), channel.getDisplayName()));
        }
        return channel;
    }

    @Override
    public Channel updateChannel(Integer id, String name, String displayName) {
        Channel channel;
        try (Session session = factory.openSession()) {
            channel = session.get(Channel.class, id);

            String nameOld = channel.getName();
            String displayNameOld = channel.getDisplayName();

            Transaction t = session.beginTransaction();
            channel.setName(name);
            channel.setDisplayName(displayName);

            channel = (Channel) session.merge(channel);
            System.out.println(String.format("Обновили канал [id = %s],[name = %s],[displayName = %s]",
                    channel.getId(), channel.getName(), channel.getDisplayName()));
            System.out.println(String.format("Старые данные [id = %s],[name = %s],[displayName = %s]",
                    id, nameOld, displayNameOld));
            t.commit();
        }
        return channel;
    }

    @Override
    public void addUserToChannel(Integer channelId, Integer userId) {
        try (Session session = factory.openSession()) {
            //ACID
            //Атомарность
            //Консистентность (Согласованность) - валидация - в бд должно остаться те ограничения, которые установленный в бд и таблице
            // Изоляция ()
            // Стойкость (Durability)
            Transaction transaction = session.beginTransaction();
            Channel channel = session.get(Channel.class, channelId);
            User user = new User();
            user.setId(userId);
            channel.getUsers().add(user);
            session.merge(channel);
            transaction.commit();
        }
    }


}
