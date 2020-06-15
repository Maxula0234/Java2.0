package org.levelup.chat.dao;

import org.levelup.chat.domain.Channel;
import org.levelup.chat.domain.Message;
import org.levelup.chat.domain.User;

import java.io.IOException;

public interface MessagesDao {
    Message getMessageById() throws IOException;
    Message getMessageById(Integer id);
    Message updateMessageById(Integer id, String text);
    void sendMessage(User user, Channel channel, String text);

}
