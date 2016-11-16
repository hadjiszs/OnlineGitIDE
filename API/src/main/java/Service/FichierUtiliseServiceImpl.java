package Service;

import DAO.FichierUtiliseDAO;
import Model.User;
import Util.DataException;

/**
 * Created by hadjiszs on 21/10/16.
 */
public class FichierUtiliseServiceImpl {
    FichierUtiliseDAO dao;

    public FichierUtiliseServiceImpl() {
        dao = new FichierUtiliseDAO();
    }

    public String getEntityById(Long idProject, String idFichier) {
        // @TODO fonction getEntityById file
        return null;
    }

    // id -> id hexa corresponodant à l'ObjectID
    public void editEntity(User u, String id, String contenue) {
        dao.createOrUpdate(u, id, contenue);
    }
}
