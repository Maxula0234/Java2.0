package org.levelup.chat.dao;

import org.levelup.chat.domain.Channel;

import java.util.Collection;

public interface ChannelDao {

    // method name "create..." -> метод должен вернуть то, что он создал
    Channel createChannel(String name, String displayName);

    Collection<Channel> findAll();

    Channel findByName(String name);

}
