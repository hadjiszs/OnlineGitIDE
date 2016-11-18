package Git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.gitective.core.BlobUtils;
import org.gitective.core.CommitUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.String;
import javax.json.*;
import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import Util.ArboNode;
import Util.ArboTree;

import static org.eclipse.jgit.lib.Constants.HEAD;

/**
 * Created by p1317074 on 15/11/16.
 */

public class Util {

    /**
     * Recupere l'arborescence associé à un commit sujet
     *
     * @param creator l'id de l'utilisateur qui a créé le projet
     * @param repository l'id du repository
     * @param revision l'id de la revision (commit) dont on souhaite récuperer l'arborescence
     * @return un nouvel objet Json contenant l'arborescence du projet pour la révision donnée
     */
    public static JsonObject getArborescence(String creator,
                                             String repository,
                                             String revision) {

        try {
            //En local, les repo sont stockés dans REPOPATH/[createur]/[id_du_repo]
            Git git = Git.open(new File(Constantes.REPOPATH + creator + "/" + repository +".git"));

            // a RevWalk allows to walk over commits based on some filtering that is defined
            try  {
                RevWalk walk = new RevWalk(git.getRepository());
                RevCommit commit = CommitUtils.getCommit(git.getRepository(), revision);
                System.out.println(commit.toString());
                if (commit == null) {
                    throw new Exception("Can't find the given revision in the current repository");
                }

                RevTree tree = commit.getTree();

                // we use a TreeWalk to iterate over all files in the Tree recursively
                try {
                    TreeWalk treeWalk = new TreeWalk(git.getRepository());
                    treeWalk.addTree(tree);
                    treeWalk.setRecursive(true);

                    //On créé un objet ArboTree contenant l'arborescence voulue
                    ArboTree arborescence = new ArboTree(new ArboNode("root", "root"));
                    while (treeWalk.next()) {
                        arborescence.addElement(treeWalk.getPathString());
                    }
                    //On convertit cet objet en Json
                    return arborescence.toJson();

                } catch (Exception e) {
                    throw e;
                }
            } catch(Exception e) {
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Fonction permettant la suppression d'un dossier. utile à la fonction deleteRepository
     *
     * @param dir le dossier à supprimer
     * @return true si la suppression a été effectuée correctement, false sinon
     */
    private static boolean deleteDirectory(File dir) {
        if (!dir.exists() || !dir.isDirectory()) {
            return false;
        }
        String[] files = dir.list();
        for (int i = 0, len = files.length; i < len; i++) {
            File f = new File(dir, files[i]);
            if (f.isDirectory()) {
                deleteDirectory(f);
            } else {
                f.delete();
            }
        }
        return dir.delete();
    }

    /**
     * Suppression d'un repository
     *
     * @param creator l'id de l'utilisateur qui a créé le dépot
     * @param repository l'id du repo
     * @return True si le repo a été supprimé, false sinon
     */
    public static boolean deleteRepository(String creator,
                                           String repository) {
        File dir = new File(Constantes.REPOPATH + creator + "/" + repository +".git");
        return deleteDirectory(dir);
    }

    /**
     * Permet de cloner un repo distant en local
     *
     * @param creator id de l'utilisateur qui créé le repo
     * @param newRepo id du nouveau Repo
     * @param remoteURL URL du repo distant
     * @throws Exception
     */
    public static void cloneRemoteRepo(String creator,
                                       String newRepo,
                                       String remoteURL) throws Exception {
        if (newRepo == null) {
            String[] list = remoteURL.split("/");
            newRepo = list[list.length - 1];
        }

        // prepare a new folder for the cloned repository
        File localPath = new File(Constantes.REPOPATH + creator + "/" + newRepo + ".git");

        // then clone
        System.out.println("Cloning from " + remoteURL + " to " + localPath);
        Git git = Git.cloneRepository()
                .setURI(remoteURL)
                .setDirectory(localPath)
                .call();
    }

    /**
     * Retourne le contenu d'un fichier
     *
     * @param creator l'id de l'utilisateur qui a créé le dépot
     * @param repo l'id du repo
     * @param revision
     * @param path
     * @return
     * @throws Exception
     */
    public static JsonObject getContent(String creator,
                                        String repo,
                                        String revision,
                                        String path) throws Exception {
        Git git = Git.open(new File(Constantes.REPOPATH + creator + "/" + repo +".git"));
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject ret = factory.createObjectBuilder()
                .add("content", BlobUtils.getContent(git.getRepository(), revision, path))
                .build();
        return ret;
    }

    /**
     * Créer une branche
     *
     * /git/<creator>/<depot>/create/branch/<branch>
     *
     * @param creator utilisateur proprietaire du depot
     * @param repo    nom du depot
     * @param branch  nom de la branche à créer
     * @return un code de réponse renvoyé un json
     * @throws Exception
     */
    public static JsonObject createBranch(String creator,
                                          String repo,
                                          String branch) throws Exception {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        GitStatus status;

        // Ouverture du depot
        Git git = Git.open(new File(Constantes.REPOPATH + creator + "/" + repo + ".git"));

        // Verification que le nom de branche n'existe pas deja dans le depot
        boolean branchExiste = false;
        // Verifier que la branche n existe pas deja
        List<Ref> refs = git.branchList().call();
        for(Ref ref : refs) {
            if(ref.getName().equals("refs/heads/"+ branch)) {
                branchExiste = true;
                break;
            }
        }

        if(branchExiste == false) {
            status = GitStatus.BRANCH_CREATED;
            // On cree la branche
            git.branchCreate()
                    .setName(branch)
                    .call();
        } else
            status = GitStatus.BRANCH_NOT_CREATED;

        JsonObject ret = factory.createObjectBuilder()
                .add("code", status.getValue())
                .build();

        return ret;
    }


    /**
     * Créer une branche
     *
     * @param creator utilisateur proprietaire du depot
     * @param repo    nom du depot
     * @return un code de réponse renvoyé un json
     * @throws Exception
     */
    public static JsonObject createRepository(String creator,
                                              String repo) throws Exception {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        GitStatus status = GitStatus.REPOSITORY_NOT_CREATED;

        // Chemin vers le nouveau repository
        String path = Constantes.REPO_FULLPATH + creator + "/" + repo;
        System.out.println("CHEMIN:" + path);
        File localPath = new File(path);

        // Création du dépot
        Git git = Git.init().setDirectory(localPath).call();

        if(git.getRepository().getRef(HEAD) != null)
            status = GitStatus.REPOSITORY_CREATED;

        JsonObject ret = factory.createObjectBuilder()
                .add("code", status.getValue())
                .build();

        return ret;
    }

    /**
     * Montre les diff entre un commit et son/ses parent(s)
     *
     * /git/<creator>/<depot>/showCommit/<revision>
     *
     * @param creator   utilisateur proprietaire du depot
     * @param repo      nom du depot
     * @param revision  string id du commit sujet (le commit sujet est le commit dont on veut le diff avec ses parents)
     * @return un code de réponse renvoyé un json
     * @throws Exception
     */
    public static JsonObject showCommit(String creator,
                                        String repo,
                                        String revision) throws Exception {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DiffFormatter formatter = new DiffFormatter(baos);

        // Ouverture du depot
        Git git = Git.open(new File(Constantes.REPOPATH + creator + "/" + repo + ".git"));
        Repository repository = git.getRepository();

        // Recuperation du RevCommit associé au commit sujet
        RevWalk walk = new RevWalk(repository);
        RevCommit commit = walk.parseCommit(ObjectId.fromString(revision));

        // Arborescence du commit
        AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, commit);

        // Pour chaque commit parent du commit sujet, on recupere le diff
        for(RevCommit parent : commit.getParents()) {
            AbstractTreeIterator newTreeParser = prepareTreeParser(repository, parent);

            List<DiffEntry> diff = git.diff()
                    .setOldTree(oldTreeParser)
                    .setNewTree(newTreeParser)
                    .call();

            formatter.setRepository(repository);
            for (DiffEntry entry : diff)
                formatter.format(entry);
        }

        JsonObject ret = factory.createObjectBuilder()
                .add("result", baos.toString( String.valueOf(Charset.defaultCharset())) )
                .build();

        return ret;
    }

    /**
     *
     * @param repository
     * @param objectId
     * @return
     * @throws IOException
     */
    private static AbstractTreeIterator prepareTreeParser(Repository repository,
                                                          RevCommit objectId) throws IOException {
        RevWalk walk = new RevWalk(repository);
        RevCommit commit = walk.parseCommit(objectId);

        RevTree tree = walk.parseTree(commit.getTree().getId());

        CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
        ObjectReader oldReader = repository.newObjectReader();
        oldTreeParser.reset(oldReader, tree.getId());

        walk.dispose();

        return oldTreeParser;
    }

    /**
     *
     * @param creator
     * @param repo
     * @return
     * @throws Exception
     */
    public static  JsonObject getBranches(String creator,
                                          String repo) throws Exception {

        Git git = Git.open(new File(Constantes.REPOPATH + creator + "/" + repo + ".git"));
        JsonBuilderFactory factory = Json.createBuilderFactory(null);

        List<Ref> call = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
        JsonArrayBuilder build = factory.createArrayBuilder();

        for(Ref ref : call) {
            //System.out.println(ref.getName());
            build.add(factory.createObjectBuilder().add("name", ref.getName()));
        }

        return factory.createObjectBuilder().add("branches", build).build();
    }

    /**
     *
     * @param creator
     * @param repo
     * @param branch
     * @return
     * @throws Exception
     */
    public static JsonObject getCommits(String creator,
                                        String repo,
                                        String branch) throws  Exception {
        Git git = Git.open(new File(Constantes.REPOPATH + creator + "/" + repo + ".git"));
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        Iterable<RevCommit> commits = git.log().all().call();
        JsonArrayBuilder build = factory.createArrayBuilder();

        Iterable<RevCommit> revCommits = git.log()
                .add(git.getRepository().resolve(branch))
                .call();

        for(RevCommit revCommit : revCommits){
            build.add(factory.createObjectBuilder().add("id", revCommit.getName()));
        }

        return factory.createObjectBuilder().add("commits", build).build();
    }

}