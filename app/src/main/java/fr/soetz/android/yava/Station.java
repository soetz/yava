package fr.soetz.android.yava;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by soetz on 23/03/18.
 */

public class Station {
    private int number;
    private String contractName;
    private String name;
    private String address;
    private Position position;
    private boolean banking;
    private boolean bonus;
    private String status;
    private int bikeStands;
    private int availableBikeStands;
    private int availableBikes;
    private long timestamp;

    public Station(int number, String contractName, String name, String address, Position position, boolean banking, boolean bonus, String status, int bikeStands, int availableBikeStands, int availableBikes, long timestamp) {
        this.number = number;
        this.contractName = contractName;
        this.name = name;
        this.address = address;
        this.position = position;
        this.banking = banking;
        this.bonus = bonus;
        this.status = status;
        this.bikeStands = bikeStands;
        this.availableBikeStands = availableBikeStands;
        this.availableBikes = availableBikes;
        this.timestamp = timestamp;
    }

    public Station(Map<String, String> stationMap){
        double lat = 0.0;
        double lon = 0.0;

        for(Map.Entry<String, String> entry : stationMap.entrySet()){
            switch(entry.getKey()){
                case "number" :
                    number = readInt(entry.getValue());
                    break;
                case "contract_name" :
                    contractName = readString(entry.getValue());
                    break;
                case "name" :
                    name = readString(entry.getValue());
                    break;
                case "address" :
                    address = readString(entry.getValue());
                    break;
                case "lat" :
                    lat = readDouble(entry.getValue());
                    break;
                case "lon" :
                    lon = readDouble(entry.getValue());
                    break;
                case "banking" :
                    banking = readBoolean(entry.getValue());
                    break;
                case "bonus" :
                    bonus = readBoolean(entry.getValue());
                    break;
                case "status" :
                    status = readString(entry.getValue());
                    break;
                case "bike_stands" :
                    bikeStands = readInt(entry.getValue());
                    break;
                case "available_bike_stands" :
                    availableBikeStands = readInt(entry.getValue());
                    break;
                case "available_bikes" :
                    availableBikes = readInt(entry.getValue());
                    break;
                case "last_update" :
                    timestamp = readLong(entry.getValue());
                    break;
            }
        }

        position = new Position(lat, lon);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isBanking() {
        return banking;
    }

    public void setBanking(boolean banking) {
        this.banking = banking;
    }

    public boolean isBonus() {
        return bonus;
    }

    public void setBonus(boolean bonus) {
        this.bonus = bonus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBikeStands() {
        return bikeStands;
    }

    public void setBikeStands(int bikeStands) {
        this.bikeStands = bikeStands;
    }

    public int getAvailableBikeStands() {
        return availableBikeStands;
    }

    public void setAvailableBikeStands(int availableBikeStands) {
        this.availableBikeStands = availableBikeStands;
    }

    public int getAvailableBikes() {
        return availableBikes;
    }

    public void setAvailableBikes(int availableBikes) {
        this.availableBikes = availableBikes;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private String readString(String str){
        String finalString = null;

        if(str.substring(0, 1).equals(" ")){
            str = str.substring(1);
        }

        finalString = str.replaceAll("\"", "");

        return(finalString);
    }

    private int readInt(String str){
        int finalInt = 0;

        if(str.substring(0, 1).equals(" ")){
            str = str.substring(1);
        }

        finalInt = new Integer(str).intValue();

        return(finalInt);
    }

    private long readLong(String str){
        long finalLong = 0;

        if(str.substring(0, 1).equals(" ")){
            str = str.substring(1);
        }

        finalLong = new Long(str).longValue();

        return(finalLong);
    }

    private double readDouble(String str){
        double finalDouble = 0.0;

        if(str.substring(0, 1).equals(" ")){
            str = str.substring(1);
        }

        finalDouble = new Double(str).doubleValue();

        return(finalDouble);
    }

    private boolean readBoolean(String str){
        boolean finalBoolean = false;

        if(str.substring(0, 1).equals(" ")){
            str = str.substring(1);
        }

        finalBoolean = new Boolean(str).booleanValue();

        return(finalBoolean);
    }

    public String toString(){
        String str = "Station";
        str += " #" + getNumber() + "\n";
        str += "  Contract: " + getContractName() + "\n";
        str += "  Name: " + getName() + "\n";
        str += "  Address: " + getAddress() + "\n";
        str += "  Position: " + getPosition() + "\n";
        str += "  Banking: " + isBanking() + "\n";
        str += "  Bonus: " + isBonus() + "\n";
        str += "  Status: " + getStatus() + "\n";
        str += "  Bike Stands: " + getBikeStands() + "\n";
        str += "  Available Bike Stands: " + getAvailableBikeStands() + "\n";
        str += "  Available Bikes: " + getAvailableBikes() + "\n";
        str += "  Timestamp: " + getTimestamp() + "\n";
        return(str);
    }
}
