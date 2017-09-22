import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Node {
    protected String name;
    protected Map<String, Edge> neighbors;
    public Node last;
    public int temp;
    public boolean visited;

    public Node(String nodeName){
        name = nodeName;
        neighbors = new HashMap<String, Edge>();
    }

    public String getName(){
        return name;
    }

    public String getLabel(){
        return name;
    }

    public void reset(){
        last = null;
        temp = 0;
    }

    public boolean isVisited() {
        return visited;
    }

    public void visit(){
        visited = true;
    }

    public void unvisit(){
        visited = false;
    }

    public int compareTo(Node comp){
        String tempA = this.toString();

        return tempA.compareTo(comp.toString());
    }

    @Override
    public String toString() {
        return name;
    }

}
