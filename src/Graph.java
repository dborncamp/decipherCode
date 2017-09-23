import java.util.*;

public class Graph {
    public static final int INIFINITY = Integer.MAX_VALUE;

    // Idea for this was taken from "Map Interface Basic Operations" section of
    // https://docs.oracle.com/javase/tutorial/collections/interfaces/map.html
    protected Map<String, Node> nodes = new HashMap<String, Node>();
    //protected Vector<Edge> edges = new Vector<Edge>();
    private LinkedList<Node> nList = new LinkedList<Node>();

    public int shortestPath(String startName){
        Node start = nodes.get(startName);

        // make sure we have something to work with
        if(start == null){
            throw new NoSuchElementException("Starting Node not found");
        }
        return 0;
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
     * @param sourceName Name of map
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
    public String specificRoute(String route) throws Exception {
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
            throw new Exception("Need at least 2 stops.");
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
    private int numTrips;
    private int visitLimit;

    /**
     * Recursively try to work 6 and 7. but did not work :(
     * @param startNode
     * @param target
     */
    private void visitQueue(Node startNode, Node target){
        if (startNode.isVisited()){
            Node removeNode = nList.removeLast();
            System.out.println("Back From " + removeNode);
            return;
        }
        startNode.visit();
        tripHelperCounter ++;
        for(Map.Entry<String, Edge> edgeEntry: startNode.neighbors.entrySet()){
            Edge edge = edgeEntry.getValue();

            nList.add(edge.dest);

            System.out.println(edge +" "+ edge.dest + " " + tripHelperCounter + " Q: " + nList);

            if(edge.dest == target){
                System.out.println("Found: " + target + " in " + tripHelperCounter + " "+nList);
                if(tripHelperCounter == visitLimit) {
                    System.out.println("Adding " + nList +"\n");
                    numTrips++;
                }
            }
            visitQueue(edge.dest, target);

        }
        Node removeNode = nList.removeLast();
        System.out.println("Back From " + removeNode);
        removeNode.unvisit();
        tripHelperCounter --;
        System.out.println("Removing: " + removeNode + "   " + nList);
    }

    public void visitCities(String start, String target, int visLimit){
        Node startNode = nodes.get(start);
        Node targNode = nodes.get(target);
        visitLimit = visLimit;
        numTrips = 0;
        tripHelperCounter = 0;
        nList.add(startNode);
        visitQueue(startNode, targNode);

        System.out.println(numTrips);
        System.out.println(nList);

    }


    /**
     * VERY Wrong way to do the problem I don't understand it.
     *
     * @param startNode
     * @param target
     * @param type
     * @return
     */
    public ArrayList<Trip> getPaths(Node startNode, Node target, int type){
        Stack<Node> nextNodes = new Stack<Node>();
        ArrayList<Trip> trips = new ArrayList<Trip>();
        Stack<Node> visited = new Stack<Node>();

        int count = 0;

        // first push the start node to the next stack.
        nextNodes.push(startNode);
        //System.out.println(startNode.neighbors);
        Trip trip = new Trip(startNode.neighbors, target, startNode, new Stack<Node>());
        //System.out.println(trip);

        // 1 enter a loop until the next stack is empty.
        while(!nextNodes.isEmpty()){
            // 2 pop the next stack (giving me my start node).
            //System.out.println("Next Nodes: "+nextNodes);
            Node current = nextNodes.pop();
            //System.out.println("Current: "+ current);

            // 3 push it to the visited stack.
            trip.visited.push(current);
            //System.out.println("Visited Nodes: "+trip.visited);
            // 4 update my current trip.
            trip.currentCity = current;
            //trip.updateCity(current);
            //System.out.println(" Trip: "+trip);

            // 5 check if the popped value is my goal
            if ((trip.currentCity == trip.dest) & (trip.visited.size() > 1) & (trip.visited.size()==4)){
                //5a - if it meets my goal, I update the trip collection with the current trip
                trips.add(trip);
                //System.out.println(" ** Found: "+trip);
                //5b - create a new trip matching the existing.
                trip = new Trip(trip);

            } else {
                //System.out.println(" --- not goal");
                //6 - If it does not meet the goal, then get the destinations I can reach from the node
                // (I defined a routemap object to give me this).
                trip.map = current.neighbors;
                //System.out.println(trip.map);
                // 7- I loop over all the destinations.
                for (Map.Entry<String, Edge> edgeEntry : trip.map.entrySet()) {
                    Edge edge = edgeEntry.getValue();

                    //7a - I check my delimiter condition, if true proceed
                    if (trip.visited.size() != 4) {
                        //System.out.println("Pushing: " + edge.dest);
                        nextNodes.push(edge.dest);
                        //8- if the delimiter condition is false this means the traversal cannot continue.
                    } else {
                        //8a - if the next stack is not empty, I need to get the current destination
                        if (!nextNodes.isEmpty()) {
                            Node dest = trip.dest;
                            //8b- I make a new trip with the current destination
                            // set as "currentInStack" as I described in my Trip.
                            trip = new Trip(trip.map, trip.dest, dest, trip.visited);
                        }
                    }

                }

            }


            // stop possible infinite loop
            count ++;
            if (count > 100) {
                //System.out.println("Limit Reached");
                break;
            }
        }
        return trips;
    }

    public static boolean delim6(Trip trip){
        System.out.println("Hi!");
        if(trip.visited.size() < 4) {
            return true;
        }
        return false;
    }

    class Trip{
        Map<String,Edge> map;
        Node dest;
        Node currentCity;
        Stack<Node> visited;

        public Trip(Map<String,Edge> routes, Node dst, Node current, Stack<Node> alreadyVisited){
            map = routes;
            dest = dst;
            currentCity = current;
            visited = alreadyVisited;
        }

        public Trip(Trip t){
            map = t.map;
            dest = t.dest;
            currentCity = t.currentCity;
            visited = t.visited;
        }

        public void updateCity(Node city){
            currentCity = city;
            visited.push(currentCity);
        }

        @Override
        public String toString() {
            String str = "";
            for(Node city: visited) str+= city+" ";
            return str;
        }
    }


    public int visitPaths(String start, String target, int visLimit){
        // try using lambda functions but no time...
        //func.run();
        // Make sure the input is good
        start.toUpperCase();
        target.toUpperCase();
        Node startNode = nodes.get(start);
        Node targNode = nodes.get(target);
        ArrayList<Trip>trips = getPaths(startNode, targNode, visLimit);
        //System.out.println(trips.size());
        return trips.size();
    }

    private void resetDist(){
        for(Map.Entry<String, Node> entry: nodes.entrySet()){
            Node node = entry.getValue();
            node.temp = Integer.MAX_VALUE;
            node.last = null;
            node.unvisit();
        }

    }



    /**
     * Get the nodes map of the graph.
     * @return nodes in graph.
     */
    public ArrayList<Node> getNodes(){
        ArrayList<Node> localNodes = new ArrayList<Node>();
        for(Map.Entry<String, Node> entry: nodes.entrySet()) {
            Node node = entry.getValue();
            localNodes.add(node);
        }
        return localNodes;
    }

    /**
     * Get the Edges map of the graph.
     * @return edges between all of the nodes in the graph.
     */
    public ArrayList getEdges(){
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for(Map.Entry<String, Node> entry: nodes.entrySet()) {
            Node node = entry.getValue();
            for (Map.Entry<String, Edge> edgeEntry : node.neighbors.entrySet()) {
                Edge edge = edgeEntry.getValue();
                edges.add(edge);
            }
        }
        return edges;
    }

}

/**
 * Heavily taken from: http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
 * Creative commons license...
 */
class DijkstraAlgorithm {
    private final List<Node> nodes;
    private final List<Edge> edges;
    private Set<Node> visitedNodes;
    private Set<Node> toVisitNodes;
    private Map<Node, Node> predecessors;
    Map<Node, Integer> distance;

    public DijkstraAlgorithm(Graph graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<Node>(graph.getNodes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
    }

    public void execute(Node source) {
        visitedNodes = new HashSet<Node>();
        toVisitNodes = new HashSet<Node>();
        distance = new HashMap<Node, Integer>();
        predecessors = new HashMap<Node, Node>();
        distance.put(source, 0);
        toVisitNodes.add(source);
        int counter = 0;

        while (toVisitNodes.size() > 0) {
            //System.out.println(toVisitNodes);
            Node node = getMinimum(toVisitNodes);
            //System.out.println(node);
            visitedNodes.add(node);
            toVisitNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Node node) {
        List<Node> adjacentNodes = getNeighbors(node);
        //System.out.println(adjacentNodes);
        for (Node target : adjacentNodes) {
            /*System.out.println("Target: " + node + " " + getShortestDistance(target));
            System.out.println(node + " " + getShortestDistance(node));
            System.out.println(node + " " + getDistance(node, target));
            */
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {

                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                toVisitNodes.add(target);
            }
        }

    }

    private int getDistance(Node node, Node target) {
        for (Edge edge : edges) {
            if (edge.source.equals(node)
                    && edge.dest.equals(target)) {
                return edge.dist;
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : edges) {
            if (edge.source.equals(node) && !isSettled(edge.dest)) {
                neighbors.add(edge.dest);
            }
        }
        return neighbors;
    }

    private Node getMinimum(Set<Node> Nodes) {
        Node minimum = null;
        for (Node Node : Nodes) {
            if (minimum == null) {
                minimum = Node;
            } else {
                if (getShortestDistance(Node) < getShortestDistance(minimum)) {
                    minimum = Node;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Node Node) {
        return visitedNodes.contains(Node);
    }

    public int getShortestDistance(Node destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Node> getPath(Node target) {
        LinkedList<Node> path = new LinkedList<Node>();
        Node step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}

/**
 * Breadth first search
 *
 * Taken from https://www.careercup.com/question?id=20786668
 *
 */
class BFSTest {
    public void findShortestPath(Node start, Node stop) {
        //g.getEdges(start);
        Queue <Node> toVisit = new LinkedList<>();
        Set <Node> alreadyVisited = new HashSet<>();

        Map <Node, Node> parent = new HashMap<>();
        alreadyVisited.add(start);
        toVisit.add(start);

        parent.put(start, null);

        while(!toVisit.isEmpty()) {
            Node cur = toVisit.poll();
            //We win
            if(cur == stop) {
                Node at = cur;
                while(at != null){
                    //System.out.print(at + ", ");
                    at = parent.get(at);
                }
                return;
            }
            for (Map.Entry<String, Edge> edgeEntry : cur.neighbors.entrySet()) {
                Edge e = edgeEntry.getValue();

                if(!alreadyVisited.contains(e.dest)) {
                    parent.put(e.dest, cur);
                    alreadyVisited.add(e.dest);
                    toVisit.offer(e.dest);
                }
            }
        }
        //We lose
        System.out.println("Couldn't find");
    }
}