package Service;

import Model.User;

/**
 * Created by amaia.nazabal on 11/16/16.
 */
public interface FichierUtiliseService {
    String getEntityById(Long idProject, String idFichier);
    void editEntity(User u, String id, String contenue);
}
