package org.levelup.chat;

import lombok.SneakyThrows;
import org.levelup.chat.dao.ChannelDao;
import org.levelup.chat.dao.ChannelDetailsDao;
import org.levelup.chat.dao.MessagesDao;
import org.levelup.chat.dao.UsersDao;
import org.levelup.chat.dao.impl.HibernateChannelDao;
import org.levelup.chat.dao.impl.HibernateChannelDetailsDao;
import org.levelup.chat.dao.impl.HibernateMessagesDao;
import org.levelup.chat.dao.impl.HibernateUsersDao;
import org.levelup.chat.domain.Channel;
import org.levelup.chat.domain.User;
import org.levelup.chat.hibernate.HibernateUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatApplication {

//    @SneakyThrows
//    public static void main(String[] args) {
//        ChannelDao channelDao = new HibernateChannelDao();
//        UsersDao usersDao = new HibernateUsersDao();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//
//        System.out.println("[log] Для работы с channel введите - Channel");
//        System.out.println("[log] Для работы с users введите - Users");
//        String _case = reader.readLine().toLowerCase();
//        switch (_case){
//            case "channel": {
//                System.out.println("Вывести все каналы - enter [findAll]");
//                System.out.println("Создать канал - enter [createChannel]");
//                System.out.println("Удалить канал - enter [removeByName]");
//                System.out.println("Найти канал - enter [findByName]");
//                String _caseChannel = reader.readLine().toLowerCase();
//                switch (_caseChannel){
//                    case "findall":{
//                        channelDao.findAll();
//                        break;
//                    }
//                    case "removebyname":{
//                        System.out.println("Введите имя канала, который необходимо удалить - ");
//                        String nameChannel = reader.readLine().toLowerCase();
//                        channelDao.findByName(nameChannel);
//                        break;
//                    }
//                    case "findbyname":{
//                        System.out.println("Введите имя канала, который необходимо найти - ");
//                        String nameChannelf = reader.readLine().toLowerCase();
//                        channelDao.findByName(nameChannelf);
//                        break;
//                    }
//                    case "createchannel":{
//                        System.out.println("Введите имя канала - ");
//                        String nameChannelС = reader.readLine();
//                        System.out.println("Введите displayName канала - ");
//                        String displayName = reader.readLine().toLowerCase();
//                        channelDao.createChannel(nameChannelС,displayName);
//                        break;
//                    }
//                    default: {
//                        System.out.println("Вы ввели некорректную команду.");
//                    }
//                }
//                break;
//            }
//            case "users":{
//                System.out.println("[log] Найти всех клиентов - enter [findAllUsers]");
//                System.out.println("[log] Найти пользователя по логину - enter [findByLogin]");
//                System.out.println("[log] Найти пользователя по id - enter [findById]");
//                System.out.println("[log] Удалить клиента - enter [removeById]");
//                System.out.println("[log] Создать клиента - enter [createUser]");
//                System.out.println("[log] Найти клиента(новая) - enter [findByIdNew]");
//                String _caseUsers = reader.readLine().toLowerCase();
//                switch (_caseUsers){
//                    case "findallusers":{
//                        usersDao.findAllUsers();
//                        break;
//                    }
//                    case "findbylogin":{
//                        Users fUser = usersDao.findByLogin();
//                        break;
//                    }
//                    case "findbyid":{
//                        System.out.println(String.format("[log] Введите id пользователя(int)"));
//                        String idFind = reader.readLine();
//                        usersDao.findById(Integer.parseInt(idFind));
//                        break;
//                    }
//                    case "removebyid":{
//                        usersDao.removeById();
//                        break;
//                    }
//                    case "createuser":{
//                        usersDao.createUsers();
//                        break;
//                    }
//                    case "findbyidnew":{
//                        usersDao.findByIdNew();
//                        break;
//                    }
//                    default: {
//                        System.out.println("[log] Вы ввели некорректную команду.");
//                    }
//                }
//                break;
//            }
//            default: {
//                System.out.println("[log] Вы ввели некорректную команду.");
//            }
//        }
//        HibernateUtils.getFactory().close();
//    }

    @SneakyThrows
    public static void main(String[] args) {
        MessagesDao messagesDao = new HibernateMessagesDao(HibernateUtils.getFactory());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ChannelDao channelDao = new HibernateChannelDao();
        UsersDao usersDao = new HibernateUsersDao();


        System.out.println("Enter id user :");
        Integer channelId = Integer.parseInt(reader.readLine());
//        System.out.println("Enter password user: ");
//        String userId = reader.readLine();
        usersDao.addUserToChat(1,"enot");
        usersDao.findUserIdFromPassword(1);
    }
}
