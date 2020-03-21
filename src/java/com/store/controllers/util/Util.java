/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
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
    public static String BILLDIR= "/Users/aranda/filesStore/bill/";
    
    
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
    
    public static String getFormatPrice(long price)
    {
        NumberFormat format = NumberFormat.getInstance();
        Currency currency = Currency.getInstance("COP");
        format.setCurrency(currency);
        return format.format(price);
    }
    
    public static String getFormatPrice(double price)
    {
        NumberFormat format = NumberFormat.getInstance();
        Currency currency = Currency.getInstance("COP");
        format.setCurrency(currency);
        return format.format(Math.round(price));
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
    
    public static void clearFilters(String widgetVar)
    {
        PrimeFaces.current().executeScript("PF('"+widgetVar+"').clearFilters()");
    }
    
    public static void closeDialog(String widgetVar)
    {
        PrimeFaces.current().executeScript("PF('"+widgetVar+"').hide()");
    }
    
    public static boolean isInteger(String value)
    {
        try
        {
            long l = Integer.parseInt(value);
            return true;
        }catch(NumberFormatException e)
        {
            return false;
        }
    }
    
    public static String getFormatDate(Date date)
    {        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yy");
        String fecha = formato.format(date);
        return fecha;
    }
    
    public static String getFormatHour(Date date)
    {        
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        String hora = formato.format(date);
        return hora;
    }
    
    public static String getFormatCurrentDate(Date date)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        String hora = formato.format(date);
        return hora;
    }
    
    public static String evaluateIva(int iva)
    {
        if(iva == 5)
        {
            return "A";
        }
        else if(iva == 19)
        {
            return "B";
        }
        return "+";
    }
    
    public static String upperCase(String string)
    {
        return string.toUpperCase();
    }
    
}
