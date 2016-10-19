package  main.java.API;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by p1317074 on 19/10/2016.
 */
public class JPATest {

    @Test
    public void setupEMTest() {
        EntityManager em = Persistence.createEntityManagerFactory("pu-multimif").createEntityManager();
        em.close();
    }

}
