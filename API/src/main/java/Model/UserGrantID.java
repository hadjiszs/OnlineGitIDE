package main.java.Model;

import javax.persistence.Id;
import java.io.Serializable;
/**
 * Created by amaia.nazabal on 10/20/16.
 */
public class UserGrantID implements Serializable {
    @Id
    private Long projetId;

    @Id
    private String mail;

    public UserGrantID(){

    }
}
