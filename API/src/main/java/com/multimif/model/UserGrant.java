package com.multimif.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * Classe pour la définition de permis entre utilisateurs et projets
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/16.
 */
@Entity
@Table(name = "User_Grant")
@NamedQueries(value = {
        @NamedQuery(name = "findByUser", query = "SELECT g FROM UserGrant g WHERE g.userId = :id"),
        @NamedQuery(name = "findProjectsByUserType", query = "SELECT g FROM UserGrant g WHERE g.user = :user " +
                "AND permissionType = :type"),
        @NamedQuery(name = "findUsersByProjectByType", query = "SELECT g FROM UserGrant g WHERE g.projectId = :id " +
                "AND permissionType = :type")
})
@IdClass(UserGrantID.class)
public class UserGrant implements Serializable {

    public enum PermissionType {ADMIN, DEVELOPER}

    @MapsId("id")
    @Id
    private Long projectId;

    @MapsId("id")
    @Id
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ADMIN', 'DEVELOPER')", nullable = false)
    private PermissionType permissionType; /* On ne peut pas utiliser grant parce que c'est une mot clé de Mysql */

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "projectId", insertable = false, updatable = false)
    private Project project;

    public Long getProjectId() {
        return projectId;
    }

    private void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    private void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public PermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(PermissionType gran) {
        this.permissionType = gran;
    }
}
