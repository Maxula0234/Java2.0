package org.levelup.chat.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.chat.dao.ChannelDao;
import org.levelup.chat.domain.Channel;
import org.levelup.chat.hibernate.HibernateUtils;

import java.util.Collection;
import java.util.List;

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

public class HibernateChannelDao implements ChannelDao {

    private final SessionFactory factory;

    public HibernateChannelDao() {
        this.factory = HibernateUtils.getFactory();
    }

    @Override
    public Channel createChannel(String name, String displayName) {
        Session session = factory.openSession(); // open new connection to database
        // Insert, update, delete
        Transaction transaction = session.beginTransaction();

        Channel channel = new Channel();
        channel.setName(name);
        channel.setDisplayName(displayName);
        session.persist(channel); // insert into channel, add new row into table "channel"

        transaction.commit();
        session.close(); // close connection to database

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
        return allChannels;
    }

    @Override
    public Channel findByName(String name) {
        try (Session session = factory.openSession()) {
            // SQL:  select * from channel where name = ?
            List<Channel> channels = session.createQuery("from Channel where name = :channelName", Channel.class)
                    .setParameter("channelName", name)
                    .getResultList();
            return channels.isEmpty() ? null : channels.get(0);
        }
    }

}
