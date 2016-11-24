package com.multimif.git;

import com.multimif.controller.UserController;
import com.multimif.util.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.ResolveMerger;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.gitective.core.BlobUtils;
import org.gitective.core.CommitUtils;

import javax.json.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.eclipse.jgit.lib.Constants.HEAD;

/**
 * @author p1317074
 * @version 1.0
 * @since 1.0 15/11/16.
 */

public class Util {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());


    private Util(){
        /* On cache le constructeur parce qu'il s'agit d'une classe utilitaire */
    }

    /**
     * Recupere l'arborescence associé à un commit sujet
     *
     ** @param creator l'utilisateur qui a créé le projet
     * @param repository le dépôt
     * @param revision l'id de la revision (commit) dont on souhaite récuperer l'arborescence
     * @return un nouvel objet Json contenant l'arborescence du projet pour la révision donnée
     */
    public static JsonObject getArborescence(String creator,
                                             String repository,
                                             String revision) {

        try {
            //En local, les repo sont stockés dans REPOPATH/[createur]/[id_du_repo]
            String path = Constantes.REPO_FULLPATH + creator + "/" + repository + ".git";
            Git git = Git.open(new File(path));

            // a RevWalk allows to walk over commits based on some filtering that is defined
            RevCommit commit = CommitUtils.getCommit(git.getRepository(), revision);
            RevTree tree = commit.getTree();

            // we use a TreeWalk to iterate over all files in the Tree recursively

            TreeWalk treeWalk = new TreeWalk(git.getRepository());
            treeWalk.addTree(tree);
            treeWalk.setRecursive(true);

            //On créé un objet ArboTree contenant l'arborescence voulue
            ArboTree arborescence = new ArboTree(new ArboNode("root", "root"));
            if (treeWalk.next()) {
                do {
                    arborescence.addElement(treeWalk.getPathString());
                } while (treeWalk.next());
            }
            //On convertit cet objet en Json
            return arborescence.toJson();

        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return null;
        }

    }

    /**
     * Fonction permettant la suppression d'un dossier. utile à la fonction deleteRepository
     *
     * @param dir le dossier à supprimer
     * @return true si la suppression a été effectuée correctement, false sinon
     * @throws DataException retourne une exception si le fichier n'a pas été supprimé.
     */
    private static boolean deleteDirectory(File dir) throws DataException {
        if (!dir.exists() || !dir.isDirectory()) {
            return false;
        }
        String[] files = dir.list();
        for (String file : files != null ? files : new String[0]) {
            File f = new File(dir, file);
            if (f.isDirectory()) {
                deleteDirectory(f);
            } else {
                if (!f.delete())
                    throw new DataException("The file has not been deleted");
            }
        }
        return dir.delete();
    }

    /**
     * Suppression d'un repository
     *
     ** @param creator le proprietaire du dépôt, le pseudo de l'utilisateur
     * @param repository le dépôt
     * @return True si le repo a été supprimé, false sinon
     * @throws DataException
     */
    public static boolean deleteRepository(String creator,
                                           String repository) throws DataException {
        String path = getGitRepo(creator, repository);
        File dir = new File(path);

        return deleteDirectory(dir);
    }

    /**
     * Permet de cloner un repo distant en local
     *
     ** @param creator le proprietaire du dépôt
     * @param newRepo le nom du dépôt
     * @param remoteURL URL du repo distant
     * @throws Exception
     */
    public static void cloneRemoteRepo(String creator,
                                       String newRepo,
                                       String remoteURL) throws DataException {

        String repository;
        if (newRepo == null) {
            String[] list = remoteURL.split(File.separator);
            repository = list[list.length - 1];
        } else
            repository = newRepo;

        // prepare a new folder for the cloned repository
        String path = getGitRepo(creator, repository);

        File localPath = new File(path);

        // then clone
        try {
            Git.cloneRepository().setURI(remoteURL)
                    .setDirectory(localPath)
                    .call();
        } catch (GitAPIException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            throw new DataException(Messages.GIT_CANT_CLONE_REPOSITORY);
        }
    }

    /**
     * Retourne le contenu d'un fichier
     *
     ** @param creator le proprietaire du dépôt
     * @param repo le nom du dépôt
     * @param revision la revision spécifiée la revision
     * @param path l'addresse
     * @return
     * @throws IOException
     */
    public static JsonObject getContent(String creator,
                                        String repo,
                                        String revision,
                                        String path) throws IOException {
        String pathRepo = getGitRepo(creator, repo);
        Git git = Git.open(new File(pathRepo));

        JsonBuilderFactory factory = Json.createBuilderFactory(null);

        return factory.createObjectBuilder()
                .add("content", BlobUtils.getContent(git.getRepository(), revision, path))
                .build();
    }

    /**
     * Créer une branche
     * <p>
     * /git/<creator>/<depot>/create/branch/<branch>
     *
     ** @param creator le proprietaire du dépôt
     * @param repo le dépôt
     * @param branch le nom de la branche à créer
     * @return un code de réponse renvoyé un json
     * @throws DataException retourne une exception si le dépôt n'existe pas
     */
    public static JsonObject createBranch(String creator,
                                          String repo,
                                          String branch) throws DataException {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        GitStatus status;

        // Ouverture du depot
        String pathRepo = getGitRepo(creator, repo);
        Git git;
        try {
            git = Git.open(new File(pathRepo));
        } catch (IOException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            throw new DataException(Messages.GIT_CANT_OPEN_REPOSITORY);
        }

        // Verification que le nom de branche n'existe pas deja dans le depot
        boolean branchExiste = false;

        // Verifier que la branche n existe pas deja
        List<Ref> refs;
        try {
            refs = git.branchList().call();
        } catch (GitAPIException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            throw new DataException(Messages.GIT_CANT_LIST_BRANCH);
        }
        for (Ref ref : refs) {
            if (ref.getName().equals("refs/heads/" + branch)) {
                branchExiste = true;
                break;
            }
        }

        if (!branchExiste) {
            status = GitStatus.BRANCH_CREATED;
            // On cree la branche
            try {
                git.branchCreate()
                        .setName(branch)
                        .call();
            }catch (Exception e){
                LOGGER.log(Level.FINE, e.getMessage(), e);
                throw new DataException(Messages.GIT_BRANCH_CANT_CREATED);
            }
        } else
            status = GitStatus.BRANCH_NOT_CREATED;

        return factory.createObjectBuilder()
                .add("code", status.getValue())
                .build();
    }


    /**
     * Créer une branche
     *
     ** @param creator le proprietaire du dépôt, le pseudo de l'utilisateur
     * @param repo le nom du dépôt
     * @return un code de réponse renvoyé un json
     * @throws Exception
     */
    public static JsonObject createRepository(String creator,
                                              String repo) throws Exception {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        GitStatus status = GitStatus.REPOSITORY_NOT_CREATED;

        // Chemin vers le nouveau repository
        String path = getGitRepo(creator, repo);
        File localPath = new File(path);

        // Création du dépot
        Git git = Git.init().setDirectory(localPath).call();

        if (git.getRepository().getRef(HEAD) != null)
            status = GitStatus.REPOSITORY_CREATED;

        File myfile = new File(path, "README");
        if (!myfile.createNewFile()) {
            throw new IOException("Could not create file " + myfile);
        }

        // run the add-call
        git.add()
                .addFilepattern("README")
                .call();

        git.commit()
                .setMessage("add .README")
                .call();


        return factory.createObjectBuilder()
                .add("code", status.getValue())
                .build();
    }

    /**
     * Montre les diff entre un commit et son/ses parent(s)
     * <p>
     * /git/<creator>/<depot>/showCommit/<revision>
     *
     ** @param creator le proprietaire du dépôt, le pseudo de l'utilisateur
     * @param repo le nom du depot
     * @param revision la revision spécifiée string id du commit sujet (
     *                 le commit sujet est le commit dont on veut le diff avec ses parents)
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
        String pathRepo = getGitRepo(creator, repo);
        Git git = Git.open(new File(pathRepo));
        Repository repository = git.getRepository();

        // Recuperation du RevCommit associé au commit sujet
        RevWalk walk = new RevWalk(repository);
        RevCommit commit = walk.parseCommit(ObjectId.fromString(revision));

        // Arborescence du commit
        AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, commit);

        // Pour chaque commit parent du commit sujet, on recupere le diff
        for (RevCommit parent : commit.getParents()) {
            AbstractTreeIterator newTreeParser = prepareTreeParser(repository, parent);

            List<DiffEntry> diff = git.diff()
                    .setOldTree(oldTreeParser)
                    .setNewTree(newTreeParser)
                    .call();

            formatter.setRepository(repository);
            for (DiffEntry entry : diff)
                formatter.format(entry);
        }

        return factory.createObjectBuilder()
                .add("result", baos.toString(String.valueOf(Charset.defaultCharset())))
                .build();
    }

    /**
     ** @param creator le proprietaire du dépôt
     * @param repo le dépôt le dépôt
     * @param branch le nom de la branche
     * @return un objet json avec le nom du fichier
     * @throws DataException retourne un exception si le dépôt n'existe pas
     */
    public static JsonObject getArchive(String creator, String repo, String branch) throws DataException {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        Git git;
        String pathRepository = getGitRepo(creator, repo);

        String zipName = repo + Constantes.ZIP_EXTENSION;

        try {
            git = Git.open(new File(pathRepository));

            git.checkout().setCreateBranch(false)
                    .setName(branch)
                    .call();

            String zip = getZipFile(creator, repo);
            ZipUtil.compress(pathRepository, zip);
            ZipUtil.moveZipFile(zip, zipName);

        } catch (IOException | GitAPIException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            throw new DataException(Messages.GIT_REPOSITORY_NOT_EXISTS);
        }

        return factory.createObjectBuilder()
                .add("file", zipName)
                .build();
    }

    /**
     * Retourne le path du repository
     *
     * @param creator le proprietaire du dépôt, le pseudo de l'utilisateur
     * @param repo le dépôt le dépôt
     * @return l'addresse du dépôt
     */
    private static String getGitRepo(String creator, String repo) {
        return new StringBuilder().append(Constantes.REPO_FULLPATH)
                .append(creator).append(File.separator).append(repo).append(Constantes.GIT_EXTENSION).toString();
    }


    /**
     * Retourne l'addresse et le nom du fichier ZIP
     *
     * @param creator le proprietaire du dépôt, le pseudo de l'utilisateur
     * @param repo le dépôt le dépôt   le dépôt
     * @return l'addresse du fichier ZIP
     */
    private static String getZipFile(String creator, String repo) {
        return new StringBuilder().append(Constantes.REPO_FULLPATH).append(creator)
                .append(File.separator).append(repo).append(Constantes.ZIP_EXTENSION).toString();
    }

    /**
     * @param repository le dépôt
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
     * @param creator le proprietaire du dépôt
     * @param repo le dépôt
     * @return
     * @throws Exception
     */
    public static JsonObject getBranches(String creator,
                                         String repo) throws Exception {
        String pathRepo = getGitRepo(creator, repo);
        Git git = Git.open(new File(pathRepo));
        JsonBuilderFactory factory = Json.createBuilderFactory(null);

        List<Ref> call = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
        JsonArrayBuilder build = factory.createArrayBuilder();

        for (Ref ref : call) {
            build.add(factory.createObjectBuilder().add("name", ref.getName()));
        }

        return factory.createObjectBuilder().add("branches", build).build();
    }

    /**
     * @param creator le proprietaire du dépôt
     * @param repo le dépôt
     * @param branch le nom de la branche
     * @return
     * @throws DataException
     */
    public static JsonObject getCommits(String creator,
                                        String repo,
                                        String branch) throws DataException {
        String pathRepo = getGitRepo(creator, repo);
        Git git;
        try {
            git = Git.open(new File(pathRepo));
        } catch (IOException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            throw new DataException(Messages.GIT_CANT_OPEN_REPOSITORY);
        }

        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonArrayBuilder build = factory.createArrayBuilder();

        Iterable<RevCommit> revCommits;
        try {
            revCommits = git.log()
                    .add(git.getRepository().resolve(branch))
                    .call();
        } catch (IOException | GitAPIException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            throw new DataException(Messages.GIT_LOG_ERROR);
        }

        for (RevCommit revCommit : revCommits) {
            build.add(factory.createObjectBuilder()
                    .add("id", revCommit.getName())
                    .add("id", revCommit.getName())
                    .add("date", revCommit.getCommitTime())
                    .add("message", revCommit.getFullMessage())
                    .add("user", revCommit.getCommitterIdent().getName())
                    .add("email", revCommit.getCommitterIdent().getEmailAddress())
                    .add("timeZone", revCommit.getCommitterIdent().getTimeZone().getDisplayName())
                    .add("offsetTimeZone", revCommit.getCommitterIdent().getTimeZoneOffset()));
        }

        return factory.createObjectBuilder().add("commits", build).build();
    }

    /**
     * @param creator le proprietaire du dépôt
     * @param repo le dépôt
     * @param nomBranch1 e nom de la branche1
     * @param branch2 le nom de la branche2
     * @return
     * @throws Exception
     */
    public static JsonObject merge(String creator, String repo, String nomBranch1, String branch2) throws Exception {
        Git git = Git.open(new File(Constantes.REPOPATH + creator + "/" + repo + ".git"));
        JsonBuilderFactory factory = Json.createBuilderFactory(null);

        // Recuperation du RevCommit associé au commit sujet
        RevWalk walk = new RevWalk(git.getRepository());
        RevCommit commit = walk.parseCommit(ObjectId.fromString(branch2));


        git.checkout().setCreateBranch(false)
                .setName(nomBranch1)
                .call();


        MergeResult result = git.merge()
                .include(commit)
                .setStrategy(MergeStrategy.RESOLVE)
                .call();

        JsonObjectBuilder res = factory.createObjectBuilder().add("status", result.getMergeStatus().toString());
        JsonArrayBuilder reasons = factory.createArrayBuilder();
        JsonArrayBuilder conflictsJson = factory.createArrayBuilder();

        if (!result.getMergeStatus().isSuccessful()) {
            Map<String, ResolveMerger.MergeFailureReason> failingPaths = result.getFailingPaths();
            if (failingPaths != null) {
                for (Map.Entry<String, ResolveMerger.MergeFailureReason> entry : failingPaths.entrySet()) {
                    reasons.add(factory.createObjectBuilder().add("path", entry.getKey()).build());
                    reasons.add(factory.createObjectBuilder().add("failure reason", entry.getValue().toString()));
                }
            }

            Map<String, int[][]> conflicts = result.getConflicts();

            JsonArrayBuilder files = factory.createArrayBuilder();
            /*TODO refactor */
            for (String path : conflicts.keySet()) {
                int[][] c = conflicts.get(path);
                JsonArrayBuilder conflictList = factory.createArrayBuilder();
                for (int i = 0; i < c.length; ++i) {
                    JsonArrayBuilder details = factory.createArrayBuilder();
                    for (int j = 0; j < (c[i].length) - 1; ++j) {
                        if (c[i][j] >= 0) {
                            details.add(factory.createObjectBuilder()
                                    .add("commit", result.getMergedCommits()[j].getName())
                                    .add("line", c[i][j]));
                        }
                    }
                    conflictList.add(factory.createObjectBuilder()
                            .add("conflict_no", i)
                            .add("details", details));
                }
                files.add(factory.createObjectBuilder()
                        .add("path", path)
                        .add("conflictList", conflictList));
            }
            conflictsJson.add(factory.createObjectBuilder().add("files", files));

            res.add("failure reasons", reasons);
            res.add("conflicts", conflictsJson);
        }

        return res.build();
    }

    /**
     * @param creator le proprietaire du dépôt
     * @param repo le dépôt
     * @param revision la revision spécifiée
     * @return
     * @throws IOException
     */
    public static JsonObject getInfoCommit(String creator, String repo, String revision) throws IOException {
        String pathRepo = getGitRepo(creator, repo);
        Git git = Git.open(new File(pathRepo));
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonArrayBuilder build = factory.createArrayBuilder();

        RevCommit commit = CommitUtils.getCommit(git.getRepository(), revision);

        build.add(factory.createObjectBuilder()
                .add("id", commit.getName())
                .add("date", commit.getCommitTime())
                .add("message", commit.getFullMessage())
                .add("user", commit.getCommitterIdent().getName())
                .add("email", commit.getCommitterIdent().getEmailAddress())
                .add("timeZone", commit.getCommitterIdent().getTimeZone().getDisplayName())
                .add("offsetTimeZone", commit.getCommitterIdent().getTimeZoneOffset())
        );

        return factory.createObjectBuilder().add("information", build).build();
    }


}