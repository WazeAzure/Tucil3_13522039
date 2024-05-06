package datahandling;

import java.io.*;
import java.util.*;
import datastructure.*;

public class Datahandling {
    public int max_len;
    public int count;
    private HashSet<String> keyword_table;

    private Map<String, ArrayList<Node>> graphMap;

    private Map<String, Node> graphStringNode;

    public Datahandling(){
        this.max_len = 0;
        this.count = 0;
        keyword_table = new HashSet<>();

        graphMap = new HashMap<String, ArrayList<Node>>();
        graphStringNode = new HashMap<String, Node>();
    }

    public void createDir(String dirname){
        boolean status = new File(dirname).mkdirs();
        if(!status){
            System.out.println("ERROR CREATING DIR");
        }
    }

    public void main(String filename){
        try {
            File file = new File("test/graphMap");
            if (file.exists()) {
                deserializeFileToMap();

                File myObj = new File(filename);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    // System.out.println(data);
                    if(data.length() > this.max_len){
                        max_len = data.length();
                    }
                    this.count++;
                    addWordList(data);

                    Node n2 = new Node(data, null);
                    graphStringNode.put(data, n2);
                }
                myReader.close();
            } else {
                File myObj = new File(filename);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    // System.out.println(data);
                    if(data.length() > this.max_len){
                        max_len = data.length();
                    }
                    this.count++;
                    addWordList(data);
                    addNode(data);

                    Node n2 = new Node(data, null);
                    graphStringNode.put(data, n2);
                }
                myReader.close();
    
                System.out.println(this.max_len);
                System.out.println(this.count);
    
                // save graph to file
                serializeMapToFile("test/graphMap");
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void serializeMapToFile(String filename){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this.graphMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializeFileToMap(){
        Map<String, ArrayList<Node>> map = new HashMap<String, ArrayList<Node>>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("test/graphMap"))) {
            map = (Map<String, ArrayList<Node>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.graphMap = map;
    }

    public void addWordList(String word){
        if(!checkWord(word)){
            keyword_table.add(word);
        }
    }

    public boolean checkWord(String word){
        if(this.keyword_table.contains(word)){
            return true;
        }
        return false;
    }

    public void addNode(String data){
        ArrayList<Node> tempArrayList = new ArrayList<Node>();
        graphMap.put(data, tempArrayList);

        for(String key: graphMap.keySet()){
            if(key.length() == data.length()){
                if(nodeIsParent(key, data)){
                    Node keyNode = getNodeFromString(key);
                    Node n = new Node(data, keyNode);
                    graphMap.get(key).add(n);
                    graphMap.get(data).add(keyNode);

                    Node n2 = new Node(data, null);
                    graphStringNode.put(data, n2);
                }
            }
        }
    }

    public boolean nodeIsParent(String s1, String s2){
        int len = s1.length();
        int difference = 0;

        for(int i=0; i<len; i++){
            if(s1.charAt(i) != s2.charAt(i)){
                difference++;
                if(difference == 2){
                    return false;
                }
            }
        }

        return true;
    }

    public Node getNodeFromString(String s){
        return this.graphStringNode.get(s);
    }

    public ArrayList<Node> getNodeList(String n){
        return this.graphMap.get(n);
    }
}
