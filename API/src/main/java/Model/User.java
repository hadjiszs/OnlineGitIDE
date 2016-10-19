package Model;

import javax.persistence.*;

/**
 * Created by Alexis on 17/10/2016.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "pseudo")
    private String pseudo;

    @Column(name = "mail")
    private String mail;

    public User(String pseudo, String mail){
        this.pseudo = pseudo;
        this.mail = mail;
    }

    @Override
    public String toString(){
        return String.format("User[id=%d, pseudo='%s', mail='%s']",
                id, pseudo,mail);
    }
}
