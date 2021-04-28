import javafx.beans.property.*;

public class BusStop
{
    public int stopId;
    private final IntegerProperty stopIdProperty;
    public int stopCode;
    private final IntegerProperty stopCodeProperty;
    public String stopName;
    private final StringProperty stopNameProperty;
    public String stopDescription;
    private final StringProperty stopDescriptionProperty;
    public double stopLatitude;
    private final DoubleProperty stopLatitudeProperty;
    public double stopLongitude;
    private final DoubleProperty stopLongitudeProperty;
    private String zoneId;
    private String stopUrl;
    private int locationType;
    private String parentStation;

    public BusStop(int stopId, int stopCode, String stopName, String stopDescription, double stopLatitude, double stopLongitude, String zoneId, String stopUrl, int locationType, String parentStation) {
        this.stopId = stopId;
        stopIdProperty = new SimpleIntegerProperty(stopId);
        this.stopCode = stopCode;
        stopCodeProperty = new SimpleIntegerProperty(stopCode);
        this.stopName = stopName;
        stopNameProperty = new SimpleStringProperty(stopName);
        this.stopDescription = stopDescription;
        stopDescriptionProperty = new SimpleStringProperty(stopDescription);
        this.stopLatitude = stopLatitude;
        stopLatitudeProperty = new SimpleDoubleProperty(stopLatitude);
        this.stopLongitude = stopLongitude;
        stopLongitudeProperty = new SimpleDoubleProperty(stopLongitude);
        this.zoneId = zoneId;
        this.stopUrl = stopUrl;
        this.locationType = locationType;
        this.parentStation = parentStation;
    }

    public int getStopIdProperty() {
        return stopIdProperty.get();
    }

    public void setStopIdProperty(int stopId){
        stopIdProperty.set(stopId);
    }

    public int getStopCodeProperty() {
        return stopCodeProperty.get();
    }

    public void setStopCodeProperty(int stopCode){
        stopCodeProperty.set(stopCode);
    }

    public String getStopNameProperty() {
        return stopNameProperty.get();
    }

    public void setStopNameProperty(String stopName){
        stopNameProperty.set(stopName);
    }

    public String getStopDescriptionProperty() {
        return stopDescriptionProperty.get();
    }

    public void setStopDescriptionProperty(String stopDescription){
        stopDescriptionProperty.set(stopDescription);
    }

    public double getStopLatitudeProperty() {
        return stopLatitudeProperty.get();
    }

    public void setStopLatitudeProperty(double stopLatitude){
        stopLatitudeProperty.set(stopLatitude);
    }

    public double getStopLongitudeProperty() {
        return stopLongitudeProperty.get();
    }

    public void setStopLongitudeProperty(int stopLongitude){
        stopLongitudeProperty.set(stopLongitude);
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