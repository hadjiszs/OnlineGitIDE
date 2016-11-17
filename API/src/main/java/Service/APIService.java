package Service;

import Util.Constantes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by hadjiszs on 21/10/16.
 */
public abstract class APIService {
    public static EntityManagerFactory entityManagerFactory;

    private APIService(){
        /* On cache le constructeur */
    }

    public static void persistance() {
        entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.ENTITY_FACTORY);
    }

    public static void close() {
        entityManagerFactory.close();
    }

    public static EntityManager getEm() {
        return em;
    }
}