import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.*;

public class Graph {
    public static final int INIFINITY = Integer.MAX_VALUE;

    // Idea for this was taken from "Map Interface Basic Operations" section of
    // https://docs.oracle.com/javase/tutorial/collections/interfaces/map.html
    protected Map<String, Node> nodes = new HashMap<String, Node>();
    //protected Vector<Edge> edges = new Vector<Edge>();
    private Queue<Node> q = new LinkedList<Node>();

    public int shortestPath(String startName){
        Node start = nodes.get(startName);

        // make sure we have something to work with
        if(start == null){
            throw new NoSuchElementException("Starting Node not found");
        }
        return 0;
    }


    /**
     * Get the nodes map of the graph.
     * @return nodes in graph.
     */
    public Map getNodes(){
        return nodes;
    }
    /**
     * A function to print the edges from all of the nodes.
     * Nice for debugging and making sure that the graph got read in properly.
     */
    public void printConnections(){
/*        for(Map.Entry<String, Node> entry: nodes.entrySet()){
            String name = entry.getKey();
            Node node = entry.getValue();

            System.out.println(name);
            for(Edge neighbor: node.neighbors){
                System.out.print(neighbor + "  ");
            }
            System.out.println();
        }*/
        for(Map.Entry<String, Node> entry: nodes.entrySet()){
            String name = entry.getKey();
            Node node = entry.getValue();

            System.out.println(name);
            for(Map.Entry<String, Edge> edgeEntry: node.neighbors.entrySet()){
                String destName = edgeEntry.getKey();
                Edge edge = edgeEntry.getValue();
                System.out.print(destName + " takes: " +edge.dist +"   ");
            }
            System.out.println();
        }
    }


    /**
     * Add a an edge to the graph. If nodes do not exist, add them
     * @param sourceName Name of source
     * @param destName Name of destination
     * @param dist Distance between the two
     */
    public void addEdge(String sourceName, String destName, int dist){
        // Make the nodes
        Node src = getNode(sourceName);
        Node dest = getNode(destName);
        //System.out.println(" using: "+src + " " + dest + " " + dist);

        // Link them together
        src.neighbors.put(destName, new Edge(src, dest, dist));
        //dest.neighbors.add(new Edge(dest, src, dist));
    }

    /**
     * Get the given node
     * @param name Name of the node
     * @return the node, either from the graph or newly made and
     * inserted into the graph
     */
    private Node getNode(String name){
        if (nodes.containsKey(name)) {
            //System.out.print("Found " + name +" ");
            //System.out.print(nodes.get(name).neighbors);
            return nodes.get(name);
        }else{
            Node n = new Node(name);
            nodes.put(name, n);
            //System.out.print("Did not find " + name+" ");
            return nodes.get(name);
        }
    }


    /**
     * Get the length of a specific route between cities
     * @param route String that contains the cities to visit i.e. ABC for A-B-C
     * @return Total length of route
     */
    public String specificRoute(String route){
        String[] stops = route.split("");
        Edge tempEdge;
        Node startNode;
        int total = 0;

        if (stops.length > 2) {
            try {
                startNode = nodes.get(stops[0]);
                Edge startEdge = startNode.neighbors.get(stops[1]);
                tempEdge = startEdge.dest.neighbors.get(stops[2]);

                for (int i = 2; i < stops.length; i++) {
                    //System.out.println("Stop: " + stops[i]);
                    if (total == 0) {
                        total = startEdge.dist + tempEdge.dist;
                        continue;
                    }
                    tempEdge = tempEdge.dest.neighbors.get(stops[i]);
                    //System.out.println("to " + stops[i] + " " + tempEdge);
                    total += tempEdge.dist;
                }

            } catch (NullPointerException e) {
                return "NO SUCH ROUTE";
            }
        } else if (stops.length == 2){
            try {
                startNode = nodes.get(stops[0]);
                tempEdge = startNode.neighbors.get(stops[1]);
            }catch (NullPointerException e) {
                return "NO SUCH ROUTE";
            }
            return Integer.toString(tempEdge.dist);
        } else{
            throw new ValueException("Need at least 2 stops.");
        }

        return Integer.toString(total);
    }
}
