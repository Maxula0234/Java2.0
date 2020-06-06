package org.levelup.chat.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.chat.dao.UsersDao;
import org.levelup.chat.domain.Channel;
import org.levelup.chat.domain.Users;
import org.levelup.chat.hibernate.HibernateUtils;
import org.levelup.chat.jdbc.DatabaseConnectionFactory;

import javax.persistence.criteria.CriteriaDelete;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        Collection<Users> allUsers = session.createQuery("from Users", Users.class).getResultList();
        session.close();
        return allUsers;
    }

    @Override
    public Users findByLogin(String login) {
        try (Session session = factory.openSession()){
            List<Users> users = session.createQuery("from Users where login = :login", Users.class)
                    .setParameter("login",login)
                    .getResultList();
            return users.isEmpty() ? null : users.get(0);
        }
    }

    @Override
    public void removeById(int id) {
        try(Session session = factory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE Users where id = :idUsers")
                    .setParameter("idUsers",id)
                    .executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public Users createUsers(String firstName, String lastName) {
        try (Session session = factory.openSession()){
            Transaction transaction = session.beginTransaction();
            Users user = new Users();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            String login = firstName.charAt(0) + lastName;
            user.setLogin(login.toLowerCase());
            session.persist(user); // insert into channel, add new row into table "channel"
            transaction.commit();
            session.close(); // close connection to database
            return user;
        }
    }
}
