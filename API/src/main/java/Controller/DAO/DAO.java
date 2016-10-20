package Controller.DAO;

import javax.persistence.EntityManager;

/**
 * Created by p1317074 on 19/10/16.
 */
public class DAO {

    protected EntityManager em;

    public DAO(EntityManager em) {
        this.em = em;
    }
}
