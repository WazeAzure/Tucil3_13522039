package datastructure;

import java.util.*;
import java.io.Serializable;

public class Node implements Serializable {
    public String info;
    public Node parent;
    public int depth;

    public Node(String info, Node parentNode){
        this.info = info;
        this.parent = parentNode;
    }

    public ArrayList<String> getPath(Node end){
        ArrayList<String> path = new ArrayList<String>();

        Node current = this;

        while (!current.info.equals(end.info)) {
            path.add(current.info);
            current = current.parent;
        }
        // current == end
        if (current.info.equals(end.info)){
            path.add(end.info);
        }
        
        return path;
    }

    public int getHeuristic(String target){
        int count = 0;
        for(int i=0; i<this.info.length(); i++){
            if(target.charAt(i) != this.info.charAt(i)){
                count++;
            }
        }

        return count;
    }
}
