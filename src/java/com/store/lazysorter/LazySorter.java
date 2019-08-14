/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.lazysorter;

import com.store.entities.Product;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @author aranda
 */
public class LazySorter implements Comparator<Product> {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    public int compare(Product prod1, Product prod2) {
        try {
            Object value1 = Product.class.getField(this.sortField).get(prod1);
            Object value2 = Product.class.getField(this.sortField).get(prod2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
