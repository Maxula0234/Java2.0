package org.levelup.chat.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.chat.dao.UsersDao;
import org.levelup.chat.domain.Users;
import org.levelup.chat.hibernate.HibernateUtils;

import javax.swing.table.AbstractTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
        allUsers.stream()
                .forEach(s -> System.out.println(String.format("[log] [firstName - %s], [lastName - %s], [login - %s]",
                        s.getFirstName(), s.getLastName(), s.getLogin())));
        return allUsers;
    }

    @Override
    public Users findByLogin(String login) throws IOException{
        try (Session session = factory.openSession()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            AtomicReference<Users> user = new AtomicReference<>();
            List<Users> users = session.createQuery("from Users where login = :login", Users.class)
                    .setParameter("login", login)
                    .getResultList();
            if (users.size() == 0) {
                System.out.println(String.format("Клиент с login = [%s] не найден", login));
                System.out.println("Хотите попробовать найти другой логин? - y/n");
                String yn = reader.readLine().toLowerCase();
                switch (yn){
                    case "y":{
                        System.out.println("Введите новый логин - ");
                        String newLogin = reader.readLine();
                        findByLogin(newLogin);
                    }
                    case "n":{
                        return null;
                    }
                }
            } else {
                user.set(users.get(0));
                System.out.println(String.format("Клиент найден. [login - %s],[firstName - %s],[lastName - %s]",
                        user.get().getLogin(),user.get().getFirstName(),user.get().getLastName()));
            }
            return user.get();
        }
    }

    @Override
    public Users findById(int id) {
        try (Session session = factory.openSession()) {
            List<Users> users = session.createQuery("from Users where id = :id", Users.class)
                    .setParameter("id", id)
                    .getResultList();
            if (users.isEmpty()) {
                System.out.println(String.format("Клиент с id = [%s] не найден", id));
            } else {
                System.out.println("Клиент найден");
            }
            return users.isEmpty() ? null : users.get(0);
        }
    }

    @Override
    public void removeById(int id) {
        try (Session session = factory.openSession()) {
            Users user = findById(id);
            if (user != null) {
                Transaction transaction = session.beginTransaction();
                session.createQuery("DELETE Users where id = :idUsers")
                        .setParameter("idUsers", id)
                        .executeUpdate();
                transaction.commit();
                System.out.println(String.format("[log] Удалили пользователя: " +
                        "firstName = [%s], " +
                        "lastName = [%s], " +
                        "login = [%s]", user.getFirstName(), user.getLastName(), user.getLogin()));
            } else {
                System.out.println(String.format("Клиент с id -  [%s] не найден", id));
            }

        }
    }

    @Override
    public Users createUsers(String firstName, String lastName) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Users user = new Users();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            String login = firstName.charAt(0) + lastName;
            user.setLogin(login.toLowerCase());
            session.persist(user); // insert into channel, add new row into table "channel"
            transaction.commit();
            session.close(); // close connection to database
            System.out.println(String.format("Создан клиент: firstName = [%s], lastName = [%s], login = [%s]",
                    user.getFirstName(), user.getLastName(), user.getLogin()));
            return user;
        }
    }
}
