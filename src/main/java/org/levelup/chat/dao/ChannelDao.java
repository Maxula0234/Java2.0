package org.levelup.chat.dao;

import org.levelup.chat.domain.Channel;
import org.levelup.chat.domain.ChannelDetails;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface ChannelDao {

    // method name "create..." -> метод должен вернуть то, что он создал
    Channel createChannel(String name, String displayName) throws IOException, IllegalAccessException;
    Collection<Channel> findAll();
    void removeByName(String name);
    Channel findByName(String name);
    Channel findById(Integer id);
    Channel updateChannel(Integer id,String name,String displayName);
    void addUserToChannel(Integer channelId, Integer userId);
}
