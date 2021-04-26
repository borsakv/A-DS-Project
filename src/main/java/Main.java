import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        //Vitali is not the smartest guy is he?
        System.out.println("This is a test!");
        BusStop stop = new BusStop(1, 1, "", "");
        try {
            File myObj = new File("src/main/resources/stops.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
