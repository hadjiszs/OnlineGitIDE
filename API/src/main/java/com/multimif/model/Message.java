package com.multimif.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * Classe metier pour les messages du chat
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/16.
 */
@Entity
public class Message implements Serializable {
    @Id
    @Column(columnDefinition = "INTEGER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String contenu;

    @Column(columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Message(User user, String contenu, Date date) {
        this.user = user;
        this.contenu = contenu;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
