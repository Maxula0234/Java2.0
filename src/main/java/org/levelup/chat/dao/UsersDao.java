package org.levelup.chat.dao;

import org.levelup.chat.domain.Users;

import java.io.IOException;
import java.util.Collection;

public interface UsersDao {

    Collection<Users> findAllUsers();
    Users findByLogin() throws IOException;
    void removeById(int id) throws IOException;
    void removeById()throws IOException;
    Users createUsers() throws IOException;
    Users findById(int id) throws IOException;
    Users checkLogin(String login);
    Users findByIdNew() throws IOException;
    Users updateUser(Integer id, String firstName,String lastName,String login) throws IOException;
    Users updateFirstNamUser(Integer id, String firstName) throws IOException;
    Users updateLastNameUser(Integer id, String lastName) throws IOException;
    Users updateLoginUser(Integer id, String login) throws IOException;
}
