package org.levelup.chat.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.chat.dao.ChannelDao;
import org.levelup.chat.dao.ChannelDetailsDao;
import org.levelup.chat.domain.Channel;
import org.levelup.chat.domain.ChannelDetails;
import org.levelup.chat.hibernate.HibernateUtils;

public class HibernateChannelDetailsDao implements ChannelDetailsDao {
    private final SessionFactory factory;
    public HibernateChannelDetailsDao() {
        this.factory = HibernateUtils.getFactory();
    }

    @Override
    public ChannelDetails appendDetails(Integer channelId, int peopleCount, String description) {
        try (Session session = factory.openSession()){
            Transaction transaction = session.beginTransaction();
            Channel channel = session.get(Channel.class,channelId);
            ChannelDetails details = new ChannelDetails();
            details.setChannelId(channelId);
            details.setChannel(channel);
            details.setDescription(description);
            details.setPeopleCount(peopleCount);

            session.persist(details);

            transaction.commit();
            return details;
        }
    }
}
