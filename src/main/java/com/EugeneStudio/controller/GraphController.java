package com.EugeneStudio.controller;

import com.EugeneStudio.core.Resources;
import com.EugeneStudio.core.Spot;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;

public class GraphController {
    public static void getGraphInformation(RoutingContext routingContext) {
        String returnString = "{";
        ArrayList<String> places = Resources.getPlaces();
        ArrayList<Spot> spots = Resources.getSpots();
        returnString += "\"nodes\":[";
        int step = 30;
        for (int i = 0; i < places.size(); i++) {
            returnString += "{\"data\":{\"id\":\"" + places.get(i) + "\", \"foo\": 1, \"bar\": 1, \"baz\": 7}, \"position\": { \"x\": " + 400+step + ", \"y\": " + 400+step + " } }";
            if (i != places.size() - 1) {
                returnString += ",";
            }
        }
        returnString += "],";
        returnString += "\"edges\":[";
        for (int i = 0; i < spots.size(); i++) {
            returnString += "{\"data\":{\"id\":\"" + spots.get(i).getSourcePlace() + "-" + spots.get(i).getDestinationPlace() + "\",\"weight\":" + spots.get(i).getDistance() + ",\"source\":\"" + spots.get(i).getSourcePlace() + "\",\"target\":\"" + spots.get(i).getDestinationPlace() + "\"}}";
            if (i != spots.size() - 1) {
                returnString += ",";
            }
        }
        returnString += "]}";
        routingContext.request().response().putHeader("content-type", "application/json")
                .end(returnString);
    }
}
