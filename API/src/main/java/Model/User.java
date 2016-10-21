package Model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alexis on 17/10/2016.
 */
@Entity
public class User implements Serializable {

    @Id
    @Column(name = "mail")
    private String mail;

    @Column(name = "pseudo")
    private String pseudo;

    @Column(name = "hashkey")
    private String hashkey;

    public User(String mail, String pseudo) {
        this.pseudo = pseudo;
        this.mail = mail;
    }

    public String getHashkey() {
        return hashkey;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setHashkey(String hashkey) {
        this.hashkey = hashkey;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getMail() {
        return mail;
    }

    @Override
    public String toString(){
        return String.format("User[pseudo='%s', mail='%s']", pseudo,mail);
    }
}
