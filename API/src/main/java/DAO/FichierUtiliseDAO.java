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

        fichier = new FichierUtilise(user, idFichier.toString(), contenu);

        return fichier;
    }

    public boolean exist(String id) {
        return em.find(FichierUtilise.class, id) != null;
    }

    //public FichierUtilise createOrUpdate(String user, ObjectId id, String contenu) {
    public FichierUtilise createOrUpdate(User user, String id, String contenu) {
        FichierUtilise fichier = em.find(FichierUtilise.class, id);
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

        em.persist(fichier);
        return fichier;
    }
}
