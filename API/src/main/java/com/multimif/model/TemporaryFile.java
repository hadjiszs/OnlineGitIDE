package com.multimif.model;

import javax.persistence.*;
import java.io.Serializable;

import static com.multimif.util.SplitPath.getFileExtension;
import static com.multimif.util.SplitPath.getFileName;


/**
 * Cette classe c'est pour tous les fichiers d'un utilisateur par rapport un project
 *
 * @author p1317074
 * @version 1.0
 * @since 1.0 19/10/16.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = "TemporaryFile.findByHashkey", query = "SELECT f from TemporaryFile f WHERE f.hashKey = :hashKey"),
        @NamedQuery(name = "TemporaryFile.findByUserAndProject", query = "SELECT t FROM TemporaryFile t WHERE t.user = :user AND t.project = :project")
})
public class TemporaryFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String hashKey;

    @Transient
    private String name;

    @Transient
    private String extension;

    @Lob
    private String content;

    @Column(nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name = "idProject")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    /**
     * Constructeur du temporaryFile
     *
     * @param user    utilisateur proprietaire du fichier
     * @param content du fichier
     * @param project associé au fichier
     * @param path    dans le dépôt
     */
    public TemporaryFile(User user, String content, Project project,
                         String path) {
        String raw = user.getIdUser().toString() +
                project.getIdProject().toString() +
                path;

        this.content = content;
        this.hashKey = String.valueOf(raw.hashCode());
        this.user = user;
        this.project = project;
        this.path = path;
        // Pas de path a la racine
        if(path.lastIndexOf("/") == -1){
            // pas d'extension
            if(path.lastIndexOf(".") == -1){
                this.name = path;
                this.extension = "";
            }else{
                this.name = path.substring(0, path.lastIndexOf(".")-1);
                this.extension = path.substring(path.lastIndexOf(".")+1);
            }
        }else{
            // pas d'extension
            if(path.lastIndexOf(".") == -1){
                this.name = path;
                this.extension = "";
            }else{
                this.name = path.substring(path.lastIndexOf("/")+1, path.lastIndexOf(".")-1);
                this.extension = path.substring(path.lastIndexOf(".")+1);
            }
        }
    }


    public TemporaryFile() {
        /**
         * On construit un constructeur vide pour pouvoir déclarer
         * des listes avec ce type là
         */
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String contenu) {
        this.content = contenu;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setIdFichier(String hashKey) {
        this.hashKey = hashKey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {

        if (name == null) {
            this.name = getFileName(this.path);
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {

        if (this.extension == null) {

            this.extension = getFileExtension(this.path);
        }


        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setExtensionType(ExtensionType extensionType) {
        setExtension(extensionType.getExtension().toUpperCase());
    }


}
