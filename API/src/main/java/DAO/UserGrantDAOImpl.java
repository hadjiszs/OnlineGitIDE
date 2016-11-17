package DAO;

import Model.UserGrant;
import Util.DataException;

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

    public boolean addEntity(UserGrant user) throws DataException{
        return false;
    }

    public UserGrant getEntityByMail(String mail) throws DataException{
        UserGrant usr = null;

        return usr;
    }

    public List getEntityList() throws NullPointerException{
        List<UserGrant> list = new ArrayList<UserGrant>();

        return list;
    }

    public boolean deleteEntity(String mail) throws DataException{
        return false;
    }
}
