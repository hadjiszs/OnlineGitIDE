package DAO;

import Model.FichierUtilise;
import Model.User;
import org.eclipse.jgit.lib.ObjectId;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Created by p1317074 on 19/10/16.
 */
public class FichierUtiliseDAO extends DAO {

    public FichierUtilise getFichierUtiliseByIdAndUser(User user, ObjectId idFichier) {
        FichierUtilise fichier;
        String contenu;
        Query query = getEntityManager().createNamedQuery("FichierUtilise.findByIdAndUser", FichierUtilise.class);
        query.setParameter("user", user.getMail());
        query.setParameter("idFichier", idFichier.toString());

        try {
            contenu = (String) query.getSingleResult();
        } catch(NoResultException e) {
            contenu = null;
        }finally {
            closeEntityManager();
        }

        fichier = new FichierUtilise(user, idFichier.toString(), contenu);

        return fichier;
    }

    public boolean exist(String id) {
        boolean result= getEntityManager().find(FichierUtilise.class, id) != null;
        closeEntityManager();

        return result;
    }

    //public FichierUtilise createOrUpdate(String user, ObjectId id, String contenu) {
    public FichierUtilise createOrUpdate(User user, String id, String contenu) {
        FichierUtilise fichier = getEntityManager().find(FichierUtilise.class, id);

        if (fichier == null) {
            if (id != ObjectId.zeroId().toString()) {
                fichier = new FichierUtilise(user, id, contenu);
            }
            else {
                throw new IllegalArgumentException("Id = zeroId");
            }
        } else {
            if( ! fichier.getContenu().equals(contenu))
                fichier.setContenu(contenu);
        }
        getEntityManager().getTransaction().begin();
        getEntityManager().persist(fichier);
        getEntityManager().getTransaction().commit();

        closeEntityManager();

        return fichier;
    }
}
