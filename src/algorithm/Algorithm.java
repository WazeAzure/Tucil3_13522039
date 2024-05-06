package algorithm;

import java.util.*;

import datahandling.Datahandling;
import datastructure.*;

public class Algorithm {
    protected HashSet<String> visitedNode;
    protected Datahandling datahandling;

    public Algorithm(){
        this.visitedNode = new HashSet<String>();
    }

    public boolean checkisVisited(Node s){
        if(visitedNode.contains(s.info)){
            return true;
        }
        return false;
    }

    public void setVisitedNode(Node s){
        visitedNode.add(s.info);
    }
}
