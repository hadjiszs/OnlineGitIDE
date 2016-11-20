package Util;


import javax.json.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by p1317074 on 16/11/16.
 */
public class ArboTree {

    ArboNode root;
    ArboNode commonRoot;

    public ArboTree(ArboNode root) {
        this.root = root;
        commonRoot = null;
    }

    public void addElement(String elementValue) {
        String[] list = elementValue.split("/");

        // latest element of the list is the filename.extrension
        root.addElement(root.incrementalPath, list);

    }

    public void printTree() {
        //I move the tree common root to the current common root because I don't mind about initial folder
        //that has only 1 child (and no leaf)
        getCommonRoot();
        commonRoot.printNode(0);
    }

    public JsonObject toJson() {
        //StringWriter sw = new StringWriter();
        Map<String, Object> config = new HashMap<String, Object>();
        //config.put(JsonGenerator.PRETTY_PRINTING, true);
        //JsonWriterFactory jf = Json.createWriterFactory(config);
        //JsonWriter jw = jf.createWriter(sw);

        JsonBuilderFactory factory = Json.createBuilderFactory(config);
        JsonArrayBuilder root = factory.createArrayBuilder();
        getCommonRoot();

        commonRoot.toJson(root, factory);
        JsonObject ret = factory.createObjectBuilder().add("root", root).build();
        //jw.writeObject(ret);
        //jw.close();

        //System.out.println(sw.toString());
        return ret;
    }

    public ArboNode getCommonRoot() {
        if (commonRoot != null)
            return commonRoot;
        else {
            ArboNode current = root;
            while (current.leafs.size() <= 0) {
                current = current.childs.get(0);
            }
            commonRoot = current;
            return commonRoot;
        }

    }

}