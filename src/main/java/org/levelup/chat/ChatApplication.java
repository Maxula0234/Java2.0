package org.levelup.chat;

import lombok.SneakyThrows;
import org.levelup.chat.dao.ChannelDao;
import org.levelup.chat.dao.impl.HibernateChannelDao;
import org.levelup.chat.domain.Channel;
import org.levelup.chat.hibernate.HibernateUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;

public class ChatApplication {

    @SneakyThrows
    public static void main(String[] args) {
        ChannelDao channelDao = new HibernateChannelDao();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter channel name:");
        String name = reader.readLine();

        Channel channel = channelDao.findByName(name);
        System.out.println(channel);

// findAll
//        Collection<Channel> allChannels = channelDao.findAll();
//        for (Channel channel : allChannels) {
//            System.out.println(channel);
//        }

// createChannel
//        System.out.println("Enter channel name:");
//        String name = reader.readLine();
//
//        System.out.println("Enter channel display name:");
//        String displayName = reader.readLine();
//
//        Channel channel = channelDao.createChannel(name, displayName);
//        System.out.println(channel);

        HibernateUtils.getFactory().close();
    }

}
