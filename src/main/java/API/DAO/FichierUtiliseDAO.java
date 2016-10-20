package API.DAO;

import Model.FichierUtilise;
import Model.User;
import org.eclipse.jgit.lib.ObjectId;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by p1317074 on 19/10/16.
 */
public class FichierUtiliseDAO extends DAO {

    public FichierUtiliseDAO(EntityManager em) {
        super(em);
    }

    public FichierUtilise getFichierUtiliseByIdAndUser(User user, ObjectId idFichier) {
        FichierUtilise fichier;
        String contenu;
        Query query = em.createNamedQuery("FichierUtilise.findByIdAndUser", FichierUtilise.class);
        query.setParameter("user", user.getMail());
        query.setParameter("idFichier", idFichier.toString());
        try {
            contenu = (String) query.getSingleResult();
        } catch(NoResultException e) {
            contenu = null;
        }

        fichier = new FichierUtilise(idFichier, user, contenu);

        return fichier;

    }



    public FichierUtilise createOrUpdate(User user, ObjectId id, String contenu) {
        FichierUtilise fichier = em.find(FichierUtilise.class, id);
        if (fichier == null) {
            if (id != ObjectId.zeroId()) {
                fichier = new FichierUtilise(id, user, contenu);
            }
            else {
                throw new IllegalArgumentException("Id = zeroId");
            }
        }
        em.persist(fichier);
        return fichier;
    }



}
