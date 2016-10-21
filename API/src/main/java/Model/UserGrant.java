package Model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
@Entity
@Table(name = "user_grant")
@IdClass(UserGrantID.class)
public class UserGrant implements Serializable {
    //private enum Permis {ADMIN, DEV};
    @MapsId("id")
    @Id
    private Long projetId;

    @MapsId("mail")
    @Id
    private String mail;


    private String grant;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mail", insertable = false, updatable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getProjetId() {
        return projetId;
    }

    public void setProjetId(Long projetId) {
        this.projetId = projetId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getGrant() {
        return grant;
    }

    public void setGrant(String grant) {
        this.grant = grant;
    }
}
