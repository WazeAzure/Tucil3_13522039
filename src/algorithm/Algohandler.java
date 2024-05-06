package algorithm;

import java.util.*;

import datahandling.Datahandling;
import datastructure.*;

public class Algohandler {
    /** time execution in miliseconds */
    public long timeExecuted;
    public int nodeVisited;
    public long memoryUsed;

    public ArrayList<String> solve(String start, String end, String algo, Datahandling dh){
        ArrayList<String> pathAns = new ArrayList<String>();

        Node startNode = dh.getNodeFromString(start);
        Node endNode = dh.getNodeFromString(end);

        long startTime = System.currentTimeMillis();
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long endMemory = 0;
        if(algo.equals("1")){
            // panggil UCS
            UCS ucs = new UCS(dh);

            nodeVisited = ucs.solve(startNode, endNode);
            pathAns = ucs.path;

            endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        }
        
        else if(algo.equals("2")){
            // panggil GBFS
            GBFS gbfs = new GBFS(dh);
            
            nodeVisited = gbfs.solve(startNode, endNode);
            pathAns = gbfs.path;

            endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        }
        
        else if(algo.equals("3")){
            // panggil A*
            
            Astar astar = new Astar(dh);
            
            nodeVisited = astar.solve(startNode, endNode);
            pathAns = astar.path;

            endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        }

        long endTime = System.currentTimeMillis();
        this.timeExecuted = endTime - startTime;
        this.memoryUsed = endMemory - startMemory;
        
        System.out.println("Time execution: " + timeExecuted + "ms");
        System.out.println("Memory used: " + memoryUsed + " bytes");
        return pathAns;
    }
}