/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.reports;

import com.store.controllers.util.GeneratePdf;
import com.store.controllers.util.PrintPdf;
import com.store.controllers.util.Util;
import com.store.entities.Cash;
import com.store.entities.Owner;
import com.store.entities.Purchase;
import com.store.entities.Purchaseitem;
import com.store.entities.Purchasetotal;
import com.store.facade.CashFacade;
import com.store.facade.PurchaseFacade;
import com.store.facade.PurchaseitemFacade;
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
    @EJB
    private PurchaseitemFacade purchaseitemEJB;
    @EJB
    private CashFacade cashEJB;
    private long total;
    private long gain;
    private double iva;
    private List<OwnerTotal> ownerTotals = new ArrayList<>();
    List<Object[]> products = new ArrayList<>();
    private List<Cash>cashList;
    private Date initDate;
    private Date finishDate;
    private Date maxiVisibleDate;
    private boolean endDate =false;
    private Date dateSelected;
    private String billSelected;
    private Purchase printPurchase;
    private Cash cashSelected;
 
    public List<Purchase> getPurchases() {
        if(initDate!=null && finishDate!=null)
        {
            purchases = purchaseEJB.findPurchaseBetweenInitDateAndEndDate(initDate, finishDate);
        }
        
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

    public String getBillSelected() {
        return billSelected;
    }

    public void setBillSelected(String billSelected) {
        this.billSelected = billSelected;
    }

    public List<Cash> getCashList() {
        return cashList;
    }

    public void setCashList(List<Cash> cashList) {
        this.cashList = cashList;
    }

    public Cash getCashSelected() {
        return cashSelected;
    }

    public void setCashSelected(Cash cashSelected) {
        this.cashSelected = cashSelected;
    }
    
    public void onInitDataSelect(SelectEvent event)
    {
        finishDate = null;
        Date initialDate = (Date) event.getObject();
        total = 0;
        gain = 0;
        iva = 0;
        endDate = false;
        ownerTotals = new ArrayList<>();
        dateSelected = null;
        purchases = null;
        billSelected = null;
        if (initialDate != null) {
            initDate = initialDate;
            endDate = true;
        }
        Util.update("formCalendar");
        Util.update(":formTotal");
        Util.update(":formOwner");
        Util.update(":formPurchase");
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
        Util.update(":formPurchase");
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
    
    public String formatDate(Date date)
    {
        return Util.getFormatCurrentDate(date);
    }
    
    public void selectBillPurchase(Purchase p)
    {
        billSelected = p.getPurId()+"";
        Util.update(":formBill");
        Util.openDialog("dialogBill");
    }
    
    public void printBill(Purchase p)
    {
        printPurchase = p;
        cashList = cashEJB.findAll();
        cashSelected = null;
        Util.update(":formPrint");
        Util.openDialog("dialogPrint");
    }
    
    public void print()
    {
        List<Purchaseitem>purchaseitems = purchaseitemEJB.findByPurId(printPurchase.getPurId());
        GeneratePdf generatePdf = new GeneratePdf(cashSelected.getCashPaperSize());
        generatePdf.generatePDF(printPurchase, purchaseitems);
        PrintPdf printPdf = new PrintPdf(cashSelected.getCashPaperSize());
        printPdf.imprimirTicket(generatePdf.getFicheroPdf(), cashSelected.getCashPrintName());
        printPurchase = null;
        cashList =null;
        cashSelected = null;
        Util.update(":formPrint");
        Util.closeDialog("dialogPrint");
    }
    
    public boolean billSelectedPurchase()
    {
        if(billSelected!=null && !billSelected.isEmpty())
        {
            return true;
        }
        return false;
    }
    
    public String urlServer()
    {
        return Util.urlServer + Util.projectPath;
    }
    
}
