package Controller.DAO;

import javax.persistence.Persistence;
import org.junit.Test;
import javax.persistence.EntityManager;

/**
 * Created by p1317074 on 20/10/16.
 */
public class JPATest {

    @Test
    public void setupEMTest() {
        EntityManager em = Persistence.createEntityManagerFactory("pu-multimif").createEntityManager();
        em.close();
    }

}