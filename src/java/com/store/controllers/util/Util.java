/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.util;

import java.text.NumberFormat;
import java.util.Currency;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Wilson Carvajal
 */
public class Util {
    
    
    public static String projectPath = "/store";
    public static String PRODUCTIMAGEDIR= "/Users/aranda/filesStore/productImage/";
    
   
    
    public static String formatText(String value)
    {
        value = value.trim();
        value = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        return value;
        
    }
    
    public static String formatTextWithSapace(String value)
    {
        value = value.trim();
        value = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        String [] values = value.split(" ");
        if(values.length > 1)
        {
            value = values[0];
            for(int i=1;i<values.length;i++)
            {
               value = value +" "+values[i].substring(0, 1).toUpperCase() + values[i].substring(1).toLowerCase();; 
            }
        }
        return value;
    }
    
    public static String getFormatPrice(int price)
    {
        NumberFormat format = NumberFormat.getInstance();
        Currency currency = Currency.getInstance("COP");
        format.setCurrency(currency);
        return format.format(price);
    }
    
    public static void addErrorMessage(String message,String messageDetail)
    {
        FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                message, 
                                messageDetail));
    }
    
    public static void addInfoMessage(String message,String messageDetail)
    {
        FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                message, 
                                messageDetail));
    }
    
    public static void addWarnMessage(String message,String messageDetail)
    {
        FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                message, 
                                messageDetail));
    }
    
    public static void update(String update)
    {
        PrimeFaces.current().ajax().update(update);
    }
    
    public static void openDialog(String widgetVar)
    {
        PrimeFaces.current().executeScript("PF('"+widgetVar+"').show()");
    }
    
    public static void closeDialog(String widgetVar)
    {
        PrimeFaces.current().executeScript("PF('"+widgetVar+"').hide()");
    }
    
}
