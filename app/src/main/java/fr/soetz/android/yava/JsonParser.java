package fr.soetz.android.yava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public static ArrayList<Station> parseStations(String jsonStationsArray){

        ArrayList<Station> resultStations = new ArrayList<>();

        int index = 0;

        while(index < jsonStationsArray.length()){
            String elem = jsonStationsArray.substring(index, index + 1);

            while(!elem.equals("{")){
                index += 1;
                elem = jsonStationsArray.substring(index, index + 1);
            }

            int start = index;
            int level = 1;

            index += 1;
            elem = jsonStationsArray.substring(index, index + 1);

            while(level > 0){
                if(elem.equals("{")){
                    level += 1;
                }
                else if(elem.equals("}")){
                    level -= 1;
                }
                index += 1;
                elem = jsonStationsArray.substring(index, index + 1);
            }

            int end = index;
            if(resultStations.size() == 31) break;
            resultStations.add(parseStation(jsonStationsArray.substring(start, end)));
        }

        return(resultStations);
    }
}
