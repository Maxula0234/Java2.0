package org.levelup.chat.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static SessionFactory factory;

    // Initialize and configure SessionFactory on application startup
    static {
        // Read hibernate.cfg.xml file
        // Configuration of Hibernate
        Configuration configuration = new Configuration().configure();

        factory = configuration.buildSessionFactory();
    }

    // Hibernate.getFactory()
    public static SessionFactory getFactory() {
        return factory;
    }

}