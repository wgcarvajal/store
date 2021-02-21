/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.model;

import com.store.entities.Price;
import com.store.entities.Pricepurchase;
import com.store.entities.Provides;

/**
 *
 * @author Wilson Carvajal
 */
public class ProvidesModel {
    private Provides provides;
    private Price price;
    private Pricepurchase pricepurchase;

    public ProvidesModel(Provides provides, Price price, Pricepurchase pricepurchase) {
        this.provides = provides;
        this.price = price;
        this.pricepurchase = pricepurchase;
    }

    
    
    public Provides getProvides() {
        return provides;
    }

    public void setProvides(Provides provides) {
        this.provides = provides;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Pricepurchase getPricepurchase() {
        return pricepurchase;
    }

    public void setPricepurchase(Pricepurchase pricepurchase) {
        this.pricepurchase = pricepurchase;
    }
    
    
    
    
    
}
