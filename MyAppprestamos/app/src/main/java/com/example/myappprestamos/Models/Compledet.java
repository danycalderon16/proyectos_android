package com.example.myappprestamos.Models;

public class Compledet {
    private String name;
    private int initialMoney;
    private int finalMoney;
    private int dividends;
    private String startsDate;
    private String finishDate;

    public Compledet(String name, int initialMoney, int finalMoney, int dividends, String startsDate, String finishDate) {
        this.name = name;
        this.initialMoney = initialMoney;
        this.finalMoney = finalMoney;
        this.dividends = dividends;
        this.startsDate = startsDate;
        this.finishDate = finishDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInitialMoney() {
        return initialMoney;
    }

    public void setInitialMoney(int initialMoney) {
        this.initialMoney = initialMoney;
    }

    public int getFinalMoney() {
        return finalMoney;
    }

    public void setFinalMoney(int finalMoney) {
        this.finalMoney = finalMoney;
    }

    public int getDividends() {
        return dividends;
    }

    public void setDividends(int dividends) {
        this.dividends = dividends;
    }

    public String getStartsDate() {
        return startsDate;
    }

    public void setStartsDate(String startsDate) {
        this.startsDate = startsDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
}
