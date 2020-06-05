package org.levelup.chat.dao;

import org.levelup.chat.domain.Users;

import java.util.Collection;

public interface UsersDao {

    public Collection<Users> findAllUsers();
    public Users findByLogin(String login);
}
