import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TripDatabase {

    public ArrayList<TripSection> database;

    //a constructor of RouteSection needed to initialise the object and ArrayList
    public TripDatabase() {
        database = new ArrayList<>();
        readTheStopTimeFile();
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
                //reads in the next line and splits it by a comma and a space to get all of the varaibles
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

                if(arrivalTime.valid || departureTime.valid)
                    database.add(new TripSection(tripID, stopID, arrivalTime, departureTime, stopSequence, stopHeadsign, pickupType, dropOffType, distTraveled));
            }
            scanner.close();

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    // Class to store and parse time from the format (HH:MM:SS) and also to make sure that the time is valid
    static public class TripTime{
        public int hours;
        public int minutes;
        public int seconds;
        public boolean valid;

        public TripTime(String time){ updateTime(time); }

        public void updateTime(String time){
            String[] times = time.split(":");
            int hours = -1;
            int minutes = -1;
            int seconds = -1;
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
            checkIfValid();
        }

        public void checkIfValid(){
            if(hours < 24 || minutes < 60 || seconds < 60)
                valid = true;
            else
                valid = false;
        }
    }
}
