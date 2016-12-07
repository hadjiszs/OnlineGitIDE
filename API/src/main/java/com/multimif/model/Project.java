package com.multimif.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Classe pour la définition du projet.
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p "),
        @NamedQuery(name = "Project.byNameByUser", query = "SELECT p FROM Project p WHERE p.name = :name")
})
public class Project implements Serializable {
    public enum TypeProject {JAVA, MAVEN, CMAKE}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* Attention: Le nom de l'id du project doit être differente à 'id' */
    private Long idProject;

    private String name;

    private Long idCreator;

    @Column(columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModification;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('JAVA', 'MAVEN', 'CMAKE')")
    private TypeProject type;

    public Project(){
        /* On ajoute le constructeur par défaut pour pouvoir instancier des listes*/
    }

    /**
     *
     * Constructeur du projet
     *
     * @param name le nom du projet
     * @param type le type du code
     */
    public Project(String name, TypeProject type, Long idCreator) {
        this.name = name;
        this.type = type;
        this.creationDate = new Date();
        this.lastModification = new Date();
        this.idCreator = idCreator;
    }

    public Long getIdProject() {
        return idProject;
    }

    public void setIdProject(Long id) {
        this.idProject = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public TypeProject getType() {
        return type;
    }

    public void setType(TypeProject type) {
        this.type = type;
    }

    public void setIdCreator(Long id){ this.idCreator = id;}

    public Long getIdCreator(){ return idCreator;}
}
