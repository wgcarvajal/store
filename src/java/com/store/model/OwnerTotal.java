/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.model;

import com.store.entities.Owner;

/**
 *
 * @author aranda
 */
public class OwnerTotal {
    
    private Owner owner;
    private long total;
    private long gain;
    private double iva;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getGain() {
        return gain;
    }

    public void setGain(long gain) {
        this.gain = gain;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }
    
    
    
    
}
