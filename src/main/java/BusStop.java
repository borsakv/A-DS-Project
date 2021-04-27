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

}