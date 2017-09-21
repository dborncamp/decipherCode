public class Edge {
    public Node source; // from
    public Node dest;   // to
    public int dist;    // distance

    /**
     * Node constructor that has destination and distance
     * @param b destination
     * @param distance distance to destination
     */
    public Edge(Node b, int distance){
        dest = b;
        dist = distance;
    }


    /**
     * Node constructor that has destination and distance
     * @param a Source
     * @param b destination
     * @param distance distance between the two
     */
    public Edge(Node a, Node b, int distance){
        source = a;
        dest = b;
        dist = distance;
    }

    public String toString(){
        return source + " ==> " + dest + " takes: " + dist;
    }
}
