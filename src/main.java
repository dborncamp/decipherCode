public class main {
    public static void main(String[] args) {
        Driver driver = new Driver();
        //Node test = new Node("A");
        //System.out.println(test);
        driver.getInput("src/input.txt");
        driver.makeGraph();
        //driver.routes();
        //driver.routeToC();
        //driver.printAdjMatrix();
        //driver.tripStop();
        driver.visitCities();
    }
}
