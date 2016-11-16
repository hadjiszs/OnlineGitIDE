package Service;

import Util.Constantes;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by hadjiszs on 21/10/16.
 */
public abstract class APIService {
    public static EntityManager em;

    private APIService(){
        /* On cache le constructeur */
    }

    public static void persistance() {
        em = Persistence.createEntityManagerFactory(Constantes.ENTITY_FACTORY)
                .createEntityManager();
    }

    public static void close() {
        em.close();
    }
}