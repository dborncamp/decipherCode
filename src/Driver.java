import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


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

            inputs.add(tok.toCharArray());
        }
    }

    public void makeGraph(){
        for(char[] in: inputs){
            graph.addEdge(String.valueOf(in[0]), String.valueOf(in[1]),
                    Character.getNumericValue(in[2]));
        }
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

    /**
     * Print the OneToFive for outputs 1 to 5
     */
    public void OneToFive(){
        try {
            System.out.println("Output #1: " + graph.specificRoute("ABC"));
            System.out.println("Output #2: " + graph.specificRoute("AD"));
            System.out.println("Output #3: " + graph.specificRoute("ADC"));
            System.out.println("Output #4: " + graph.specificRoute("AEBCD"));
            System.out.println("Output #5: " + graph.specificRoute("AED"));
        } catch(Exception e) {
            System.out.println("Nodes are wrong. Something is weird... Stopping these.");
        }
    }

    public void visitCities(){
        graph.visitCities("A", "C", 4);
    }

    public void routeToC(){
        graph.roundTrip("C", 3);
    }

    public void SixSeven(){

        int six = graph.visitPaths("C","C", 4);
        int seven = graph.visitPaths("A","C", 7);
        System.out.println("Output #6: "+six);
        System.out.println("Output #7: "+seven);

    }

    public void testshort(){
        DijkstraAlgorithm dij = new DijkstraAlgorithm(graph);
        Node searchNode;
        searchNode = graph.nodes.get("C");
        dij.execute(searchNode);
        int distCC = dij.getShortestDistance(graph.nodes.get("C"));
        System.out.println("Output #8: " + distCC + "  -- Wrong :(");

        searchNode = graph.nodes.get("A");
        dij.execute(searchNode);
        int distAC = dij.getShortestDistance(graph.nodes.get("C"));
        System.out.println("Output #9: " + distAC);

    }

    public void ten(){
        BFSTest t = new BFSTest();
        t.findShortestPath(graph.nodes.get("C"),graph.nodes.get("C"));
        System.out.println("Output #10: A mix of 6,7 and 9. Which I didn't figure out :(");
    }
}
