package org.levelup.chat.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.chat.dao.UsersDao;
import org.levelup.chat.domain.Users;
import org.levelup.chat.hibernate.HibernateUtils;

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
                .forEach(s -> System.out.println(String.format("[log] [id = %s][firstName - %s], [lastName - %s], [login - %s]",
                        s.getId(), s.getFirstName(), s.getLastName(), s.getLogin())));
        return allUsers;
    }

    @Override
    public Users findByLogin() throws IOException {
        try (Session session = factory.openSession()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            AtomicReference<Users> user = new AtomicReference<>();
            System.out.println("Введите логин");
            String login = reader.readLine();
            List<Users> users = session.createQuery("from Users where login = :login", Users.class)
                    .setParameter("login", login)
                    .getResultList();
            if (users == null) {
                System.out.println(String.format("[log] Клиент с login = [%s] не найден", login));
                System.out.println("[log] Хотите попробовать найти другой логин? - y/n");
                String yn = reader.readLine().toLowerCase();
                switch (yn) {
                    case "y": {
                        System.out.println("[log] Введите новый логин - ");
                        String newLogin = reader.readLine();
                        findByLogin();
                    }
                    case "n": {
                        System.out.println("[log] Программа завершена....");
                        return null;
                    }
                }
            } else {
                user.set(users.get(0));
                System.out.println(String.format("[log] Клиент найден. [login - %s],[firstName - %s],[lastName - %s]",
                        user.get().getLogin(), user.get().getFirstName(), user.get().getLastName()));
            }
            return user.get();
        }
    }

    @Override
    public Users findById(int id) throws IOException {
        try (Session session = factory.openSession()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            AtomicReference<Users> user = new AtomicReference<>();
            List<Users> users = session.createQuery("from Users where id = :id", Users.class)
                    .setParameter("id", id)
                    .getResultList();
            if (users.isEmpty()) {
                System.out.println(String.format("Клиент с id = [%s] не найден", id));
                System.out.println("[log] Хотите попробовать найти другой id? - y/n");
                String yn = reader.readLine().toLowerCase();
                switch (yn) {
                    case "y": {
                        System.out.println("[log] Введите новый id(int) - ");
                        String newLogin = reader.readLine();
                        user.set(findById(Integer.parseInt(newLogin)));
                        break;
                    }
                    case "n": {
                        System.out.println("[log] Программа завершена....");
                        return null;
                    }
                }
            } else {
                user.set(users.get(0));
                System.out.println(String.format("Клиент найден - [id = %s],[firstName = %s],[lastName = %s],[login = %s].",
                        user.get().getId(), user.get().getFirstName(), user.get().getLastName(), user.get().getLogin()));
                return user.get();
            }
            return user.get();
        }
    }

    @Override
    public Users findByIdNew() throws IOException {
        try (Session session = factory.openSession()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter id");
            Integer id = Integer.parseInt(reader.readLine());
            AtomicReference<Users> user = new AtomicReference<>();
            Users user1 = session.get(Users.class, id);
            if (user1 == null) {
                System.out.println(String.format("Клиент с id = [%s] не найден", id));
                System.out.println("[log] Хотите попробовать найти другой id? - y/n");
                String yn = reader.readLine().toLowerCase();
                switch (yn) {
                    case "y": {
                        System.out.println("[log] Введите новый id(int) - ");
                        String newLogin = reader.readLine();
                        user.set(findById(Integer.parseInt(newLogin)));
                        break;
                    }
                    case "n": {
                        System.out.println("[log] Программа завершена....");
                        return null;
                    }
                }
            } else {
                user.set(user1);
                System.out.println(String.format("Клиент найден - [id = %s],[firstName = %s],[lastName = %s],[login = %s].",
                        user.get().getId(), user.get().getFirstName(), user.get().getLastName(), user.get().getLogin()));
                return user.get();
            }
            return user.get();
        }
    }

    @Override
    public Users checkLogin(String login) {
        try (Session session = factory.openSession()) {
            List<Users> users = session.createQuery("from Users where login = :login", Users.class)
                    .setParameter("login", login)
                    .getResultList();
            if (users.size() == 0) {
                System.out.println(String.format("[log] Клиент с login = [%s] свободен", login));
                return null;
            } else {
                System.out.println(String.format("[log] Клиент с login = [%s] занят", login));
            }
            return users.get(0);
        }
    }

    @Override
    public void removeById() throws IOException {
        try (Session session = factory.openSession()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Collection<Users> allUsers = findAllUsers();
            System.out.println("[log] Введите id пользователя, которого необходимо удалить.");
            String delId = reader.readLine();
            Users user = findById(Integer.parseInt(delId));
            if (user != null) {
                System.out.println(String.format("[log] Клиент найден: " +
                        "firstName = [%s], " +
                        "lastName = [%s], " +
                        "login = [%s]", user.getFirstName(), user.getLastName(), user.getLogin()));
                System.out.println("Вы уверены что хотите удалить? Введите y/n");
                String __case = reader.readLine().toLowerCase();
                switch (__case) {
                    case "y": {
                        Transaction transaction = session.beginTransaction();
                        session.createQuery("DELETE Users where id = :idUsers")
                                .setParameter("idUsers", Integer.parseInt(delId))
                                .executeUpdate();
                        System.out.println(String.format("[log] Удалили пользователя: " +
                                "firstName = [%s], " +
                                "lastName = [%s], " +
                                "login = [%s]", user.getFirstName(), user.getLastName(), user.getLogin()));
                        transaction.commit();
                        break;
                    }
                    case "n": {
                        System.out.println("Клиент не удален, программа завершена......");
                        break;
                    }
                }
            } else {
                System.out.println(String.format("Клиент с id -  [%s] не найден", delId));
            }
        }
    }

    @Override
    public void removeById(int id) throws IOException {
        try (Session session = factory.openSession()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Collection<Users> allUsers = findAllUsers();
            System.out.println("[log] Введите id пользователя, которого необходимо удалить.");
            String delId = reader.readLine();
            Users user = findById(Integer.parseInt(delId));
            if (user != null) {
                System.out.println(String.format("[log] Клиент найден: " +
                        "firstName = [%s], " +
                        "lastName = [%s], " +
                        "login = [%s]", user.getFirstName(), user.getLastName(), user.getLogin()));
                System.out.println("Вы уверены что хотите удалить? Введите y/n");
                String __case = reader.readLine().toLowerCase();
                switch (__case) {
                    case "y": {
                        Transaction transaction = session.beginTransaction();
                        session.createQuery("DELETE Users where id = :idUsers")
                                .setParameter("idUsers", id)
                                .executeUpdate();
                        transaction.commit();
                        System.out.println(String.format("[log] Удалили пользователя: " +
                                "firstName = [%s], " +
                                "lastName = [%s], " +
                                "login = [%s]", user.getFirstName(), user.getLastName(), user.getLogin()));
                        break;
                    }
                    case "n": {
                        System.out.println("Клиент не удален, программа завершена......");
                        break;
                    }
                }
            } else {
                System.out.println(String.format("Клиент с id -  [%s] не найден", id));
            }
        }
    }

    @Override
    public Users createUsers() throws IOException {
        try (Session session = factory.openSession()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            AtomicReference<Users> newUser = new AtomicReference<>();

            System.out.println("Введите имя.");
            String firstName = reader.readLine();
            System.out.println("Введите фамилию.");
            String lastName = reader.readLine();
            System.out.println("Введите login.");
            String login = reader.readLine();
            Users checkLogin = checkLogin(login.toLowerCase());
            if (checkLogin == null) {
                Transaction transaction = session.beginTransaction();
                Users user = new Users();
                user.setLastName(lastName);
                user.setFirstName(firstName);
                user.setLogin(login.toLowerCase());
                session.persist(user); // insert into channel, add new row into table "channel"
                transaction.commit();
                session.close(); // close connection to database
                newUser.set(user);
                System.out.println(String.format("Создан клиент: firstName = [%s], lastName = [%s], login = [%s]",
                        newUser.get().getFirstName(), newUser.get().getLastName(), newUser.get().getLogin()));
            } else {
                System.out.println("Попробовать снова? y/n");
                String yn = reader.readLine().toLowerCase();
                switch (yn) {
                    case "y": {
                        newUser.set(createUsers());
                        break;
                    }
                    case "n": {
                        System.out.println("Программа завершена.....");
                        return null;
                    }
                }
            }
            return newUser.get();
        }
    }

    @Override
    public Users updateUser(Integer id, String firstName, String lastName, String login) throws IOException {
        Users users;
        try (Session session = factory.openSession()) {
            users = session.get(Users.class, id);

            String firstNameOld = users.getFirstName();
            String lastNameOld = users.getLastName();
            String loginOld = users.getLogin();

            if (firstNameOld != firstName) {
                users.setFirstName(firstName);
            }
            if (lastNameOld != lastName) {
                users.setLastName(lastName);
            }
            if (loginOld != login) {
                users.setLogin(login);
            }

            Transaction t = session.beginTransaction();
            Users updateUser = (Users) session.merge(users);

            System.out.println(String.format("Обновили данные для клиента - [id = %s],[firstName = %s],[lastName = %s],[login = %s]",
                    users.getId(), users.getFirstName(), users.getLastName(), users.getLogin()));
            System.out.println(String.format("Старые данные клиента - [id = %s],[firstName = %s],[lastName = %s],[login = %s]",
                    users.getId(), firstNameOld, lastNameOld, loginOld));
            t.commit();
        }
        return users;
    }

    @Override
    public Users updateFirstNamUser(Integer id, String firstName) throws IOException {
        Users users;
        try (Session session = factory.openSession()) {
            users = session.get(Users.class, id);
            String oldFirstName = users.getFirstName();
            users.setFirstName(firstName);

            Transaction t = session.beginTransaction();
            users = (Users) session.merge(users);
            System.out.println(String.format("Обновили имя клиенту с id = %s, новоем имя %s(старое - %s)", users.getId(), users.getFirstName(), oldFirstName));
            t.commit();
        }
        return users;
    }

    @Override
    public Users updateLastNameUser(Integer id, String lastName) throws IOException {
        Users users;
        try (Session session = factory.openSession()) {
            users = session.get(Users.class, id);
            String oldLastName = users.getLastName();

            Transaction t = session.beginTransaction();
            users.setLastName(lastName);
            Object merge = session.merge(users);
            users = (Users) merge;

            t.commit();
            System.out.println(String.format("Обновили имя клиенту с id = %s, новоем имя %s(старое - %s)", users.getId(), users.getLastName(), oldLastName));
        }
        return users;
    }

    @Override
    public Users updateLoginUser(Integer id, String login) throws IOException {
        Users users;
        try (Session session = factory.openSession()) {
            users = session.get(Users.class, id);
            String oldLogin = users.getLogin();

            Transaction t = session.beginTransaction();
            users.setLogin(login);
            Users updateUser = (Users) session.merge(users);
            t.commit();
            System.out.println(String.format("Обновили имя клиенту с id = %s, новоем имя %s(старое - %s)", users.getId(), users.getLogin(), oldLogin));
        }
        return users;
    }

}
