package com.multimif.dao;

import com.multimif.util.Constantes;

import javax.persistence.EntityManagerFactory;

/**
 *
 * Classe où on cree la persistance
 *
 * @author hadjiszs
 * @version 1.0
 * @since 1.0 21/10/16.
 */
public abstract class EntityFactoryManager {
    private static EntityManagerFactory entityManagerFactory;

    private EntityFactoryManager() {
        /* On cache le constructeur */
    }

    /**
     * Cette méthode crée la persistance contre la base de données, pour cette raison est appelé just
     * la premierère fois
     */
    public static void persistance() {
        entityManagerFactory = javax.persistence.Persistence.createEntityManagerFactory(Constantes.ENTITY_FACTORY);
    }

    /**
     * Cette méthode ferme la persistance contre la BD
     */
    public static void close() {
        entityManagerFactory.close();
    }

    /**
     * Cette méthode retourne l'entité du factory
     *
     * @return entity manager factory
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}