public class BusStop
{
    private int stopId;
    private int stopCode;
    public String stopName;
    public String stopDescription;
    public RouteSection[] routesSections;

    /* Do we need these?

    public int stopLatitude;
    public int stopLongitude;
    private String zoneId;
    private String stopUrl;
    private String locationType;
    private String parentStation;*/

    public BusStop(int stopId, int stopCode, String stopName, String stopDescription)
    {
        this.stopId= stopId;
        this.stopCode= stopCode;
        this.stopName= alterStopName(stopName);
        this.stopDescription= stopDescription;
    }

    public int getStopId()
    {
        return stopId;
    }

    public int getStopCode()
    {
        return stopCode;
    }

    public String alterStopName(String nameString)
    {
        String[] name = nameString.split(" ", 2);
        if(name[0].equals("FLAGSTOP") || name[0].equals("WB") || name[0].equals("NB") || name[0].equals("SB") || name[0].equals("EB"))
            nameString= name[1] + " " + name[0];
        return nameString;
    }
}