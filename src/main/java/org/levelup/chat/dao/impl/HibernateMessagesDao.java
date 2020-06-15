package org.levelup.chat.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.chat.dao.MessagesDao;
import org.levelup.chat.domain.Channel;
import org.levelup.chat.domain.Message;
import org.levelup.chat.domain.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class HibernateMessagesDao implements MessagesDao {
    private final SessionFactory factory;

    @Override
    public Message getMessageById() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Message message;
        try (Session session = factory.openSession()) {
            System.out.println("Введите id сообщения: ");
            Integer id = Integer.parseInt(reader.readLine());
            message = session.get(Message.class, id);
            System.out.println(String.format("Найдено сообщение: [text - %s][date - %s][userId - %s][channelId - %s]",
                    message.getText(), message.getDate(), message.getUser(), message.getChannel()));
        }
        return message;
    }

    @Override
    public Message getMessageById(Integer id) {
        Message message;
        try (Session session = factory.openSession()) {
            try {
                message = session.get(Message.class,id);
                System.out.println(String.format("Найдено сообщение: [text - %s][date - %s][userId - %s][channelId - %s]",
                        message.getText(), message.getDate(), message.getUser(), message.getChannel()));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(String.format("Сообщение с id = %s, не найден.",id));
                return null;
            }
        }
        return message;
    }

    @Override
    @SneakyThrows
    public Message updateMessageById(Integer id, String text) {
        Message message;
        try (Session session = factory.openSession()){
            message = session.get(Message.class,id);
            String oldText = message.getText();
            message.setText(text);
            message = (Message) session.merge(message);
            System.out.println(String.format("Update text for messageId = %s, [old text = %s], [new text = %s]",id,oldText,text));
        }
        return message;
    }

    @Override
    public void sendMessage(User user, Channel channel, String text) {
        try(Session session = factory.openSession()){
            Transaction transaction = session.beginTransaction();
            Message message = new Message();
            message.setUser(user);
            message.setChannel(channel);
            message.setText(text);
            message.setDate(LocalDateTime.now());
            session.persist(message);

            transaction.commit();
        }
    }

}
