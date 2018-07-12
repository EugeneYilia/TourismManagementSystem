package com.EugeneStudio.core;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Resources {
    private static ArrayList<String> places = new ArrayList<String>();
    private static ArrayList<Spot> spots = new ArrayList<Spot>();
    private static String distanceFileName = "document/spots.txt";
    private static String descriptionFileName = "document/spotsDescription.txt";
    private static int[][] distances;
    private static ArrayList<Description> descriptions = new ArrayList<Description>();
    private static ArrayList<Vertex> vertexes = new ArrayList<Vertex>();
    private static ArrayList<Point> points = new ArrayList<Point>();
    public static String record = "";

    static {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(distanceFileName)));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, "——");
                String firstPlace = stringTokenizer.nextToken();
                String secondPlace = stringTokenizer.nextToken();
                int distance = Integer.parseInt(stringTokenizer.nextToken());
                spots.add(new Spot(firstPlace, secondPlace, distance));
                if (!isExist(firstPlace)) {
                    places.add(firstPlace);
                }
                if (!isExist(secondPlace)) {
                    places.add(secondPlace);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(descriptionFileName)));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
                String spotName = stringTokenizer.nextToken();
                String spotDescription = stringTokenizer.nextToken();
                int loveDegree = Integer.parseInt(stringTokenizer.nextToken());
                boolean hasRestPlace = Boolean.parseBoolean(stringTokenizer.nextToken());
                boolean hasToilet = Boolean.parseBoolean(stringTokenizer.nextToken());
                descriptions.add(new Description(spotName, spotDescription, loveDegree, hasRestPlace, hasToilet));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        for (String place1: places) {
            int count = 0;
            for (String place2: places) {
                if (place1.equals(place2)) {
                    continue;
                } else {
                    if (getTwoPlaceDistance(place1, place2) < 32767) {
                        count++;
                    }
                }
            }
            vertexes.add(new Vertex(place1, count));
        }
    }

    static {
        for (String place: places) {
            points.add(new Point(place, 2));
        }
    }

    public static ArrayList<Point> getPoints() {
        return points;
    }

    private static boolean isExist(String name) {
        for (String place: places) {
            if (name.equals(place)) {
                return true;
            }
        }
        return false;
    }

    public static void showSpots() {
        for (Spot spot: spots) {
            System.out.println(spot.getSourcePlace() + "->" + spot.getDestinationPlace() + "   distance:" + spot.getDistance());
        }
    }

    public static boolean deleteRoad(String firstPlace, String secondPlace) {
        for (Spot spot: spots) {
            if (spot.getSourcePlace().equals(firstPlace) && spot.getDestinationPlace().equals(secondPlace)) {
                spots.remove(spot);
                return true;
            } else if (spot.getSourcePlace().equals(secondPlace) && spot.getDestinationPlace().equals(firstPlace)) {
                spots.remove(spot);
                return true;
            }
        }
        return false;
    }

    public static boolean addRoad(String firstPlace, String secondPlace, int distance) {
        for (Spot spot: spots) {
            if (spot.getSourcePlace().equals(firstPlace) && spot.getDestinationPlace().equals(secondPlace)) {
                spot.setDistance(distance);
                return true;
            } else if (spot.getSourcePlace().equals(secondPlace) && spot.getDestinationPlace().equals(firstPlace)) {
                spot.setDistance(distance);
                return true;
            }
        }
        if (isExist(firstPlace) && isExist(secondPlace)) {
            spots.add(new Spot(firstPlace, secondPlace, distance));
            return true;
        }
        return false;
    }


    public static boolean removePlace(String place) {
        for (String p: places) {
            if (p.equals(place)) {
                places.remove(p);
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    public static void showPlaces() {
        for (String place: places) {
            System.out.print(place + "  ");
        }
        System.out.println();
    }

    public static void printResult() {
        distances = new int[places.size()][places.size()];
        System.out.print("          ");
        for (String place: places) {
            System.out.print(place + "        ");
        }
        System.out.println();
        for (int i = 0; i < places.size(); i++) {
            for (int j = 0; j < places.size(); j++) {
                distances[i][j] = getTwoPlaceDistance(places.get(i), places.get(j));
            }
        }
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).length() == 2) {
                System.out.print(places.get(i) + "      ");
            } else {
                System.out.print(places.get(i) + "    ");
            }
            for (int j = 0; j < places.size(); j++) {
                if ((distances[i][j] / 10) == 0) {
                    System.out.print(distances[i][j] + "    " + "        ");
                } else if ((distances[i][j] / 100) == 0) {
                    System.out.print(distances[i][j] + "   " + "        ");
                } else if ((distances[i][j] / 1000) == 0) {
                    System.out.print(distances[i][j] + "  " + "        ");
                } else if ((distances[i][j] / 10000) == 0) {
                    System.out.print(distances[i][j] + " " + "        ");
                } else {
                    System.out.print(distances[i][j] + "        ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        //printResult();
        printVertexDegree();
    }

    public static void printVertexDegree() {
        for (Vertex vertex: vertexes) {
            System.out.println("景点名称->" + vertex.getSpotName() + "  与该景点相连的边数->" + vertex.getDegree());
        }
    }

    public static int getTwoPlaceDistance(String sourcePlace, String destinationPlace) {
        if (sourcePlace.equals(destinationPlace)) {
            return 0;
        }
        for (Spot spot: spots) {
            if (spot.getSourcePlace().equals(sourcePlace) && spot.getDestinationPlace().equals(destinationPlace)) {
                return spot.getDistance();
            } else if (spot.getDestinationPlace().equals(sourcePlace) && spot.getSourcePlace().equals(destinationPlace)) {
                return spot.getDistance();
            }
        }
        return 32767;
    }

    public static ArrayList<Description> getDescriptions() {
        return descriptions;
    }

    private static void filterUndeterminedSpots(ArrayList<DistanceSpot> existSpots, ArrayList<DistanceSpot> undeterminedSpots) {
        for (int i = 0; i < undeterminedSpots.size(); i++) {
            for (int j = 0; j < existSpots.size(); j++) {
                if (undeterminedSpots.get(i).getName().equals(existSpots.get(j).getName())) {
                    undeterminedSpots.remove(undeterminedSpots.get(i));
                    break;
                }
            }
        }
    }

    public static void getShortestDistance(String spot1, String spot2) {

        ArrayList<DistanceSpot> distanceSpots = new ArrayList<DistanceSpot>();//已选择的节点集合
        ArrayList<String> pplaces = new ArrayList<String>();
        ArrayList<DistanceSpot> undeterminedSpots = new ArrayList<DistanceSpot>();//待选择的节点集合
        ArrayList<String> allPlaces = getDuplicatePlaces();//所有的节点集合

        //System.out.println("初始的时候");
        //System.out.println("allPlaces.size():" + allPlaces.size());
        //System.out.println("distanceSpots.size():" + distanceSpots.size());

        if (!isExist(spot1) || !isExist(spot2)) {
            System.out.println("至少有一个景点不存在");
            return;
        } else {
            //Core start
            DistanceSpot currentDistanceSpot, previousDistanceSpot;
            currentDistanceSpot = previousDistanceSpot = new DistanceSpot(null, 0, spot1);
            distanceSpots.add(currentDistanceSpot);
            allPlaces.remove(spot1);
            System.out.println(spot1);
            //System.out.println("allPlaces.size():" + allPlaces.size());
            //System.out.println("distanceSpots.size():" + distanceSpots.size());
            //int count = 0;

            while (true) {//Search road start
                //System.out.println(count++);
                boolean isAchieveable = false;
                for (DistanceSpot distanceSpot: distanceSpots) {//遍历已选择节点的集合
                    for (String place: allPlaces) {
                        filterUndeterminedSpots(distanceSpots, undeterminedSpots);
                        boolean isSelected = false;
                        for (DistanceSpot distanceSpot2: distanceSpots) {
                            if (distanceSpot2.getName().equals(place)) {
                                isSelected = true;
                            }
                        }
                        if (isSelected) {
                            continue;
                        }

                        if (getTwoPlaceDistance(distanceSpot.getName(), place) < 32767) {//Two place is
                            for (DistanceSpot undeterminedDistanceSpot: undeterminedSpots) {
                                if (undeterminedDistanceSpot.getName().equals(place)) {
                                    if ((getTwoPlaceDistance(distanceSpot.getName(), place) + distanceSpot.getDistanceSum()) < undeterminedDistanceSpot.getDistanceSum()) {
                                        undeterminedDistanceSpot.setPreviousDistanceSpot(distanceSpot);
                                        undeterminedDistanceSpot.setDistanceSum((getTwoPlaceDistance(distanceSpot.getName(), place) + distanceSpot.getDistanceSum()));
                                    }
                                    break;
                                }
                            }
                            undeterminedSpots.add(new DistanceSpot(distanceSpot, (getTwoPlaceDistance(distanceSpot.getName(), place) + distanceSpot.getDistanceSum()), place));
                        }
                        isAchieveable = true;
                    }
                    if (!isAchieveable) {
                        System.out.println("两景点之间不可到达");
                        return;
                    }
                }
                String nextSpot = "";
                int shortestDistance = 32767;
                for (DistanceSpot undeterminedSpot: undeterminedSpots) {
                    if (undeterminedSpot.getDistanceSum() < shortestDistance) {
                        shortestDistance = undeterminedSpot.getDistanceSum();
                        nextSpot = undeterminedSpot.getName();
                    }
                }
                //DistanceSpot tempDistanceSpot = currentDistanceSpot;

                pplaces.add(nextSpot);


//                System.out.println("allPlaces before remove size" + allPlaces.size());
                String deleteObject = null;
                for (int i = 0; i < allPlaces.size(); i++) {
                    if (allPlaces.get(i).equals(nextSpot)) {
//                        System.out.println("找到要删除的景点");
                        deleteObject = allPlaces.get(i);
                        break;
                    }
                }
                allPlaces.remove(deleteObject);
//                System.out.println("allPlaces after remove size" + allPlaces.size());

//                System.out.println("undeterminedSpots before remove size" + undeterminedSpots.size());
                DistanceSpot deleteDistanceObject = null;
                for (int i = 0; i < undeterminedSpots.size(); i++) {
                    if (undeterminedSpots.get(i).getName().equals(nextSpot)) {
//                        System.out.println("找到要删除的景点");
                        deleteDistanceObject = undeterminedSpots.get(i);
                        break;
                    }
                }
                undeterminedSpots.remove(deleteDistanceObject);
//                System.out.println("undeterminedSpots after remove size" + undeterminedSpots.size());

                currentDistanceSpot = deleteDistanceObject;
                //previousDistanceSpot = tempDistanceSpot;
                boolean isExist = false;
                for (DistanceSpot distanceSpot: distanceSpots) {
                    if (distanceSpot.getName().equals(nextSpot)) {
                        isExist = true;
                    }
                }
                if (!isExist) {
                    distanceSpots.add(currentDistanceSpot);
                }

                System.out.println(nextSpot);
//                System.out.println("allPlaces.size():" + allPlaces.size());
//                System.out.println("distanceSpots.size():" + distanceSpots.size());

                if (nextSpot.equals(spot2)) {
                    //DistanceSpot distanceSpot ;
                    //for(DistanceSpot distanceSpot1:)
                    System.out.println("寻路到达终点");
                    System.out.println("共需要走的路程为:" + deleteDistanceObject.getDistanceSum());
                    System.out.println("所需要经过的景点包括:");
                    ArrayList<String> spots = new ArrayList<String>();
                    while (true) {
                        spots.add(deleteDistanceObject.getName());
                        if (deleteDistanceObject.getPreviousDistanceSpot() == null) {
                            break;
                        }
                        deleteDistanceObject = deleteDistanceObject.getPreviousDistanceSpot();
                    }
                    for (int i = spots.size() - 1; i >= 0; i--) {
                        if (i == 0) {
                            System.out.println(spots.get(i));
                        } else {
                            System.out.print(spots.get(i) + "-->");
                        }
                    }
                    return;
                }
            }//search road end

            //Core end
        }
    }

    public static void setDescriptionFileName(String descriptionFileName) {
        Resources.descriptionFileName = descriptionFileName;
    }

    public static ArrayList<String> getPlaces() {
        return places;
    }

    public static ArrayList<String> getDuplicatePlaces() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String place: places) {
            arrayList.add(place);
        }
        return arrayList;
    }

    public static ArrayList<Spot> getSpots() {
        return spots;
    }

    @Test
    public void testSpots() {
        System.out.println("#############show spots#############");
        showSpots();
        System.out.println("$$$$$$$$$$$$$show spots$$$$$$$$$$$$$");
    }

    @Test
    public void testPlaces() {
        System.out.println("#############show places#############");
        showPlaces();
        System.out.println("$$$$$$$$$$$$$show places$$$$$$$$$$$$$");
    }
}
