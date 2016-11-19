package Git;

/**
 * Created by hadjiszs on 17/11/16.
 */
public enum GitStatus {

    // Liste des codes de retour pour le module git
    REPOSITORY_NOT_CREATED(8400, "Repository créé"),
    REPOSITORY_CREATED(8200, "Repository créé"),
    BRANCH_NOT_CREATED(9400, "Nom de branche déjà existant"),
    BRANCH_CREATED(9200, "Branche créée");

    public final int value;

    private final String descr;

    GitStatus(int value, String descr) {
        this.value = value;
        this.descr = descr;
    }

    public int getValue() {
        return value;
    }

    public String getDescr() {
        return descr;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
