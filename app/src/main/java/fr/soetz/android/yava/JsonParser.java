package fr.soetz.android.yava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by soetz on 23/03/18.
 */

public final class JsonParser {

    public static Station parseStation(String jsonStation){
        Station resultStation = null;

        Map<String, String> stationMap = new HashMap<String, String>();
        String pureJsonStation = jsonStation.replaceAll("(\\{|\\}|\n)", "");

        String[] stationInformations = pureJsonStation.split(",");
        for(String infomation : stationInformations){
            String[] informationPair = infomation.split(":");
            //TODO retirer les espaces au début des values
            stationMap.put(informationPair[informationPair.length - 2].replaceAll("( |\n|\")", ""), informationPair[informationPair.length - 1]);
        }

        resultStation = new Station(stationMap);

        return(resultStation);
    }
}
