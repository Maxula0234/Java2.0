package org.levelup.chat;

import lombok.SneakyThrows;
import org.levelup.chat.dao.ChannelDao;
import org.levelup.chat.dao.UsersDao;
import org.levelup.chat.dao.impl.HibernateChannelDao;
import org.levelup.chat.dao.impl.HibernateUsersDao;
import org.levelup.chat.domain.Users;
import org.levelup.chat.hibernate.HibernateUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatApplication {

    @SneakyThrows
    public static void main(String[] args) {
        ChannelDao channelDao = new HibernateChannelDao();
        UsersDao usersDao = new HibernateUsersDao();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("********************************************************************************************************");
        System.out.println("********************************************************************************************************");

        usersDao.removeById(13);
        HibernateUtils.getFactory().close();
    }

}
