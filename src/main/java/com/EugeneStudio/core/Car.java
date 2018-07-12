package com.EugeneStudio.core;

import java.util.Date;

public class Car {
    private String carNumber;
    private Date arrive_date;

    public Car() {
    }

    public Car(String carNumber, Date arrive_date) {
        this.carNumber = carNumber;
        this.arrive_date = arrive_date;
    }

    public Date getArrive_date() {
        return arrive_date;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setArrive_date(Date arrive_date) {
        this.arrive_date = arrive_date;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
