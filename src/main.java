public class main {
    public static void main(String[] args) {
        Driver driver = new Driver();
        //Node test = new Node("A");
        //System.out.println(test);
        driver.getInput("src/input.txt");
        driver.makeGraph();

        // 1 through 5
        driver.routes();
        //driver.visitCities();
        // 6, 7 and 10
        //driver.visits();
        // 8 and 9
        driver.testshort();

        // 10
        driver.roundRound();
    }
}
