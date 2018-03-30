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

        pureJsonStation = ignoreCommaBetweenQuotes(pureJsonStation);
        String[] stationInformations = pureJsonStation.split(",");
        for(String infomation : stationInformations){
            String[] informationPair = infomation.split(":");
            stationMap.put(informationPair[informationPair.length - 2].replaceAll("( |\n|\")", ""), informationPair[informationPair.length - 1]);
        }

        resultStation = new Station(stationMap);

        return(resultStation);
    }

    public static ArrayList<Station> parseStations(String jsonStationsArray){

        ArrayList<Station> resultStations = new ArrayList<>();

        int index = 0;

        while(index < jsonStationsArray.length() - 1){
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
            resultStations.add(parseStation(jsonStationsArray.substring(start, end)));
        }

        return(resultStations);
    }

    private static String ignoreCommaBetweenQuotes(String input){
        String result = "";

        boolean betweenQuotes = false;

        int index = 0;

        while(index < input.length()){
            String character = input.substring(index, index + 1);

            if(character.equals("\"")){
                betweenQuotes = !betweenQuotes;
            }

            if(!(character.equals(",") && betweenQuotes)){
                result += character;
            }

            index += 1;
        }

        return(result);
    }
}
