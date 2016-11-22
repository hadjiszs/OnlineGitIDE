package com.multimif.dao;

import javax.persistence.EntityManager;

/**
 * Created by p1317074 on 19/10/16.
 */
public class DAO {

    protected EntityManager em;

    protected EntityManager getEntityManager(){
        if (em == null || !em.isOpen()){
            em = EntityFactoryManager.getEntityManagerFactory()
                    .createEntityManager();
        }
        return em;
    }

    protected void closeEntityManager(){
        if (em.isOpen()){
            em.close();
        }
    }
}
