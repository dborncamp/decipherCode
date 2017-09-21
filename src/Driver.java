import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;


public class Driver {
    private Graph graph;

    private Scanner inputScanner;
    private ArrayList<char[]> inputs;

    public Driver(){
         graph = new Graph();
         inputs = new ArrayList<char[]>();
    }

    /**
     * Build a graph from the given infile.
     *
     * @param infile file that contains the data
     */
    public void getInput(String infile){
        openInFile(infile);

        // loop over the file
        while (inputScanner.hasNext()){
            String temp = inputScanner.next();
            if (temp.regionMatches(true, 0,"Graph",0,"Graph".length())){
                //System.out.println("Found 'Graph'");
                continue;
            }
            StringTokenizer tempToken = new StringTokenizer(temp, ",");
            //System.out.print(temp + " " + temp.length() + "\n");

            String tok = tempToken.nextToken();

            //System.out.println(tok.toCharArray()[2]);
            //System.out.println(temp.toCharArray());

            inputs.add(tok.toCharArray());
        }
    }

    public void makeGraph(){
        for(char[] in: inputs){
            //System.out.println(String.valueOf(in[0]));
            graph.addEdge(String.valueOf(in[0]), String.valueOf(in[1]),
                          Character.getNumericValue(in[2]));
        }
        //System.out.println(graph.getNode("A"));
        //System.out.println(graph.nodeMap);
        //System.out.println(graph.getNodes());
        //System.out.println(graph.nodes.contains(new Node("A")));
        //System.out.println(graph.nodes.indexOf(new Node("z")));
        //graph.printConnections();
    }


    /**
     * Open the file to read in.
     * @param fileName Name of ascii file to try to read in.
     *
     * NOTE:
     * This must be a static method in this class as it is referenced from main
     * (a static method itself).
     */
    private void openInFile(String fileName){
        try{
            inputScanner = new Scanner(new File(fileName));
        }catch(SecurityException securityExemption){
            System.out.println("No permissions for you!");
            System.exit(1);
        }catch(FileNotFoundException fileNotFoundException){
            System.out.println("File not found. Try again");
            System.exit(1);
        }
    }

    public void routes(){
        System.out.println("Output #1: " + graph.specificRoute("ABC"));
        System.out.println("Output #2: " + graph.specificRoute("AD"));
        System.out.println("Output #2: " + graph.specificRoute("ADE"));
        System.out.println("Output #4: " + graph.specificRoute("AEBCD"));
        System.out.println("Output #4: " + graph.specificRoute("AED"));
    }

}
