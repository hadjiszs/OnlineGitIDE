package Model;

import org.eclipse.jgit.lib.ObjectId;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by p1317074 on 19/10/16.
 */

@Entity
@NamedQuery(name="FichierUtilise.findByIdAndUser", query="SELECT f from FichierUtilise f WHERE f.user = :user AND f.idFichier = :idFichier")
public class FichierUtilise implements Serializable {


    @Column(name = "contenu")
    @Lob
    private String contenu;

    @Column(name="idFichier")
    @Id
    private String idFichier;

    @ManyToOne
    private User user;

    public FichierUtilise(User user, String idFichier, String contenu) {
        this.contenu = contenu;
        this.idFichier = idFichier;
        this.user = user;
    }

    public FichierUtilise() {
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public ObjectId getIdFichier() {
        return ObjectId.fromString(idFichier);
    }

    public void setIdFichier(ObjectId id) {
        this.idFichier = id.toString();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
