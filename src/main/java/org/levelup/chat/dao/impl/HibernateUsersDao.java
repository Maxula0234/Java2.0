package org.levelup.chat.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.levelup.chat.dao.UsersDao;
import org.levelup.chat.domain.Users;
import org.levelup.chat.hibernate.HibernateUtils;

import java.util.Collection;
import java.util.List;

public class HibernateUsersDao implements UsersDao {
    private final SessionFactory factory;

    public HibernateUsersDao() {
        this.factory = HibernateUtils.getFactory();
    }
    @Override
    public Collection<Users> findAllUsers() {
        Session session = factory.openSession();
        Collection<Users> allUsers = session.createQuery("from Users",Users.class).getResultList();
        session.close();
        return allUsers;
    }

    @Override
    public Users findByLogin(String login) {
        try (Session session = factory.openSession()){
            List<Users> users = session.createQuery("from Users where login = :login",Users.class)
                    .setParameter("login",login)
                    .getResultList();
            return users.isEmpty() ? null : users.get(0);
        }

    }


}
