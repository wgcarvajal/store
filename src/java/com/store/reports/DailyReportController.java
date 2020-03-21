/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.reports;

import com.store.controllers.util.Util;
import com.store.entities.Owner;
import com.store.entities.Purchase;
import com.store.entities.Purchasetotal;
import com.store.facade.PurchaseFacade;
import com.store.model.OwnerTotal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "dailyReportController")
@ViewScoped
public class DailyReportController implements Serializable {
    
    private List<Purchase> purchases;
    private List<Purchasetotal> purchasetotals;
    @EJB
    private PurchaseFacade purchaseEJB;
    private long total;
    private long gain;
    private double iva;
    private List<OwnerTotal> ownerTotals = new ArrayList<>();
    List<Object[]> products = new ArrayList<>();
    private Date initDate;
    private Date finishDate;
    private Date maxiVisibleDate;
    private boolean endDate =false;
    private Date dateSelected;

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
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

    public List<OwnerTotal> getOwnerTotals() {
        return ownerTotals;
    }

    public void setOwnerTotals(List<OwnerTotal> ownerTotals) {
        this.ownerTotals = ownerTotals;
    }

    public List<Object[]> getProducts() {
        return products;
    }

    public void setProducts(List<Object[]> products) {
        this.products = products;
    }

    public boolean isEndDate() {
        return endDate;
    }

    public void setEndDate(boolean endDate) {
        this.endDate = endDate;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(Date dateSelected) {
        this.dateSelected = dateSelected;
    }
    
    public void onInitDataSelect(SelectEvent event)
    {
        Date initialDate = (Date) event.getObject();
        total = 0;
        gain = 0;
        iva = 0;
        endDate = false;
        ownerTotals = new ArrayList<>();
        dateSelected = null;
        if (initialDate != null) {
            initDate = initialDate;
            endDate = true;
        }
        Util.update("formCalendar");
        Util.update(":formTotal");
        Util.update(":formOwner");
    }
    
    public void onEndDateSelect(SelectEvent event) {
        Date eDate = (Date) event.getObject();
        total = 0;
        gain = 0;
        iva = 0;
        ownerTotals = new ArrayList<>();
        if (eDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(eDate);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            finishDate = calendar.getTime();
            List<Object[]> list = purchaseEJB.findPurshaseTotalInitialDayEndDay(initDate, finishDate);
            if (list != null) {
                for (Object[] object : list) {
                    if (object[0] != null && object[1] != null && object[2] != null && object[2] != null) {
                        OwnerTotal ownerTotal = new OwnerTotal();
                        ownerTotal.setOwner((Owner) object[0]);
                        ownerTotal.setTotal((long) object[1]);
                        ownerTotal.setGain((long) object[2]);
                        ownerTotal.setIva((double) object[3]);
                        ownerTotals.add(ownerTotal);
                        total = total + (long) object[1];
                        gain = gain + (long) object[2];
                        iva = iva + (double) object[3];
                    }
                }
            }
        }
        Util.update(":formTotal");
        Util.update(":formOwner");
    }
    
    
    public void showProductList(Owner owner)
    {
        products = purchaseEJB.findProductQuantityInitialDayEndDay(initDate, finishDate,owner);
        if(products==null)
        {
            products = new ArrayList<>();
        }
        
        Util.update(":formProductTable");
        Util.openDialog("dialongProductTable");
    }
    
    public String returnIVA(long iva, long quantity)
    {
        long i = iva / quantity;
        return i+"%";
    }
    
    public String returnPrice(long price, long quantity)
    {
        long p = price / quantity;
        return priceFormat(p);
    }
    
    public String priceFormat(long value)
    {
        return "$"+Util.getFormatPrice(value);
    }
    
    public String priceFloatFormat(double value)
    {
        return "$"+Util.getFormatPrice(value);
    }
    
    public Date maxVisibleDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        maxiVisibleDate =  calendar.getTime();
        return maxiVisibleDate;
    }
    
    
    public Date minVisibleDate()
    {
        return null;
    }
    
}
