package com.baufest.Libreria.session;


import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Hashtable;

public class HibernateUtil {

    private StandardServiceRegistry registry;
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory(String option) {
        Hashtable<String, String>
                configs = new Hashtable<String, String>();
        configs.put("select", "hibernate1.cfg.xml");
        configs.put("insert", "hibernate2.cfg.xml");
        configs.put("delete", "hibernate3.cfg.xml");

        System.out.println("Hash code: " + option + " Hash: " + configs.get(option));

        if (sessionFactory == null) {
            try {
                //create registry
                registry = new StandardServiceRegistryBuilder().configure(configs.get(option)).build();
                System.out.println("registry: " + registry);
                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                System.out.println("sources: " + sources);
                System.out.println("metaBuilder: " + sources.getMetadataBuilder());

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e ) {

                e.printStackTrace();
                if (registry != null){
                    StandardServiceRegistryBuilder.destroy(registry);
                }

            }
        }

        return sessionFactory;

    }

    public void shutdown() {
        if (registry != null){
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}

