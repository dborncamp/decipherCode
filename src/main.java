public class main {
    public static void main(String[] args) {
        Driver driver = new Driver();
        //Node test = new Node("A");
        //System.out.println(test);
        driver.getInput("C:\\Users\\Dave\\IdeaProjects\\decipher1\\src\\input.txt");
        driver.makeGraph();
        driver.routes();

    }
}
