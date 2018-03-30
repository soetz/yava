package fr.soetz.android.yava;

/**
 * Created by soetz on 23/03/18.
 */

public class Position {
    private double latitude;
    private double longitude;

    public Position(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return(latitude);
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return(longitude);
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String toString() {
        return("(" + getLatitude() + ", " + getLongitude() + ")");
    }
}
