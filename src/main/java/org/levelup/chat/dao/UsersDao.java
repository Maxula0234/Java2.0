package org.levelup.chat.dao;

import org.levelup.chat.domain.Channel;
import org.levelup.chat.domain.Password;
import org.levelup.chat.domain.User;
import org.levelup.chat.domain.UserChannel;

import java.io.IOException;
import java.util.Collection;

public interface UsersDao {

    Collection<User> findAllUsers();
    User findByLogin(String login) throws IOException;
    void removeById(int id) throws IOException;
    void removeById()throws IOException;
    User createUsers(String firstName, String lastName, String login) throws IOException;
    User findById(int id) throws IOException;
    User checkLogin(String login);
    User findByIdNew() throws IOException;
    User updateUser(Integer id, String firstName, String lastName, String login) throws IOException;
    User updateFirstNamUser(Integer id, String firstName) throws IOException;
    User updateLastNameUser(Integer id, String lastName) throws IOException;
    User updateLoginUser(Integer id, String login) throws IOException;
    //Креды
    Password addUserToChat(Integer userId,String password);
    void removeUserFromChat(Integer userId);
    Password findUserIdFromPassword(Integer userId);
    Password updatePasswordFromUser(Integer userId,String oldPassword, String newPassword);
    //Авторизация
    User loginToChat(String login,String password);


    Collection<UserChannel> allUserChannels(Integer userId);
}
