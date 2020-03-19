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
    private long iva;
    private List<OwnerTotal> ownerTotals = new ArrayList<>();

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

    public long getIva() {
        return iva;
    }

    public void setIva(long iva) {
        this.iva = iva;
    }

    public List<OwnerTotal> getOwnerTotals() {
        return ownerTotals;
    }

    public void setOwnerTotals(List<OwnerTotal> ownerTotals) {
        this.ownerTotals = ownerTotals;
    }
    
    public void onDateSelect(SelectEvent event) {
        Date initialDate = (Date) event.getObject();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND, 59);
        Date endDate = calendar.getTime();
        List<Object[]> list =purchaseEJB.findPurshaseTotalInitialDayEndDay(initialDate, endDate);
        total = 0;
        gain = 0;
        iva = 0;
        ownerTotals = new ArrayList<>();
        if (list != null) {
            for (Object[] object : list) {
                if (object[0] != null && object[1] != null && object[2] != null && object[2] != null) {
                    OwnerTotal ownerTotal = new OwnerTotal();
                    ownerTotal.setOwner((Owner) object[0]);
                    ownerTotal.setTotal((long) object[1]);
                    ownerTotal.setGain((long) object[2]);
                    ownerTotal.setIva((long) object[3]);
                    ownerTotals.add(ownerTotal);
                    total = total + (long) object[1];
                    gain = gain + (long) object[2];
                    iva = iva + (long) object[3];
                }
            }
        }
        Util.update(":formTotal");
        Util.update(":formOwner");
    }
    
}
