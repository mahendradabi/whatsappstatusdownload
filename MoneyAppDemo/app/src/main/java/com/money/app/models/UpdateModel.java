package com.money.app.models;

public class UpdateModel {
    private double oldPrice;
    private double newPrice;

    private long timeValue;
    private double upValue;
    private double downValue;
    private String instruement;

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public long getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(long timeValue) {
        this.timeValue = timeValue;
    }

    public double getUpValue() {
        return upValue;
    }

    public void setUpValue(double upValue) {
        this.upValue = upValue;
    }

    public double getDownValue() {
        return downValue;
    }

    public void setDownValue(double downValue) {
        this.downValue = downValue;
    }

    public String getInstruement() {
        return instruement;
    }

    public void setInstruement(String instruement) {
        this.instruement = instruement;
    }
}
