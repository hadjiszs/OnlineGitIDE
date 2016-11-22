package com.multimif.dao;

import javax.persistence.EntityManager;

/**
 * @author p1317074
 * @version 1.0
 * @since 1.0 19/10/16.
 */
public class DAO {

    protected EntityManager em;

    /**
     * Cette méthode retourne une nouvelle instance de l'entity manager s'il n'y a pas
     * aucun déjà ouvert, sinon retourne la même
     *
     * @return entity manager
     */
    protected EntityManager getEntityManager(){
        if (em == null || !em.isOpen()){
            em = EntityFactoryManager.getEntityManagerFactory()
                    .createEntityManager();
        }
        return em;
    }

    /**
     * Cette méthode ferme l'entite manager.
     * Pour convention on ouvre et ferme l'entity manager dans méthode du DAO.
     */
    protected void closeEntityManager(){
        if (em.isOpen()){
            em.close();
        }
    }
}
