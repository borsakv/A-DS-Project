import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class TripDatabase {

    public ArrayList<Trip> database;

    //a constructor of RouteSection needed to initialise the object and ArrayList
    public TripDatabase(String tripFilePath) {
        database = new ArrayList<>();
        readTheStopTimeFile(tripFilePath);
        sortTrips(0, database.size() - 1);
    }

    // A wrapper for a given trip
    static public class Trip
    {
        public int tripID;
        public ArrayList<TripSection> trip;

        public Trip()
        {
            trip = new ArrayList<>();
        }
    }

    //holds one line of the information from stop_times.txt
    //has 4 parameters tripID, stopID, arrivalTime, departureTime
    static public class TripSection
    {
        public int tripID;
        public int stopID;
        public TripTime arrivalTime;
        public TripTime departureTime;
        public int stopSequence;
        public int stopHeadsign;
        public int pickupType;
        public int dropOffType;
        public float distTraveled;

        //a constructor of TripSection to write in the information
        public TripSection(int tripID, int stopID, TripTime arrivalTime, TripTime departureTime, int stopSequence, int stopHeadsign, int pickupType, int dropOffType, float distTraveled)
        {
            this.tripID = tripID;
            this.stopID = stopID;
            this.arrivalTime = arrivalTime;
            this.departureTime = departureTime;
            this.stopSequence = stopSequence;
            this.stopHeadsign = stopHeadsign;
            this.pickupType = pickupType;
            this.dropOffType = dropOffType;
            this.distTraveled = distTraveled;
        }
    }

    public void readTheStopTimeFile(String tripFilePath)
    {
        //reads int the file
        String nextLine;
        Scanner scanner = new Scanner(TripDatabase.class.getResourceAsStream(tripFilePath));

        //gets the next line to skip the first line with the names of inputs
        scanner.nextLine();

        Trip newTrip = new Trip();
        newTrip.tripID = 9017927;

        //if there isn't a next line it stops
        while(scanner.hasNext())
        {
            int tripID = -1;
            int stopID = -1;
            String arrivalTimeString;
            TripTime arrivalTime;
            String departureTimeString;
            TripTime departureTime;
            int stopSequence = -1;
            int stopHeadsign = -1;
            int pickupType = -1;
            int dropOffType = -1;
            float distTraveled = -1;
            nextLine = scanner.nextLine();
            //reads in the next line and splits it by a comma and a space to get all of the variables
            String[] inputs = nextLine.split(",");
            if(!inputs[0].equals(""))
                tripID = Integer.parseInt(inputs[0]);
            arrivalTimeString = inputs[1];
            arrivalTime = new TripTime(arrivalTimeString);
            departureTimeString = inputs[2];
            departureTime= new TripTime(departureTimeString);
            if(!inputs[3].equals(""))
                stopID = Integer.parseInt(inputs[3]);
            if(!inputs[4].equals(""))
                stopSequence= Integer.parseInt(inputs[4]);
            if(!inputs[5].equals(""))
                stopHeadsign= Integer.parseInt(inputs[5]);
            if(!inputs[6].equals(""))
                pickupType= Integer.parseInt(inputs[6]);
            if(!inputs[7].equals(""))
                dropOffType= Integer.parseInt(inputs[7]);
            if(inputs.length == 9 && !inputs[8].equals(""))
                distTraveled= Float.parseFloat(inputs[8]);

            if(arrivalTime.validate() && departureTime.validate())
            {
                // If we are onto a new trip we need to add the previous one to the database and start a new one
                if(tripID != newTrip.tripID)
                {
                    database.add(newTrip);
                    newTrip = new Trip();
                    newTrip.tripID = tripID;
                }
                newTrip.trip.add(new TripSection(tripID, stopID, arrivalTime, departureTime, stopSequence, stopHeadsign, pickupType, dropOffType, distTraveled));
            }
        }
        scanner.close();

    }

    // Implements quick sort to sort the trips by tripID as we have such a large number of elements
    private void sortTrips(int start, int end)
    {
        int partition = partition(start, end);

        if((partition - 1) > start) {
            sortTrips(start, partition - 1);
        }
        if((partition + 1) < end) {
            sortTrips(partition + 1, end);
        }
    }

    private int partition(int start, int end)
    {
        Trip pivot = database.get(end);

        for(int i = start; i < end; i++)
        {
            if(database.get(i).tripID < pivot.tripID)
            {
                Collections.swap(database, start, i);
                start++;
            }
        }

        Collections.swap(database, start, end);

        return start;
    }

    // We can use the fact that the trip times are all already in order to use binarySearch
    public ArrayList<Trip> searchForArrivalTime(String arrivalTime)
    {
        TripTime time = new TripTime(arrivalTime);
        ArrayList<Trip> result = new ArrayList<>();
        for(Trip t : database)
        {
            if(binarySearch(t, 0, t.trip.size() - 1, time) != - 1)
            {
                result.add(t);
            }
        }
        return result;
    }

    public ArrayList<Trip> searchForArrivalTime(int hours, int minutes, int seconds)
    {
        TripTime time = new TripTime(hours, minutes, seconds);
        ArrayList<Trip> result = new ArrayList<>();
        for(Trip t : database)
        {
            if(binarySearch(t, 0, t.trip.size() - 1, time) != - 1)
            {
                result.add(t);
            }
        }
        return result;
    }

    private int binarySearch(Trip t, int l, int r, TripTime x)
    {
        if (r >= l)
        {
            int mid = l + (r - l) / 2;

            // If the element is present at the middle
            // itself
            if (t.trip.get(mid).arrivalTime.compare(x) == 0)
                return mid;

            // If element is smaller than mid, then
            // it can only be present in left subarray
            if (t.trip.get(mid).arrivalTime.compare(x) == 1)
                return binarySearch(t, l, mid - 1, x);

            // Else the element can only be present
            // in right subarray
            return binarySearch(t, mid + 1, r, x);
        }

        // We reach here when element is not
        // present in array
        return -1;
    }

    // Class to store and parse time from the format (HH:MM:SS) and also to make sure that the time is valid
    static public class TripTime{
        public int hours;
        public int minutes;
        public int seconds;

        public TripTime(String time){ updateTime(time); }

        public TripTime(int hours, int minutes, int seconds)
        {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }

        public void updateTime(String time){
            String[] times = time.split(":");
            this.hours = -1;
            this.minutes = -1;
            this.seconds = -1;
            if(times[0].charAt(0) == ' ')
                this.hours = Integer.parseInt(Character.toString(times[0].charAt(1)));
            else
                this.hours = Integer.parseInt(times[0]);
            if(times[1].charAt(0) == ' ')
                this.minutes = Integer.parseInt(Character.toString(times[1].charAt(1)));
            else
                this.minutes = Integer.parseInt(times[1]);
            if(times[2].charAt(0) == ' ')
                this.seconds = Integer.parseInt(Character.toString(times[2].charAt(1)));
            else
                this.seconds = Integer.parseInt(times[2]);
        }

        public boolean validate(){
            if(hours < 24 && minutes < 60 && seconds < 60)
                return true;
            return false;
        }

        public int compare(TripTime comparator)
        {
            if(this.hours < comparator.hours) { return -1; }
            else if(this.hours > comparator.hours) { return 1; }
            else {
                if(this.minutes < comparator.minutes) { return -1; }
                else if(this.minutes > comparator.minutes) { return 1; }
                else {
                    return Integer.compare(this.seconds, comparator.seconds);
                }
            }
        }

        public String toString()
        {
            return String.format("%d:%d:%d", hours, minutes, seconds);
        }
    }
}
