package com.EugeneStudio.core;

import java.util.ArrayList;

public class TempStack {


    public TempStack() {
    }

    private ArrayList<Car> cars = new ArrayList<Car>();

    public void addCar(Car car) {
        cars.add(car);
        System.out.println("车辆" + car.getCarNumber() + "成功进入临时停车区");
    }

    public int getSize() {
        return cars.size();
    }

    public void removeCar(Car car) {//这个暂时用不上
        if (cars.size() != 0) {
            System.out.println("特定车辆准备移出");
            cars.remove(car);
            /*
            //自动化管理
            if (Parking.waitingQueue.getSize()!=0){
                Parking.waitingQueue.removeCar();//第一辆车处等待队列，进入停车场
            }*/
        } else {
            System.out.println("当前车道没有车辆");
        }
    }

    public Car removeCar() {
        if (cars.size() != 0) {
            Car car = cars.get(cars.size() - 1);
            cars.remove((cars.size() - 1));
            System.out.println("最外面的车辆" + car.getCarNumber() + "准备移出临时停车区");
            return car;
        } else {
            System.out.println("当前车道没有车辆");
            return null;
        }
    }
}
