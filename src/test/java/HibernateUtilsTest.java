import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.levelup.chat.domain.*;

import java.util.Properties;

public class HibernateUtilsTest {
    private static final SessionFactory testFactory;

    static {
        //in memory database
        Properties databaseProperties = new Properties();

        databaseProperties.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        databaseProperties.setProperty("hibernate.connection.url", "jdbc:h2:mem:chat;INIT=CREATE SCHEMA IF NOT EXISTS chat");
        databaseProperties.setProperty("hibernate.connection.username", "sa");
        databaseProperties.setProperty("hibernate.connection.password", "");
        databaseProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        databaseProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        databaseProperties.setProperty("hibernate.show_sql", "true");
        databaseProperties.setProperty("hibernate.format_sql", "true");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(databaseProperties)
                .build();

        testFactory = new Configuration()
                .addAnnotatedClass(Channel.class)
                .addAnnotatedClass(ChannelDetails.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Message.class)
                .addAnnotatedClass(Password.class)
                .addAnnotatedClass(UserChannel.class)
                .buildSessionFactory(registry);
    }

    private HibernateUtilsTest() {
    }

    public static SessionFactory getSessionFactory() {
        return testFactory;
    }

}
