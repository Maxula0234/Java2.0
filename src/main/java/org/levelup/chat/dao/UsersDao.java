package org.levelup.chat.dao;

import org.levelup.chat.domain.Users;

import java.io.IOException;
import java.util.Collection;

public interface UsersDao {

    Collection<Users> findAllUsers();
    Users findByLogin(String login) throws IOException;
    void removeById(int id);
    Users createUsers(String firstName,String lastName);
    Users findById(int id);
}
