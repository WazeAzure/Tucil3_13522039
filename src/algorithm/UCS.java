package algorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import datahandling.Datahandling;
import datastructure.Node;

/** 
 * UCS algorithm
 * 
 * @param awikwok A class to represent a point on the x-y coordinate plane.
 */ 
public class UCS extends Algorithm {

    public ArrayList<String> path;

    public UCS(Datahandling dh){
        super();
        this.datahandling = dh;
        this.path = new ArrayList<String>();
    }

    public int solve(Node start, Node end){
        if(start.info.equals(end.info)){
            return 0;
        }

        int nodeVisited = 0;

        ArrayDeque<Node> queue = new ArrayDeque<>();
        start.depth = 0;
        queue.add(start);

        while(!queue.isEmpty()){
            Node n = queue.getFirst();
            queue.removeFirst();
            nodeVisited++;

            if(!checkisVisited(n)){
                setVisitedNode(n);
                if(n.info.equals(end.info)){
                    this.path = n.getPath(start);
                    return nodeVisited;
                }

                ArrayList<Node> temp = datahandling.getNodeList(n.info);
                for(int i=0; i<temp.size(); i++){
                    if(temp.get(i) != null){
                        Node x = new Node(temp.get(i).info, n);
    
                        if(x != null && !checkisVisited(x)){
                            x.parent = n;
                            x.depth = x.parent.depth + 1;
                            queue.add(x);
                        }
                    }
                }
            }
        }

        return nodeVisited;
    }
}
