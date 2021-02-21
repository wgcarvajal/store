/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.model;

/**
 *
 * @author Wilson Carvajal
 */
public class OrderEvent {
    
    int year;
    int month;
    int day;
    long total;
    int state;

    public OrderEvent(int year, int month, int day, long total, int state) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.total = total;
        this.state = state;
    }
    
    

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    
    
    
}
