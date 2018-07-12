package com.EugeneStudio.core;

public class Parking {
    private Parking() {
    }

    public static WaitingQueue waitingQueue = new WaitingQueue();//入停车场的队列
    public static ParkingStack parkingLot = new ParkingStack(10);//停车场  为了更好的模拟先暂时假设其数量为10
    public static TempStack temporaryArea = new TempStack();//车辆临时存放地
}
