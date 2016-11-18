package Git;

import org.eclipse.jgit.api.Git;

/**
 * Created by hadjiszs on 17/11/16.
 */
public enum GitStatus {

    // Liste des codes de retour pour le module git
    BRANCH_NOT_CREATED(9400, "Nom de branche déjà existant"),
    BRANCHE_CREATED(9200, "Branche créée");

    private final int value;
    private final String descr;

    GitStatus(int value, String descr) {
        this.value = value;
        this.descr = descr;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
