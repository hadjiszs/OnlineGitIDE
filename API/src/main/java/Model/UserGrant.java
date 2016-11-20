package Model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
@Entity
@Table(name = "User_Grant")
@NamedQueries(value = {
        @NamedQuery(name = "findByUser", query = "SELECT g FROM UserGrant g WHERE g.userId = :id"),
        @NamedQuery(name = "findProjectsByUserType", query = "SELECT g FROM UserGrant g WHERE g.projectId = :id AND gran = :type")
})
@IdClass(UserGrantID.class)
public class UserGrant implements Serializable {

    public enum Permis {Admin, Dev};

    @MapsId("id")
    @Id
    private Long projectId;

    @MapsId("id")
    @Id
    private Long userId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('Admin', 'Dev')", nullable = false)
    private Permis gran; /* On ne peut pas utiliser grant parce que c'est une mot clé de Mysql */

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "projectId", insertable = false, updatable = false)
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        setProjectId(project.getIdProject());
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        setUserId(user.getIdUser());
        this.user = user;
    }

    public Permis getGran() {
        return gran;
    }

    public void setGran(Permis gran) {
        this.gran = gran;
    }
}
