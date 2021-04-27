import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class RouteSection {

    List<TripSection> info = new ArrayList<TripSection>();

    //a constructor of RouteSection needed to initialise the object and ArrayList
    public RouteSection() {}

    //holds one line of the information from stop_times.txt
    //has 4 parameters tripID, stopID, arrivalTime, departureTime
    private class TripSection
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

    public void addRouteSection(int tripID, int stopID, String arrivalTime, String departureTime)
    {
        TripSection tripSectionNode = new TripSection(tripID,stopID,arrivalTime,departureTime);
        info.add(tripSectionNode);
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
                addRouteSection(tripID,stopID,arrivalTime,departureTime);
            }

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    //get a tripID of a specific line given it's number in array list
    public int getTripID(int numberInList)
    {
        return info.get(numberInList).tripID;
    }

    //get a stopID of a specific line given it's number in array list
    public int getStopID(int numberInList)
    {
        return info.get(numberInList).stopID;
    }

    //get a arrivalTime of a specific line given it's number in array list
    public String getArrivalTime(int numberInList)
    {
        return info.get(numberInList).arrivalTime;
    }

    //get a departureTime of a specific line given it's number in array list
    public String getDepartureTime(int numberInList)
    {
        return info.get(numberInList).departureTime;
    }

    //get a full set of information(Inside of an Object) given it's number in array list
    public TripSection getInfo(int numberInList)
    {
        return info.get(numberInList);
    }

    public static void main(String[] args)
    {
        RouteSection testObject = new RouteSection();
        testObject.readTheStopTimeFile();
    }
}
