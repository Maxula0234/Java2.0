package org.levelup.chat.dao;

import org.levelup.chat.domain.Message;

import java.io.IOException;

public interface MessageDao {
    Message getMessageById() throws IOException;
    Message getMessageById(Integer id);
    Message updateMessageById(Integer id,String text);
}
