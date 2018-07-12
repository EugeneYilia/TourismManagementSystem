package com.EugeneStudio.core;

import java.util.ArrayList;

public class ParkingStack {
    int size = -1;


    public ParkingStack(int size) {
        this.size = size;
    }

    private ArrayList<Car> cars = new ArrayList<Car>();

    public void addCar(Car car) {
        if (cars.size() <= size) {
            cars.add(car);
            System.out.println("车辆" + car.getCarNumber() + "成功进入停车场");
            if (cars.size() == this.size) {
                System.out.println("最后一辆车" + car.getCarNumber() + "驶入后，停车场已满");
            }
        } else {
            Parking.waitingQueue.addCar(car);
            System.out.println("停车场已满，车辆" + car.getCarNumber() + "进入等待队列");
        }
    }

    public void remove(String carNumber) {
        Car car = null;
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getCarNumber().equalsIgnoreCase(carNumber)) {
                car = cars.get(i);
            }
        }
        if (car != null) {
            //需要将car车从cars中按照栈的方式移出
            //cars.remove(car);

            boolean isFound = false;
            while (true) {
                Car removedcar = removeCar();
                if (car != removedcar) {//临时停车位存放该移出的车
                    Parking.temporaryArea.addCar(removedcar);
                } else {//临时停车区不存放该移出的车
                    isFound = true;
                    break;
                }
            }
            while (true) {
                if (Parking.temporaryArea.getSize() == 0) {
                    break;
                }
                addCar(Parking.temporaryArea.removeCar());
                if (Parking.temporaryArea.getSize() == 0) {
                    break;
                }
            }
            if (isFound) {
                System.out.println("汽车" + car.getCarNumber() + "成功从停车场开出");
                if (cars.size() == this.size - 1) {//刚从满的状态恢复到还剩一个空车位，此时从等待队列中将一个汽车移入(如果有汽车在等待队列中的话)
                    if (Parking.waitingQueue.getSize() != 0) {
                        cars.add(Parking.waitingQueue.removeCar());
                    }
                }
            }
        } else {
            System.out.println("在停车场中没有找到对应车牌号" + car.getCarNumber() + "的汽车");
        }
    }

    public int getSize() {
        return cars.size();
    }

    public void showCarsOrder() {
        System.out.println("停车场内的汽车顺序(由头到尾)");
        for (int i = (cars.size() - 1); i >= 0; i--) {
            System.out.print(cars.get(i).getCarNumber() + "    ");
        }
        System.out.println();
    }

    public void removeCar(Car car) {
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
            System.out.println("最外面的车辆" + car.getCarNumber() + "准备移出停车场");
            return car;
        } else {
            System.out.println("当前车道没有车辆");
            return null;
        }
    }
}
