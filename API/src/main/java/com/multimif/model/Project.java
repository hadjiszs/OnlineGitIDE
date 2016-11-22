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
@NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p ")
public class Project implements Serializable {
    public enum TypeProject {JAVA, MAVEN, C, CPP}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* Attention: Le nom de l'id du project doit être differente à 'id' */
    private Long idProject;

    private String name;

    @Column(columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModification;

    private String version;

    private String root;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('JAVA', 'MAVEN', 'C', 'CPP')")
    private TypeProject type;

    public Project(){

    }

    /**
     *
     * Constructeur du projet
     *
     * @param name le nom du projet
     * @param version la version du projet
     * @param type le type du code
     * @param root l'address du projet
     */
    public Project(String name, String version, TypeProject type, String root) {
        this.name = name;
        this.version = version;
        this.type = type;
        this.root = root;
        this.creationDate = new Date();
        this.lastModification = new Date();
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

    public String getRoot() { return root; }

    public void setRoot(String root) {this.root = root; }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public TypeProject getType() {
        return type;
    }

    public void setType(TypeProject type) {
        this.type = type;
    }
}
