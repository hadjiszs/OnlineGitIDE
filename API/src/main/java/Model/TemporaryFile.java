package Model;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by p1317074 on 19/10/16.
 */

@Entity
//tous les fichiers d'un utilisateur par rapport un project

@NamedQueries({
        @NamedQuery(name = "TemporaryFile.findByIdAndUser", query = "SELECT f from TemporaryFile f WHERE f.user = :user AND f.hashKey = :hashKey"),
        @NamedQuery(name = "TemporaryFile.findByUserAndProject", query = "SELECT t FROM TemporaryFile t WHERE t.user = :user AND t.project = :project")
})
public class TemporaryFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String hashKey;

    @Lob
    private String content;

    @Column(nullable = false)
    private String path;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Project project;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private User user;

    public TemporaryFile(User user, String hashKey, String content, Project project,
                         String path) {
        this.content = content;
        this.hashKey = hashKey;
        this.user = user;
        this.project = project;
        this.path = path;
    }

    public TemporaryFile() {
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
}
