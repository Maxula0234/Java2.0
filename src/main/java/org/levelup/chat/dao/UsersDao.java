package org.levelup.chat.dao;

import org.levelup.chat.domain.User;

import java.io.IOException;
import java.util.Collection;

public interface UsersDao {

    Collection<User> findAllUsers();
    User findByLogin() throws IOException;
    void removeById(int id) throws IOException;
    void removeById()throws IOException;
    User createUsers() throws IOException;
    User findById(int id) throws IOException;
    User checkLogin(String login);
    User findByIdNew() throws IOException;
    User updateUser(Integer id, String firstName, String lastName, String login) throws IOException;
    User updateFirstNamUser(Integer id, String firstName) throws IOException;
    User updateLastNameUser(Integer id, String lastName) throws IOException;
    User updateLoginUser(Integer id, String login) throws IOException;
}
