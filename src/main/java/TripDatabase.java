import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class TripDatabase {

    public ArrayList<TripSection> database;

    //a constructor of RouteSection needed to initialise the object and ArrayList
    public TripDatabase()
    {
        database = new ArrayList<>();
    }

    static public class TripTime
    {
        public int hours;
        public int minutes;
        public int seconds;

        public TripTime(int hours, int minute, int seconds)
        {
            this.hours = hours;
            this.minutes = minute;
            this.seconds = seconds;
        }
    }

    //holds one line of the information from stop_times.txt
    //has 4 parameters tripID, stopID, arrivalTime, departureTime
    static public class TripSection
    {
        private int tripID;
        private int stopID;
        private String arrivalTime;
        private String departureTime;

        //a constructor of TripSection to write in the information
        public TripSection(int tripID, int stopID, String arrivalTime, String departureTime)
        {
            this.tripID = tripID;
            this.stopID = stopID;
            this.arrivalTime = arrivalTime;
            this.departureTime = departureTime;
        }
    }

    public void readTheStopTimeFile()
    {
        //reads int the file
        String nextLine;
        try {
            File file = new File("src/main/resources/stop_times.txt");
            Scanner scanner = new Scanner(file);

            //gets the next line to skip the first line with the names of inputs
            scanner.nextLine();
            //if there isn't a next line it stops
            while(scanner.hasNext())
            {
                int tripID;
                int stopID;
                String arrivalTime;
                String departureTime;
                nextLine = scanner.nextLine();
                //reads in the next line and splits it by a comma and a space to get the
                //trip ID and arrival time
                String[] inputs = nextLine.split(",");
                tripID = Integer.parseInt(inputs[0]);
                arrivalTime = inputs[1];
                stopID = Integer.parseInt(inputs[3]);
                departureTime = inputs[2];
                database.add(new TripSection(tripID,stopID,arrivalTime,departureTime));
            }

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public static void main(String[] args)
    {
        TripDatabase testObject = new TripDatabase();
        testObject.readTheStopTimeFile();
    }
}