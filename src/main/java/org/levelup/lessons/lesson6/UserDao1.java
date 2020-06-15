package org.levelup.lessons.lesson6;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.chat.domain.User;

@RequiredArgsConstructor
public class UserDao1 {
    private final SessionFactory factory;

    public User createUSer(String login, String firstName, String lastName){
        User user;
        try (Session session = factory.openSession()){
            Transaction t = session.beginTransaction();
            user = new User(login,firstName,lastName); //-> Transient (Переходящий)

            Integer id = (Integer) session.save(user);
            System.out.println("Genereate id: " + id);//            session.persist(user); // -> Persistent (Постоянный)
//            session.persist(user);
//            user.setFirstName(firstName + "(update)");


            t.commit();
        }
//        session close() -> объект User = Detached (откреплен от сессии)
//         user.setFirstName -> не отправленный в базу ум
        try (Session session = factory.openSession()){
             Transaction t = session.beginTransaction();

             user.setLogin(user.getLogin() + "(update)");
             Integer id = (Integer)session.save(user);
            System.out.println("Generate ID of Detached object: " + id);
             t.commit();
        }
        return null;
    }


    public User mergeUser(Integer id, String login, String firstName, String lastName){
        User user;

        try (Session session = factory.openSession()) {
            //get() ->  createQuery("from User where id = :id).setParametr("id",id).uniqueResult()
            user = session.get(User.class,id);
        } //user - detached state
        try (Session session = factory.openSession()){
            Transaction t = session.beginTransaction();
            user.setLogin(login);
            user.setFirstName(firstName);
            user.setLastName(lastName);

            User mergeUser = (User) session.merge(user);
            //user - detached
            //merfeUser - persistent

            System.out.println("Equals: " + user.equals(mergeUser));
            System.out.println("== " + (user ==mergeUser));
            t.commit();
        }
        return null;
    }

    public User updateUser(Integer id, String login, String firstName, String lastName){
        User user;
        try (Session session = factory.openSession()){
            Transaction t = session.beginTransaction();
            user = session.get(User.class,id);

            session.evict(user);
            user.setLogin(login);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setId(id);
            session.update(user);

            t.commit();
        }
        return null;
    }

    //get and load получить сущность по id
    public void get(Integer id){
        User user;
        try (Session session = factory.openSession()){
            user = session.get(User.class,id);
          //  System.out.println("Detached user : " + user.toString());
        }
        System.out.println("Detached user : " + user.toString());
    }

    public void load(Integer id){
        User user;
        try (Session session = factory.openSession()){
            user = session.load(User.class,id);
            System.out.println(user.getLogin());
          //  System.out.println("Persistance user: " + user.toString());
        }
        System.out.println("Detached user : " + user.toString());
    }
}
