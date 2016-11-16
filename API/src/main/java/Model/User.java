package Model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alexis on 17/10/2016.
 */
@Entity
@NamedQuery(name="User.findByMail", query="SELECT u from User u WHERE u.mail = :mail")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mail", unique = true)
    private String mail;

    @Column(name = "pseudo", unique = true)
    private String pseudo;

    @Column(name = "hashkey")
    private String hashkey;

    public User(){

    }

    public User(String mail, String pseudo, String hashkey) {
        this.pseudo = pseudo;
        this.mail = mail;
        this.hashkey = hashkey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
