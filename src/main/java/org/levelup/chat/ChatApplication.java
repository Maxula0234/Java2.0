package org.levelup.chat;

import lombok.SneakyThrows;
import org.levelup.chat.dao.ChannelDao;
import org.levelup.chat.dao.UsersDao;
import org.levelup.chat.dao.impl.HibernateChannelDao;
import org.levelup.chat.dao.impl.HibernateUsersDao;
import org.levelup.chat.domain.Channel;
import org.levelup.chat.domain.Users;
import org.levelup.chat.hibernate.HibernateUtils;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatApplication {

    @SneakyThrows
    public static void main(String[] args) {
        ChannelDao channelDao = new HibernateChannelDao();
        UsersDao usersDao = new HibernateUsersDao();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Для работы с channel введите - Channel");
        System.out.println("Для работы с users введите - Users");
        String _case = reader.readLine().toLowerCase();
        switch (_case){
            case "channel": {
                System.out.println("Вывести все каналы - enter [findAll]");
                System.out.println("Создать канал - enter [createChannel]");
                System.out.println("Удалить канал - enter [removeByName]");
                System.out.println("Найти канал - enter [findByName]");
                String _caseChannel = reader.readLine().toLowerCase();
                switch (_caseChannel){
                    case "findall":{
                        channelDao.findAll();
                        break;
                    }
                    case "removebyname":{
                        System.out.println("Введите имя канала, который необходимо удалить - ");
                        String nameChannel = reader.readLine().toLowerCase();
                        channelDao.findByName(nameChannel);
                        break;
                    }
                    case "findbyname":{
                        System.out.println("Введите имя канала, который необходимо найти - ");
                        String nameChannelf = reader.readLine().toLowerCase();
                        channelDao.findByName(nameChannelf);
                        break;
                    }
                    case "createchannel":{
                        System.out.println("Введите имя канала - ");
                        String nameChannelС = reader.readLine();
                        System.out.println("Введите displayName канала - ");
                        String displayName = reader.readLine().toLowerCase();
                        channelDao.createChannel(nameChannelС,displayName);
                        break;
                    }
                    default: {
                        System.out.println("Вы ввели некорректную команду.");
                    }
                }
                break;
            }
            case "users":{
                System.out.println("Найти всех клиентов - enter [findAllUsers]");
                System.out.println("Найти пользователя по логину - enter [findByLogin]");
                System.out.println("Найти пользователя по id - enter [findById]");
                System.out.println("Удалить клиента - enter [removeById]");
                System.out.println("Создать клиента - enter [createUser]");
                String _caseUsers = reader.readLine();
                switch (_caseUsers){
                    case "findallusers":{
                        usersDao.findAllUsers();
                        break;
                    }
                    case "findbylogin":{
                        System.out.println("Введите логин пользователя - ");
                        String loginU = reader.readLine();
                        Users fUser = usersDao.findByLogin(loginU);
                        break;
                    }
                    case "findbyid":{}
                    case "removebyid":{}
                    case "createuser":{}
                    default: {
                        System.out.println("Вы ввели некорректную команду.");
                    }
                }
                break;
            }
            default: {
                System.out.println("Вы ввели некорректную команду.");
            }
        }
        HibernateUtils.getFactory().close();
    }

}
