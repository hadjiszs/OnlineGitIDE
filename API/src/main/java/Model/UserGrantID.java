package Model;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public class UserGrantID implements Serializable {
    @Id
    private Long projectId;

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
}
