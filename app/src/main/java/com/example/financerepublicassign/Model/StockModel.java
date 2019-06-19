package com.example.financerepublicassign.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class StockModel {
    @PrimaryKey
    @NotNull
    private String name;
    private String Industry;
    private String Symbol;
    private String Series;
    private String ISIN;
    private double price;
    private int unit;
    private long userid;
    private String date;
    @Ignore
    private String currentdate;

    @Ignore
    private double currentPrice=0;

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public StockModel(){}
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public StockModel(String[] row) {
        name=row[0];
        Industry=row[1];
        Symbol=row[2];
        Series=row[3];
        ISIN=row[4];
        unit=0;

    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String industry) {
        Industry = industry;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getSeries() {
        return Series;
    }

    public void setSeries(String series) {
        Series = series;
    }

    public String getISIN() {
        return ISIN;
    }

    public void setISIN(String ISIN) {
        this.ISIN = ISIN;
    }


}
