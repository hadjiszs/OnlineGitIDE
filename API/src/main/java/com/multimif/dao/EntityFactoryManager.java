package com.multimif.dao;

import com.multimif.util.Constantes;

import javax.persistence.EntityManagerFactory;

/**
 * Created by hadjiszs on 21/10/16.
 */
public abstract class EntityFactoryManager {
    private static EntityManagerFactory entityManagerFactory;

    private EntityFactoryManager(){
        /* On cache le constructeur */
    }

    public static void persistance() {
        entityManagerFactory = javax.persistence.Persistence.createEntityManagerFactory(Constantes.ENTITY_FACTORY);
    }

    public static void close() {
        entityManagerFactory.close();
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}