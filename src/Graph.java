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

    private void unvisitAll(){
        for(Map.Entry<String, Node> entry: nodes.entrySet()){
            Node node = entry.getValue();
            node.unvisit();
        }
    }

    public int roundTrip(String city, int maxStops){
        Node startNode = nodes.get(city);
        int total = 0;

        visitNode(startNode);

        unvisitAll();
        return total;
    }

    /**
     * Recursively visit each node
     * @param node node to visit neighbors of
     */
    private void visitNode(Node node){
        if (node.isVisited()){
            return;
        }
        node.visit();
        System.out.println("Visiting: " + node.getName());
        for(Map.Entry<String, Edge> edgeEntry: node.neighbors.entrySet()){
            Edge edge = edgeEntry.getValue();
            System.out.println(edge);
            visitNode(edge.dest);
        }
    }

    public int[][] getAdjacencyMatrix() {
        int[][] adjMatrix = new int[nodes.size()][nodes.size()];

        int i = 0;
        int j = 0;

        for(Map.Entry<String, Node> entry: nodes.entrySet()) {
            String name = entry.getKey();
            Node node = entry.getValue();
            for(Map.Entry<String, Edge> edgeEntry: node.neighbors.entrySet()) {
                Edge edge = edgeEntry.getValue();

                if(edge.source.equals(node.getName())){
                    adjMatrix[i][j] = edge.dist;
                }
            }
        }
            return adjMatrix;
    }

    public String tripsBetweenNodes(Node start, Node end, int limit){
        int numTrips = 0;
        Node startNode = nodes.get(start);

        // look at every edge from starting node
        for(Map.Entry<String, Edge> edgeEntry: startNode.neighbors.entrySet()) {
            int counter = 0;
            while(true) {
                Edge edge = edgeEntry.getValue();

                if (counter > limit){
                    break;
                }
                if (edge.dest == end){
                    numTrips ++;
                }
            }
        }
        return Integer.toString(numTrips);
    }

    private int tripHelperCounter;
    private int limit;
    private boolean equal;
    int numTrips;
    public int triprecurse(String start, String target, int tripLimit, boolean tripEqual){
        numTrips = 0;
        limit = tripLimit;
        equal = tripEqual;
        Node startNode = nodes.get(start);
        Node targetNode = nodes.get(target);

        for(Map.Entry<String, Edge> edgeEntry: startNode.neighbors.entrySet()) {
            tripHelperCounter = 1;
            Edge edge = edgeEntry.getValue();
            System.out.println("Starting at: " + startNode + " looking for: " + targetNode);
            triphelper(edge.dest, targetNode);
            //System.out.println("Found: " + tripHelperCounter);

        }
        return numTrips;
    }

    public void triphelper(Node start, Node target){
        if (start == target){
            System.out.println("FOUND TARGET!!! " + tripHelperCounter);
            if (equal){
                if (tripHelperCounter == limit - 1) {
                    System.out.println("equal: " + tripHelperCounter);
                    numTrips++;
                }
            } else {
                if (tripHelperCounter <= limit) {
                    System.out.println("maximum: " + tripHelperCounter);
                    numTrips++;
                }
            }
            return;
        }

        for(Map.Entry<String, Edge> edgeEntry: start.neighbors.entrySet()) {
            Edge edge = edgeEntry.getValue();
            System.out.println("looking at: " + edge);
            tripHelperCounter ++;
            triphelper(edge.dest, target);
            tripHelperCounter --;
        }
        //tripHelperCounter --;
    }

    private int visitLimit;
    private void popQueue(Node startNode, Node target){
        if (startNode.isVisited()){
            return;
        }
        startNode.visit();
        tripHelperCounter ++;
        for(Map.Entry<String, Edge> edgeEntry: startNode.neighbors.entrySet()){
            Edge edge = edgeEntry.getValue();

            q.add(edge.dest);
            System.out.println(edge +" "+ edge.dest + " " + tripHelperCounter);
            popQueue(edge.dest, target);
            if(edge.dest == target){
                System.out.println("Found: " + target + " in " + tripHelperCounter);
                if(tripHelperCounter == visitLimit) {
                    numTrips++;
                }
            }

        }
        System.out.println("Back From " + startNode);
        startNode.unvisit();
        tripHelperCounter --;
    }

    public void visitCities(String start, String target, int visLimit){
        Node startNode = nodes.get(start);
        Node targNode = nodes.get(target);
        visitLimit = visLimit;
        numTrips = 0;
        tripHelperCounter = 0;
        popQueue(startNode, targNode);



        System.out.println(numTrips);

    }
}
