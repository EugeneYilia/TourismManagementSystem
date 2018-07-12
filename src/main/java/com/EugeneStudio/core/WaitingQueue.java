package com.EugeneStudio.core;

import java.util.LinkedList;

public class WaitingQueue {
    public LinkedList<Car> cars = new LinkedList<Car>();

    public WaitingQueue() {
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public Car removeCar() {
        Car car = null;
        if (cars.size() != 0) {
            car = cars.get(0);
            System.out.println("第一辆车" + cars.get(0).getCarNumber() + "准备移出");
            cars.remove(0);
            return car;
        } else {
            System.out.println("车道中没有汽车");
            return null;
        }
    }

    public int getSize() {
        return cars.size();
    }

    public void removeCar(Car car) {
        if (cars.size() != 0) {
            cars.remove(car);
            System.out.println("特定车辆准备移出");
        } else {
            System.out.println("车道中没有汽车");
        }
    }

    public void showCarsOrder() {
        System.out.println("等待队列中汽车的顺序为(由头到尾)");
        for (int i = 0; i < cars.size(); i++) {
            System.out.print(cars.get(i).getCarNumber() + "    ");
        }
        System.out.println();
    }
}
