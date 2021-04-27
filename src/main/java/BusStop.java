import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BusStop
{
    public int stopId;
    public int stopCode;
    public String stopName;
    public String stopDescription;
    public double stopLatitude;
    public double stopLongitude;
    private String zoneId;
    private String stopUrl;
    private int locationType;
    private String parentStation;

    public BusStop(int stopId, int stopCode, String stopName, String stopDescription, double stopLatitude, double stopLongitude, String zoneId, String stopUrl, int locationType, String parentStation) {
        this.stopId = stopId;
        this.stopCode = stopCode;
        this.stopName = stopName;
        this.stopDescription = stopDescription;
        this.stopLatitude = stopLatitude;
        this.stopLongitude = stopLongitude;
        this.zoneId = zoneId;
        this.stopUrl = stopUrl;
        this.locationType = locationType;
        this.parentStation = parentStation;
    }
    public void print(){
        System.out.println(stopId + ", " + stopCode + ", " + stopName + ", " + stopDescription + ", " + stopLatitude + ", " + stopLongitude + ", " + zoneId + ", " + stopUrl + ", " + locationType + ", " + parentStation);
    }

    /* Someone can maybe make use of this code in the reading in functions
    public String alterStopName(String nameString)
    {
        String[] name = nameString.split(" ", 2);
        if(name[0].equals("FLAGSTOP") || name[0].equals("WB") || name[0].equals("NB") || name[0].equals("SB") || name[0].equals("EB"))
            nameString= name[1] + " " + name[0];
        return nameString;
    }
     */
    private static String moveInfo(String input){
        String keyword = input.substring(0, 2).strip();
        if (keyword.toUpperCase().equals("WB") || keyword.toUpperCase().equals("SB") || keyword.toUpperCase().equals("NB") || keyword.toUpperCase().equals("EB")) {
            return input.substring(3).trim() + input.substring(2,3) + input.substring(0,2);
        }
        return input;
    }

    public static ArrayList<BusStop> readStops(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            ArrayList<BusStop> stops = new ArrayList<>();
            scanner.nextLine();
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] stopInfo = line.split(",");
                System.out.println(Arrays.toString(stopInfo));
                String parentStation = "";
                int stopId = -1, stopCode = -1, locationType = -1;
                double stopLatitude = -1.0, stopLongitude = -1.0;
                try {
                    parentStation = stopInfo[9];
                } catch (ArrayIndexOutOfBoundsException ignored) {}
                try {
                    stopId = Integer.parseInt(stopInfo[0]);
                } catch (NumberFormatException ignored) {}
                try {
                    stopCode = Integer.parseInt(stopInfo[1]);
                } catch (NumberFormatException ignored) {}
                try {
                    stopLatitude = Double.parseDouble(stopInfo[4]);
                } catch (NumberFormatException ignored) {}
                try {
                    stopLongitude = Double.parseDouble(stopInfo[5]);
                } catch (NumberFormatException ignored) {}
                try {
                    locationType = Integer.parseInt(stopInfo[8]);
                } catch (NumberFormatException ignored) {}
                stops.add(new BusStop(stopId, stopCode, moveInfo(stopInfo[2]), stopInfo[3], stopLatitude, stopLongitude, stopInfo[6], stopInfo[7], locationType, parentStation));
                stops.get(stops.size()-1).print();
            }
            return stops;
        } catch (FileNotFoundException e) {
            System.out.println(filename);
            e.printStackTrace();
            return null;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        } catch (ArrayIndexOutOfBoundsException ignored) {
          return null;
        }
    }

}