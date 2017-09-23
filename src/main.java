import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Driver driver = new Driver();
        //Node test = new Node("A");
        //System.out.println(test);
        String infile = askForFile();
        driver.getInput(infile);
        //driver.getInput("src/input.txt");
        driver.makeGraph();

        // 1 through 5
        driver.OneToFive();
        //driver.visitCities();
        // 6, 7 and 10
        driver.SixSeven();
        // 8 and 9
        driver.testshort();

        // 10
        driver.ten();
    }

    private static String askForFile(){
        String fileName = "";
        do {
            System.out.println("Please enter a to create a graph with.");
            System.out.println("For example: src/input.txt");
            System.out.println("The file must be in the format of NND, NND, ...");
            System.out.println("Where N is a node and D is the distance between the 2");
            Scanner sc = new Scanner(System.in);
            fileName = sc.nextLine();
        } while (fileName.length() < 1);
        return fileName;
    }
}
