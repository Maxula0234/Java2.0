package org.levelup.chat.dao;

import org.levelup.chat.domain.Users;

import java.util.Collection;

public interface UsersDao {

    Collection<Users> findAllUsers();
    Users findByLogin(String login);
    void removeById(int id);
    public Users createUsers(String firstName,String lastName);
}
