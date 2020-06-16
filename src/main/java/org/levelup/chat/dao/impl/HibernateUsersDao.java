package org.levelup.chat.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.chat.dao.UsersDao;
import org.levelup.chat.domain.Channel;
import org.levelup.chat.domain.Password;
import org.levelup.chat.domain.User;
import org.levelup.chat.domain.UserChannel;
import org.levelup.chat.hibernate.HibernateUtils;
import org.w3c.dom.ls.LSOutput;

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
    public Collection<User> findAllUsers() {
        Session session = factory.openSession();
        Collection<User> allUsers = session.createQuery("from Users", User.class).getResultList();
        session.close();
        allUsers.stream()
                .forEach(s -> System.out.println(String.format("[log] [id = %s][firstName - %s], [lastName - %s], [login - %s]",
                        s.getId(), s.getFirstName(), s.getLastName(), s.getLogin())));
        return allUsers;
    }

    @Override
    public User findByLogin() throws IOException {
        try (Session session = factory.openSession()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            AtomicReference<User> user = new AtomicReference<>();
            System.out.println("Введите логин");
            String login = reader.readLine();
            List<User> users = session.createQuery("from Users where login = :login", User.class)
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
    public User findById(int id) throws IOException {
        try (Session session = factory.openSession()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            AtomicReference<User> user = new AtomicReference<>();
            List<User> users = session.createQuery("from Users where id = :id", User.class)
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
    public User findByIdNew() throws IOException {
        try (Session session = factory.openSession()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter id");
            Integer id = Integer.parseInt(reader.readLine());
            AtomicReference<User> user = new AtomicReference<>();
            User user1 = session.get(User.class, id);
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
    public User checkLogin(String login) {
        try (Session session = factory.openSession()) {
            List<User> users = session.createQuery("from User where login = :log", User.class)
                    .setParameter("log", login)
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
            Collection<User> allUsers = findAllUsers();
            System.out.println("[log] Введите id пользователя, которого необходимо удалить.");
            String delId = reader.readLine();
            User user = findById(Integer.parseInt(delId));
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
            Collection<User> allUsers = findAllUsers();
            System.out.println("[log] Введите id пользователя, которого необходимо удалить.");
            String delId = reader.readLine();
            User user = findById(Integer.parseInt(delId));
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
    public User createUsers() throws IOException {
        try (Session session = factory.openSession()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            AtomicReference<User> newUser = new AtomicReference<>();

            System.out.println("Введите имя.");
            String firstName = reader.readLine();
            System.out.println("Введите фамилию.");
            String lastName = reader.readLine();
            System.out.println("Введите login.");
            String login = reader.readLine();
            User checkLogin = checkLogin(login.toLowerCase());
            if (checkLogin == null) {
                Transaction transaction = session.beginTransaction();
                User user = new User();
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
    public User updateUser(Integer id, String firstName, String lastName, String login) throws IOException {
        User user;
        try (Session session = factory.openSession()) {
            user = session.get(User.class, id);

            String firstNameOld = user.getFirstName();
            String lastNameOld = user.getLastName();
            String loginOld = user.getLogin();

            if (firstNameOld != firstName) {
                user.setFirstName(firstName);
            }
            if (lastNameOld != lastName) {
                user.setLastName(lastName);
            }
            if (loginOld != login) {
                user.setLogin(login);
            }

            Transaction t = session.beginTransaction();
            User updateUser = (User) session.merge(user);

            System.out.println(String.format("Обновили данные для клиента - [id = %s],[firstName = %s],[lastName = %s],[login = %s]",
                    user.getId(), user.getFirstName(), user.getLastName(), user.getLogin()));
            System.out.println(String.format("Старые данные клиента - [id = %s],[firstName = %s],[lastName = %s],[login = %s]",
                    user.getId(), firstNameOld, lastNameOld, loginOld));
            t.commit();
        }
        return user;
    }

    @Override
    public User updateFirstNamUser(Integer id, String firstName) throws IOException {
        User user;
        try (Session session = factory.openSession()) {
            user = session.get(User.class, id);
            String oldFirstName = user.getFirstName();
            user.setFirstName(firstName);

            Transaction t = session.beginTransaction();
            user = (User) session.merge(user);
            System.out.println(String.format("Обновили имя клиенту с id = %s, новоем имя %s(старое - %s)", user.getId(), user.getFirstName(), oldFirstName));
            t.commit();
        }
        return user;
    }

    @Override
    public User updateLastNameUser(Integer id, String lastName) throws IOException {
        User user;
        try (Session session = factory.openSession()) {
            user = session.get(User.class, id);
            String oldLastName = user.getLastName();

            Transaction t = session.beginTransaction();
            user.setLastName(lastName);
            Object merge = session.merge(user);
            user = (User) merge;

            t.commit();
            System.out.println(String.format("Обновили имя клиенту с id = %s, новоем имя %s(старое - %s)", user.getId(), user.getLastName(), oldLastName));
        }
        return user;
    }

    @Override
    public User updateLoginUser(Integer id, String login) throws IOException {
        User user;
        try (Session session = factory.openSession()) {
            user = session.get(User.class, id);
            String oldLogin = user.getLogin();

            Transaction t = session.beginTransaction();
            user.setLogin(login);
            User updateUser = (User) session.merge(user);
            t.commit();
            System.out.println(String.format("Обновили имя клиенту с id = %s, новоем имя %s(старое - %s)", user.getId(), user.getLogin(), oldLogin));
        }
        return user;
    }

    @Override
    public Password addUserToChat(Integer userId, String password) {
        try (Session session = factory.openSession()) {
            Password checkUser = findUserIdFromPassword(userId);
            if (checkUser == null) {
                Transaction transaction = session.beginTransaction();
                Password newUser = new Password();
                newUser.setUserId(userId);
                newUser.setPassword(password);
                session.persist(newUser);
                transaction.commit();
                return newUser;
            } else {
                System.out.println("Данный пользователь уже зарегистрирован");
                return null;
            }
        }
    }

    @Override
    public void removeUserFromChat(Integer userId) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE Password where user_id = :userId")
                    .setParameter("userId", userId)
                    .executeUpdate();
            System.out.println(String.format("Удалили пользователя из чата %s", userId));
            transaction.commit();
        }
    }

    @Override
    public Password findUserIdFromPassword(Integer userId) {
        try (Session session = factory.openSession()) {
            try {
                Password user = session.get(Password.class, userId);
                System.out.println("Пользователь найден.");
                return user;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Пользователь не найден.");
                return null;
            }
        }
    }

    @Override
    public Password updatePasswordFromUser(Integer userId, String oldPassword, String newPassword) {
        try (Session session = factory.openSession()) {

            Password password = session.get(Password.class, userId);
            if (password.getPassword().contains(oldPassword)) {
                Transaction transaction = session.beginTransaction();
                password.setPassword(newPassword);
                session.merge(password);
                transaction.commit();
                System.out.println(String.format("Изменили пароль для пользователя - %s [old - %s][new - %s]", password.getUser().getLogin(), oldPassword, newPassword));
            } else {
                System.out.println("Старый пароль введен не верно. Изменение отклонено.");
                return null;
            }
            return password;
        }
    }

    @Override
    public String loginToChat(String login, String password) {
        try (Session session = factory.openSession()) {
            User user = checkLogin(login);
            if (user != null) {
                Password checkPass = findUserIdFromPassword(user.getId());
                if (checkPass.getPassword().contains(password)){
                    System.out.println("***Доступ разрещен");
                    return "ok";
                }else {
                    System.out.println("***Доступ запрещен.");
                    return "no";
                }
            } else {
                System.out.println("***Доступ запрещен.");
                return "no";
            }
        }
    }

    @Override
    public Collection<UserChannel> allUserChannels(Integer userId) {
        try (Session session = factory.openSession()) {

            User user = session.get(User.class,userId);
            Collection<UserChannel> allUserChannels = session.createQuery("from UserChannel where user_id = :userId",UserChannel.class)
                    .setParameter("userId",userId)
                    .getResultList();
            return allUserChannels;
        }
    }

}
