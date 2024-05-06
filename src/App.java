
import datahandling.Datahandling;
import gui.*;

/**
 * Main Program Class.
 * @author WazeAzure
 */
public class App {

    /**
     * main method
     * 
     * @param args Get arguments from runtime
     */
    public static void main(String[] args) {
        Datahandling f = new Datahandling();
        // f.createDir("test/dictionary/");
        f.main("test/dictionary.txt");
        
        
        Gui mainGui = new Gui(f);
        mainGui.main();
    }
}
