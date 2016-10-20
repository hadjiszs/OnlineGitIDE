package Service;

import org.json.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author Alexis Danizan
 * Crée l'arborescence d'un dossier
 */
public class FolderTraverse {

    /**
     * L'arborescence du dossier sous forme d'un linkedlist
     */
    private LinkedList FileTree = new LinkedList();


    /**
     * Construit l'arborescence d'un dossier
     * @param parent la racine de l'arborescence
     * @throws FileNotFoundException Si le dossier n'existe pas
     */
    public FolderTraverse(File parent) throws FileNotFoundException{
        // On vérifie si le fichier existe
        if(parent.exists()){
            FileTree = browse(parent);
        }else{
            throw new FileNotFoundException("Impossible de créer l'arborescence, le dossier n'existe pas.");
        }
    }

    /**
     * Parcours l'arborescence de façon récursive pour crée la linkedList
     * @param parent le dossier parent
     * @return la linkedList générée
     */
    private LinkedList browse(File parent){
        LinkedList ll = new LinkedList();
        if(parent.isDirectory()){
            ll.add(parent.getName());
            File childNodes[] = parent.listFiles();
            for (File childNode : childNodes) {
                ll.add(browse(childNode));
            }
        }else{
            ll.add(parent.getName());
        }
        return ll;
    }

    /**
     * Renvoi la linkedList de l'arborescence
     * @return la linkedList généré
     */
    public LinkedList getFileTree(){
        return FileTree;
    }

    /**
     * Renvoi sous forme d'une string l'arborescence de manière récursive
     * @param ll l'arborescence sous forme d'une linkedlist
     * @param leftIndent l'indentation à droite
     * @return l'arborescence en String
     */
    private String showFileTree(LinkedList ll, String leftIndent){
        String string = "";

        if(ll.size() == 1){ // on a un fichier
            string += (leftIndent + ll.getFirst().toString() + "\n");
        }else{ // on a un dossier

            // on ajoute le nom du dossier
            string += (leftIndent + ll.getFirst().toString() + "\n");
            leftIndent += "\t";
            // on parcours les élements du dossier
            ListIterator iterator = ll.listIterator(1);
            while (iterator.hasNext()){
                string += showFileTree((LinkedList)iterator.next(),leftIndent);
            }
        }
        return string;
    }

    /**
     * Renvoi sous forme d'un document JSON l'arborescence de manière récursive
     * @param ll l'arborescence sous forme d'une linkedlist
     * @return Le JSON de l'arborescence
     */
    private JSONObject toJsonFileTreeImpl(LinkedList ll){
        JSONObject jo = new JSONObject();
        if(ll.size() == 1){
            jo.put("file",ll.getFirst().toString());
        }else{
            JSONArray dir = new JSONArray();
            ListIterator iterator = ll.listIterator(1);
            while (iterator.hasNext()){
                LinkedList current = (LinkedList)iterator.next();
                if( current.size() == 1){
                    dir.put(current.getFirst().toString());
                }else{
                    dir.put(toJsonFileTreeImpl(current));
                }
            }
            jo.put(ll.getFirst().toString(),dir);
        }
        return jo;
    }

    /**
     * Renvoi l'arboresence sous forme d'un document JSON
     * @return l'arborescence en JSON
     */
    public JSONObject toJsonFileTree(){
        return toJsonFileTreeImpl(FileTree);
    }

    @Override
    public String toString(){
        return showFileTree(FileTree,"");
    }
}
