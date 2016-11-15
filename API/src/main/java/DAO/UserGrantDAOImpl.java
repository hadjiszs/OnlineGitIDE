package DAO;

import Model.UserGrant;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public class UserGrantDAOImpl extends DAO implements UserGrantDAO {

    public UserGrantDAOImpl(EntityManager em) {
        super(em);
    }

    public boolean addEntity(UserGrant user) throws Exception{
        return false;
    }

    public UserGrant getEntityByMail(String mail) throws Exception{
        UserGrant usr = null;

        return usr;
    }

    public List getEntityList() throws Exception{
        List<UserGrant> list = new ArrayList<UserGrant>();

        return list;
    }

    public boolean deleteEntity(String mail) throws Exception{
        return false;
    }
}
