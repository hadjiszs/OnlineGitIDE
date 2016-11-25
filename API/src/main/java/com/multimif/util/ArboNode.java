package com.multimif.util; /**
 * @author p1317074
 * @version 1.0
 * @since 1.0 16/11/16.
 */

import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ArboNode {

    List<ArboNode> childs;
    List<ArboNode> leafs;
    String data;
    String incrementalPath;

    public ArboNode(String nodeValue, String incrementalPath) {
        childs = new ArrayList<ArboNode>();
        leafs = new ArrayList<ArboNode>();
        data = nodeValue;
        this. incrementalPath = incrementalPath;
    }

    public boolean existElement(String[] list, int depth) {
        //System.out.println(data + " != " + list[depth]);
        //System.out.println((depth + 1) + " > " + list.length);
        if (depth + 1 > list.length || !data.equals(list[depth])) {
            //System.out.println("nope");
            return false;
        }
        if (depth + 1 == list.length && data.equals(list[depth])) {
            return true;
        }
        boolean ret = false;
        for (ArboNode node : childs) {
            ret = ret || node.existElement(list, depth +1);
        }
        for (ArboNode node : leafs) {
            ret = ret || node.existElement(list, depth +1);
        }

        return ret;
    }

    public boolean isLeaf() {
        return childs.isEmpty() && leafs.isEmpty();
    }

    public void addElement(String currentPath, String[] list) {

        //Avoid first element that can be an empty string if you split a string that has a starting slash as /sd/card/
        while( list[0] == null || list[0].equals("") )
            list = Arrays.copyOfRange(list, 1, list.length);

        ArboNode currentChild = new ArboNode(list[0], currentPath+"/"+list[0]);
        if ( list.length == 1 ) {
            leafs.add( currentChild );
            return;
        } else {
            int index = childs.indexOf( currentChild );
            if ( index == -1 ) {
                childs.add( currentChild );
                currentChild.addElement(currentChild.incrementalPath, Arrays.copyOfRange(list, 1, list.length));
            } else {
                ArboNode nextChild = childs.get(index);
                nextChild.addElement(currentChild.incrementalPath, Arrays.copyOfRange(list, 1, list.length));
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        ArboNode cmpObj = (ArboNode)obj;
        return incrementalPath.equals( cmpObj.incrementalPath ) && data.equals( cmpObj.data );
    }

    public void printNode( int increment) {
        for (int i = 0; i < increment; i++) {
            System.out.print(" ");
        }
        System.out.println(incrementalPath + (isLeaf() ? " -> " + data : "")  );
        for( ArboNode n: childs)
            n.printNode(increment+2);
        for( ArboNode n: leafs)
            n.printNode(increment+2);
    }

    public void toJson(JsonArrayBuilder jb, JsonBuilderFactory factory ) {
        if (!isLeaf()) {
            JsonArrayBuilder children = factory.createArrayBuilder();

            for (ArboNode n : childs)
                n.toJson(children, factory);
            for (ArboNode n : leafs)
                n.toJson(children, factory);

            jb.add(factory.createObjectBuilder()

                            .add("name", this.data)
                            .add("path", this.incrementalPath)
                            .add("type", "dir")
                            .add("children", children)
            );
        } else {
            jb.add(factory.createObjectBuilder()
                    .add("name", this.data)
                    .add("path", this.incrementalPath)
                    .add("type", "file"));

        }


    }

    @Override
    public String toString() {
        return data;
    }


}
