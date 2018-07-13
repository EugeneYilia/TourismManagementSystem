package com.EugeneStudio.core;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CLIBootstrap {
    public static Scanner scanner = new Scanner(System.in);
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static PrintWriter printWriter = null;

    static {
        try {
            printWriter = new PrintWriter(new FileWriter(new File("document/log.txt"), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void start() {//自动从文件读取，创建景区景点分布图   cli start
        while (true) {
            System.out.println("1.管理员登陆");
            System.out.println("2.景点查找");
            System.out.println("3.景点排序");
            System.out.println("4.两景点之间的最短距离和路径");//路径就是途中会经过哪些景点
            System.out.println("5.输出导游路线图(最短哈密尔顿回路)");
            System.out.println("6.输出车辆的进出信息");
            System.out.println("7.输出景区景点分布图");
            System.out.println("0.退出");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                managerLogin();
            } else if (input.equals("2")) {
                searchSpot();
            } else if (input.equals("3")) {
                sortSpot();
            } else if (input.equals("4")) {
                System.out.println("请输入第一个景点的名称");
                String spot1 = scanner.nextLine();
                System.out.println("请输入第二个景点的名称");
                String spot2 = scanner.nextLine();
                Resources.getShortestDistance(spot1, spot2);
            } else if (input.equals("5")) {
                generateRoute();
            } else if (input.equals("6")) {
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new FileReader(new File("document/log.txt")));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (input.equals("7")) {
                Resources.printResult();
            } else if (input.equals("0")) {
                closeResource();
                return;
            }
        }
    }

    public static void generateRoute() {//生成最小哈密尔顿回路，求出所有的哈密尔顿回路并求出每个哈密尔顿回路的总路线长度
        //在构建哈密尔顿回路过程中，一个点应该有且只有一个入边和出边
        ArrayList<String> allPlaces = Resources.getDuplicatePlaces();
        ArrayList<String> selectedPlaces = new ArrayList<String>();
        ArrayList<Vertex> vertexes = Resources.getVertexes();
        ArrayList<Point> allPoints = Resources.getPoints();
        ArrayList<Point> existPoints = new ArrayList<Point>();
        //System.out.println(vertexes.size());
        for (Vertex vertex: vertexes) {
            if (vertex.getDegree() == 2) {
                System.out.println("度数为2的景点有" + vertex.getSpotName());
                Resources.record += " " + vertex.getSpotName() + " ";
                selectedPlaces.add(vertex.getSpotName());
                allPlaces.remove(vertex.getSpotName());
                Point removedPoint = null;
                for (Point point: allPoints) {
                    if (point.getSpotName().equals(vertex.getSpotName())) {
                        removedPoint = point;
                        break;
                    }
                }
                allPoints.remove(removedPoint);
                existPoints.add(removedPoint);
                for (String place: Resources.getPlaces()) {
                    if (place.equals(vertex.getSpotName())) {
                        continue;
                    }
                    if (Resources.getTwoPlaceDistance(vertex.getSpotName(), place) < 32767) {
                        if (Resources.record.contains(" " + vertex.getSpotName())) {
                            Resources.record = Resources.record.replaceAll(" " + vertex.getSpotName(), " " + place + "-" + vertex.getSpotName());
                            /*if (!Resources.record.contains(place)) {
                                Resources.record = Resources.record.replaceAll(" " + vertex.getSpotName(), " " + place + "-" + vertex.getSpotName());
                            } else {
                                if (Resources.record.contains(" " + place)) {

                                } else if (Resources.record.contains(place + " ")) {

                                }
                            }*/
                        } else if (Resources.record.contains(vertex.getSpotName() + " ")) {
                            Resources.record = Resources.record.replaceAll(vertex.getSpotName() + " ", vertex.getSpotName() + "-" + place + " ");
                            /*if (!Resources.record.contains(place)) {
                                Resources.record = Resources.record.replaceAll(vertex.getSpotName() + " ", vertex.getSpotName() + "-" + place + " ");
                            } else {
                                if (Resources.record.contains(" " + place)) {

                                } else if (Resources.record.contains(place + " ")) {

                                }
                            }*/
                        }
                        System.out.println("与" + vertex.getSpotName() + "相连的有" + place);
                        boolean isExistInAllPlaces = false;
                        for (String existPlace: allPlaces) {
                            if (existPlace.equals(place)) {
                                isExistInAllPlaces = true;
                            }
                        }
                        if (isExistInAllPlaces) {
                            allPlaces.remove(place);
                        }

                        boolean isExistInSelectedPlaces = false;
                        for (String selectedPlace: selectedPlaces) {
                            if (selectedPlace.equals(place)) {
                                isExistInSelectedPlaces = true;
                            }
                        }
                        if (!isExistInSelectedPlaces) {
                            selectedPlaces.add(place);
                            System.out.println(place + "被加入到选择集合中");
                        } else {
                            System.out.println(place + "已存在于选择集合中");
                        }
                    }
                }
            }
        }
        //System.out.println(selectedPlaces.size());

        /*
        System.out.println("已选择的集合中的景点");
        for (String selectedPlace: selectedPlaces) {
            System.out.print(selectedPlace + "        ");
        }
        System.out.println();

        System.out.println("还未加入选择集合中的元素");
        for (String leftPlace: allPlaces) {
            System.out.print(leftPlace + "        ");
        }
        System.out.println();*/

        System.out.println(Resources.record);
        Resources.record = combineSpots(Resources.record);

        System.out.println(Resources.record);
        System.out.println("还未加入选择集合中的元素");
        for (String leftPlace: allPlaces) {
            System.out.print(leftPlace + "        ");
        }
        System.out.println();
        finalStepToGenerateRoute(allPlaces);
    }

    public static String finalStepToGenerateRoute(ArrayList<String> leftPlaces) {//滤掉还需要连接一个边的且只有一个边可供其相连
        //leftPlaces中每一个景点的权重为2，还可以连接两个边
        //Resources.record中路线的边缘节点权重为1，还可以连接一个边
        String initialRecord = Resources.record;
        System.out.println("开始过滤");
        //int leftPlacesSize = leftPlaces.size();
        String finalRoute = "";
        StringTokenizer stringTokenizer = new StringTokenizer(Resources.record, " ");
        ArrayList<Point> leftSpots = new ArrayList<Point>();
        while (stringTokenizer.hasMoreTokens()) {
            StringTokenizer edgeSpots = new StringTokenizer(stringTokenizer.nextToken(), "-");
            int number = edgeSpots.countTokens();
            for (int i = 0; i < number; i++) {
                String spotName = edgeSpots.nextToken();
                if (i == 0) {
                    leftSpots.add(new Point(spotName, 1));
                } else if (i == (number - 1)) {
                    leftSpots.add(new Point(spotName, 1));
                }
            }
        }
        for (String place: leftPlaces) {
            leftSpots.add(new Point(place, 2));
        }
        int leftSpotsSize = leftSpots.size();

        ArrayList<Vertex> vertexes = new ArrayList<Vertex>();
        System.out.println("理论上还可连接的边的数目");
        for (Point point: leftSpots) {
            System.out.println(point.getSpotName() + "  " + point.getEdgeNumber());
            vertexes.add(new Vertex(point.getSpotName(), 0));
        }

        for (Point point1: leftSpots) {
            for (Point point2: leftSpots) {
                if (point1.getSpotName().equals(point2.getSpotName())) {
                    continue;
                }
                if (Resources.getTwoPlaceDistance(point1.getSpotName(), point2.getSpotName()) < 32767) {
                    for (Vertex vertex: vertexes) {
                        if (vertex.getSpotName().equals(point1.getSpotName())) {
                            if (!Resources.record.contains(point1.getSpotName() + "-" + point2.getSpotName()) && !Resources.record.contains(point2.getSpotName() + "-" + point1.getSpotName())) {
                                vertex.setDegree((vertex.getDegree() + 1));
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("实际还有可与其连接边的数目");
        for (Vertex vertex: vertexes) {
            System.out.println(vertex.getSpotName() + "  " + vertex.getDegree());
        }

        int count = 0;
        String spotName = "";
        boolean isFound = false;
        for (Vertex vertex: vertexes) {//实际
            for (Point point: leftSpots) {//理论
                if (point.getSpotName().equals(vertex.getSpotName())) {
                    if (point.getEdgeNumber() == 1 && point.getEdgeNumber() == vertex.getDegree()) {
                        spotName = point.getSpotName();
                        count++;
                        isFound = true;
                        break;
                    } /*else if(point.getEdgeNumber() == 2 && point.getEdgeNumber() == vertex.getDegree()) {
                        spotName = point.getSpotName();
                        count++;
                        break;
                    }*/
                }
            }
        }

        if (!isFound) {
            for (Vertex vertex: vertexes) {//实际
                for (Point point: leftSpots) {//理论
                    if (point.getSpotName().equals(vertex.getSpotName())) {
                    /*if (point.getEdgeNumber() == 1 && point.getEdgeNumber() == vertex.getDegree()) {
                        spotName = point.getSpotName();
                        count++;
                        break;
                    } else*/
                        if (point.getEdgeNumber() == 2 && point.getEdgeNumber() == vertex.getDegree()) {
                            spotName = point.getSpotName();
                            count++;
                            break;
                        }
                    }
                }
            }
        }

        String destinationSpot = "";
        for (Point point2: leftSpots) {
            if (point2.getSpotName().equals(spotName)) {
                continue;
            }
            if (Resources.getTwoPlaceDistance(spotName, point2.getSpotName()) < 32767 && !Resources.record.contains(spotName + "-" + point2.getSpotName()) && !Resources.record.contains(point2.getSpotName() + "-" + spotName)) {
                destinationSpot = point2.getSpotName();
            }
        }
        if (spotName.equals("")) {
            return Resources.record;
        }
        System.out.println(spotName + "可连接到" + destinationSpot + "上面");
        if (Resources.record.contains(destinationSpot)) {
           /* if (Resources.record.contains(spotName)) {//同时具有spotName和destinationSpot并且要将这个景点连接起来，也就是意味着这是哈密尔顿回路的最后一步连接操作
                StringTokenizer finalTokenizer = new StringTokenizer(Resources.record, "-");
                String firstSpot = finalTokenizer.nextToken();
                //Resources.record = Resources.record.substring(0, Resources.record.length() - 1);
                Resources.record += "-" + firstSpot;
                System.out.println("最终生成的哈密尔顿回路是    " + Resources.record);
                return Resources.record;
            }*/
            if (Resources.record.contains(" " + destinationSpot)) {
                Resources.record = Resources.record.replaceAll(" " + destinationSpot, " " + spotName + "-" + destinationSpot);
            } else if (Resources.record.contains(destinationSpot + " ")) {
                Resources.record = Resources.record.replaceAll(destinationSpot + " ", destinationSpot + "-" + spotName + " ");
            }
            boolean isExist = false;
            for (String place: leftPlaces) {
                if (place.equals(destinationSpot)) {
                    isExist = true;
                    break;
                }
            }
            if (isExist) {
                leftPlaces.remove(destinationSpot);
            }

            boolean spotNameIsExist = false;
            for (String place: leftPlaces) {
                if (place.equals(spotName)) {
                    spotNameIsExist = true;
                    break;
                }
            }
            if (spotNameIsExist) {
                leftPlaces.remove(spotName);
            }
        } else {
            if (Resources.record.contains(" " + spotName)) {
                Resources.record = Resources.record.replaceAll(" " + spotName, " " + destinationSpot + "-" + spotName);
            } else if (Resources.record.contains(spotName + " ")) {
                Resources.record = Resources.record.replaceAll(spotName + " ", spotName + "-" + destinationSpot + " ");
            } else if (!Resources.record.contains(spotName)) {//spotName和destinationSpot都不包含
                Resources.record += " " + spotName + "-" + destinationSpot;
            }
            boolean isExist = false;
            for (String place: leftPlaces) {
                if (place.equals(destinationSpot)) {
                    isExist = true;
                    break;
                }
            }
            if (isExist) {
                leftPlaces.remove(destinationSpot);
            }

            boolean spotNameIsExist = false;
            for (String place: leftPlaces) {
                if (place.equals(spotName)) {
                    spotNameIsExist = true;
                    break;
                }
            }
            if (spotNameIsExist) {
                leftPlaces.remove(spotName);
            }
        }
        System.out.println(Resources.record);
        Resources.record = combineSpots(Resources.record);
        System.out.println(Resources.record);
        finalRoute = Resources.record;


        for (Point point1: leftSpots) {
            if (point1.getSpotName().equals(spotName)) {
                point1.setEdgeNumber((point1.getEdgeNumber() - 1));
                if (point1.getEdgeNumber() == 0) {
                    Point point = null;
                    for (Point point3: leftSpots) {
                        if (point3.getSpotName().equals(spotName)) {
                            point = point3;
                            break;
                        }
                    }
                    leftSpots.remove(point);
                    System.out.println("成功移除节点" + point.getSpotName());
                    break;
                }
            }
        }

        //System.out.println(destinationSpot);
        for (Point point1: leftSpots) {
            if (point1.getSpotName().equals(destinationSpot)) {
                point1.setEdgeNumber((point1.getEdgeNumber() - 1));
                if (point1.getEdgeNumber() == 0) {
                    Point point = null;
                    for (Point point3: leftSpots) {
                        if (point3.getSpotName().equals(destinationSpot)) {
                            point = point3;
                            break;
                        }
                    }
                    leftSpots.remove(point);
                    System.out.println("成功移除节点" + point.getSpotName());
                    break;
                }
            }
        }
        System.out.println("可再次筛掉的景点的数目为" + (leftSpotsSize - leftSpots.size()));
        System.out.println("剩余节点数目为" + leftSpots.size());

//        if (leftSpots.size() == leftSpotsSize) {
//        if (initialRecord.equals(Resources.record)) {
        if (leftSpots.size() == 0) {
            Resources.record = Resources.record.substring(0, Resources.record.length() - 1);
            StringTokenizer finalTokenizer = new StringTokenizer(Resources.record, "-");
            String firstSpot = finalTokenizer.nextToken();
            //Resources.record = Resources.record.substring(0, Resources.record.length() - 1);
            Resources.record += "-" + firstSpot;
            System.out.println("最终生成的哈密尔顿回路是    " + "Start->" + Resources.record + "->End");
            return finalRoute;
        } else {
            finalStepToGenerateRoute(leftPlaces);
            return finalRoute;
        }

    }

    public static String combineSpots(String spots) {
        String returnString = "";
        StringTokenizer stringTokenizer = new StringTokenizer(spots, " ");
        int segmentNumber = stringTokenizer.countTokens();
        ArrayList<String> segments[] = new ArrayList[segmentNumber];
        for (int i = 0; i < segmentNumber; i++) {
            segments[i] = new ArrayList<String>();
        }
        int count = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String line = stringTokenizer.nextToken();
            System.out.println(line);
            StringTokenizer segmentTokenizer = new StringTokenizer(line, "-");
            while (segmentTokenizer.hasMoreTokens()) {
                segments[count].add(segmentTokenizer.nextToken());
            }
            count++;
        }
        for (int i = 0; i < segmentNumber; i++) {
            for (int j = 0; j < segmentNumber; j++) {
                if (i == j) {
                    continue;
                }
                if (segments[i].size() == 0 || segments[j].size() == 0) {
                    continue;
                }
                if (segments[i].get(0).equals(segments[j].get(0))) {
                    ArrayList<String> newList = new ArrayList<String>();
                    for (int m = segments[i].size() - 1; m >= 0; m--) {
                        newList.add(segments[i].get(m));
                    }
                    for (int n = 1; n < segments[j].size(); n++) {
                        newList.add(segments[j].get(n));
                    }
                    if (j > i) {
                        segments[j] = newList;
                        segments[i] = new ArrayList<String>();
                    } else {
                        segments[i] = newList;
                        segments[j] = new ArrayList<String>();
                    }
                } else if (segments[i].get(0).equals(segments[j].get(segments[j].size() - 1))) {
                    ArrayList<String> newList = new ArrayList<String>();
                    for (int n = 0; n < segments[j].size(); n++) {
                        newList.add(segments[j].get(n));
                    }
                    for (int m = 1; m < segments[i].size(); m++) {
                        newList.add(segments[i].get(m));
                    }
                    if (j > i) {
                        segments[j] = newList;
                        segments[i] = new ArrayList<String>();
                    } else {
                        segments[i] = newList;
                        segments[j] = new ArrayList<String>();
                    }
                } else if (segments[i].get(segments[i].size() - 1).equals(segments[j].get(0))) {
                    ArrayList<String> newList = new ArrayList<String>();
                    for (int m = 0; m < segments[i].size(); m++) {
                        newList.add(segments[i].get(m));
                    }
                    for (int n = 1; n < segments[j].size(); n++) {
                        newList.add(segments[j].get(n));
                    }
                    if (j > i) {
                        segments[j] = newList;
                        segments[i] = new ArrayList<String>();
                    } else {
                        segments[i] = newList;
                        segments[j] = new ArrayList<String>();
                    }
                } else if (segments[i].get(segments[i].size() - 1).equals(segments[j].size() - 1)) {
                    ArrayList<String> newList = new ArrayList<String>();
                    for (int n = 0; n < segments[j].size(); n++) {
                        newList.add(segments[j].get(n));
                    }
                    for (int m = segments[i].size() - 2; m >= 0; m--) {
                        newList.add(segments[i].get(m));
                    }
                    if (j > i) {
                        segments[j] = newList;
                        segments[i] = new ArrayList<String>();
                    } else {
                        segments[i] = newList;
                        segments[j] = new ArrayList<String>();
                    }
                }
            }
        }
        for (int i = 0; i < segmentNumber; i++) {
            if (segments[i].size() == 0) {
                continue;
            } else {
                for (int j = 0; j < segments[i].size(); j++) {
                    returnString += segments[i].get(j);
                    if (j != segments[i].size() - 1) {
                        returnString += "-";
                    }
                }
            }
            returnString += " ";
        }
        return returnString;
    }

    public static void sortSpot() {
        ArrayList<Description> originalDescriptions = Resources.getDescriptions();
        ArrayList<Description> tempDescriptions = new ArrayList<Description>();
        int count = 0;
        System.out.println("推荐等级        景点名称                景点简介                       游客喜欢程度              是否有休息场所               是否有厕所");
        for (int i = 10; i >= 0; i--) {
            int j = 1;
            int m = count;
            for (Description description: originalDescriptions) {
                if (description.getLoveDegree() == i) {
                    count++;
                    if ((j + m) < 10) {
                        if (description.getSpotName().length() == 2) {
                            System.out.println(j + m + ".  " + "            " + description.getSpotName() + "   " + "           " + description.getSpotDescription() + "         " + description.getLoveDegree() + "                   " + description.hasRestPlace() + "                          " + description.hasToilet());
                        } else if (description.getSpotName().length() == 3) {
                            System.out.println(j + m + ".  " + "            " + description.getSpotName() + " " + "           " + description.getSpotDescription() + "         " + description.getLoveDegree() + "                    " + description.hasRestPlace() + "                          " + description.hasToilet());
                        }
                    } else {
                        if (description.getSpotName().length() == 2) {
                            System.out.println(j + m + ". " + "            " + description.getSpotName() + "   " + "           " + description.getSpotDescription() + "              " + description.getLoveDegree() + "                     " + description.hasRestPlace() + "                     " + description.hasToilet());
                        } else if (description.getSpotName().length() == 3) {
                            System.out.println(j + m + ". " + "            " + description.getSpotName() + " " + "           " + description.getSpotDescription() + "         " + description.getLoveDegree() + "                    " + description.hasRestPlace() + "                          " + description.hasToilet());
                        }
                    }
                }
            }
            j++;
        }
    }

    public static void searchSpot() {
        System.out.println("请输入你想要查找的景区特征");
        String feature = scanner.nextLine();
        ArrayList<Description> returnSpots = new ArrayList<Description>();
        for (Description description: Resources.getDescriptions()) {
            if (description.getSpotName().contains(feature) || description.getSpotDescription().contains(feature)) {
                returnSpots.add(description);
            }
        }

        if (returnSpots.size() != 0) {
            System.out.println("符合要求的景点包括");
            System.out.println("景点名称        景点描述                       景点喜爱程度      是否有休息的场所        否有厕所");
        } else {
            System.out.println("没有你想要查找的景点");
        }
        for (Description description: returnSpots) {
            System.out.println(description.getSpotName() + "        " + description.getSpotDescription() + "        " + description.getLoveDegree() + "          " + description.hasRestPlace() + "                    " + description.hasToilet());
        }
    }

    private static void managerLogin() {
        String account = "";
        String password = "";
        System.out.println("请输入账号");
        account = scanner.nextLine();
        System.out.println("请输入密码");
        password = scanner.nextLine();
        if (account.equals("root") && password.equals("root")) {
            showManagerInterface();
        }
    }

    private static void showManagerInterface() {
        while (true) {
            System.out.println("1. 插入一个景点");
            System.out.println("2. 删除一个景点");
            System.out.println("3. 插入一条路");
            System.out.println("4. 删除一条路");
            System.out.println("5. 发布通知");
            System.out.println("6. 查看现在所有的景点(名称)");
            System.out.println("7. 查看现在所有的景点(简略)");
            System.out.println("8. 查看现在所有的景点(详细)");
            System.out.println("9. 查看两个景点之间的最短距离与最短路线");
            System.out.println("10.管理车辆进出");
            System.out.println("0.退出");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                System.out.println("输入想要插入景点的名称");
                input = scanner.nextLine();
                Resources.getPlaces().add(input);
            } else if (input.equals("2")) {
                System.out.println("输入想要删除的景点的名称");
                input = scanner.nextLine();
                boolean isDeleteSuccessfully = Resources.removePlace(input);
                if (isDeleteSuccessfully) {
                    System.out.println("删除成功");
                } else {
                    System.out.println("不存在要删除的景点");
                }
            } else if (input.equals("3")) {
                System.out.println("输入要插入的路的第一个端点");
                String firstPlace = scanner.nextLine();
                System.out.println("输入要插入的路的第二个端点");
                String secondPlace = scanner.nextLine();
                System.out.println("输入要插入的路的长度");
                int distance = Integer.parseInt(scanner.nextLine());
                boolean isAddSuccessfully = Resources.addRoad(firstPlace, secondPlace, distance);
                if (isAddSuccessfully) {
                    System.out.println("添加成功");
                } else {
                    System.out.println("至少有一个端点不存在，添加失败");
                }
            } else if (input.equals("4")) {
                System.out.println("输入要删除的路的第一个端点");
                String firstPlace = scanner.nextLine();
                System.out.println("输入要删除的路的第二个端点");
                String secondPlace = scanner.nextLine();
                boolean isDeleteSuccessfully = Resources.deleteRoad(firstPlace, secondPlace);
                if (isDeleteSuccessfully) {
                    System.out.println("删除成功");
                } else {
                    System.out.println("至少有一个端点不存在，删除失败");
                }
            } else if (input.equals("5")) {
                System.out.println("输入你想要发布的公告的内容");
                String content = scanner.nextLine();
                try {
                    PrintWriter printWriter = new PrintWriter(new FileWriter(new File("document/bulletin.txt"), true));
                    printWriter.println(simpleDateFormat.format(new Date()) + ":" + content);
                    printWriter.flush();
                    printWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (input.equals("6")) {
                Resources.showPlaces();
            } else if (input.equals("7")) {
                Resources.showSpots();
            } else if (input.equals("8")) {
                Resources.printResult();
            } else if (input.equals("9")) {
                System.out.println("请输入第一个景点的名称");
                String spot1 = scanner.nextLine();
                System.out.println("请输入第二个景点的名称");
                String spot2 = scanner.nextLine();
                Resources.getShortestDistance(spot1, spot2);
            } else if (input.equals("10")) {
                while (true) {
                    System.out.println("请输入A(车辆进停车场),D(车辆出停车场),N(查看停车场的车辆数目),CP(查看停车场内的汽车),CQ(查看队列中的汽车),E(退出停车场管理程序)");
                    String choice = scanner.nextLine();
                    if (choice.equalsIgnoreCase("A")) {
                        System.out.println("请输如刚到的汽车的车牌号码");
                        String carNumber = scanner.nextLine();
                        Car car = new Car(carNumber, new Date());
                        Parking.parkingLot.addCar(car);
                        printWriter.println("车牌号为" + car.getCarNumber() + "的车在" + simpleDateFormat.format(car.getArrive_date()) + "时间进入了停车场");
                        printWriter.flush();
                    } else if (choice.equalsIgnoreCase("D")) {
                        System.out.println("请输入要离开的汽车的车牌号码");
                        String carNumber = scanner.nextLine();
                        Parking.parkingLot.remove(carNumber);
                        printWriter.println("车牌号为" + carNumber + "的车在" + simpleDateFormat.format(new Date()) + "时间离开了停车场");
                        printWriter.flush();
                    } else if (choice.equalsIgnoreCase("N")) {
                        System.out.println("当前停车场内车辆数目为" + Parking.parkingLot.getSize());
                        printWriter.println("在" + simpleDateFormat.format(new Date()) + "时，停车场内的汽车数量为" + Parking.parkingLot.getSize());
                        printWriter.flush();
                    } else if (choice.equalsIgnoreCase("CP")) {
                        Parking.parkingLot.showCarsOrder();
                    } else if (choice.equalsIgnoreCase("CQ")) {
                        Parking.waitingQueue.showCarsOrder();
                    } else if (choice.equalsIgnoreCase("E")) {
                        return;
                    }
                }
            } else if (input.equals("0")) {
                return;
            }
        }
    }

    public static void closeResource() {
        printWriter.close();
    }
}
