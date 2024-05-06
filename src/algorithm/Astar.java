package algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import datahandling.Datahandling;
import datastructure.Node;

public class Astar extends Algorithm {
    public ArrayList<String> path;
    private String endString;

    public Astar(Datahandling dh){
        super();
        this.datahandling = dh;
        this.path = new ArrayList<String>();
    }

    class PQComparator implements Comparator<Node>{
        @Override
        public int compare(Node o1, Node o2) {
            // System.out.println("cOMPARATOR CALLED");
            int h1 = o1.getHeuristic(endString) + o1.depth;
            // System.out.println(o1.info + " | " + h1);
            int h2 = o2.getHeuristic(endString) + o2.depth;
            // System.out.println(o2.info + " | " + h2);
            if (h1 > h2){
                return 1;
            } else if(h1 < h2){
                return -1;
            }
            return 0;
        }
    }

    public int solve(Node start, Node end){
        this.endString = end.info;

        if(start.info.equals(end.info)){
            return 0;
        }

        int nodeVisited = 0;

        PriorityQueue<Node> queue = new PriorityQueue<Node>(new PQComparator());
        start.depth = 0;
        queue.offer(start);

        while(!queue.isEmpty()){
            Node n = queue.poll();
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

                        if(x != null){
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
