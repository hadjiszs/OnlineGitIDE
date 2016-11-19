package Model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alexis on 17/10/2016.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="User.findByMail", query="SELECT u from User u WHERE u.mail = :mail"),
        @NamedQuery(name="User.findByPseudo", query="SELECT u from User u WHERE u.username = :username")
})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mail", unique = true, nullable = false)
    private String mail;

    @Column(name = "username", unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String hashkey;

    public User(){

    }

    public User(String mail, String username, String hashkey) {
        this.username = username;
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

    public void setUsername(String pseudo) {
        this.username = pseudo;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setHashkey(String hashkey) {
        this.hashkey = hashkey;
    }

    public String getUsername() {
        return username;
    }

    public String getMail() {
        return mail;
    }

    @Override
    public String toString(){
        return String.format("User[pseudo='%s', mail='%s']", username,mail);
    }
}
