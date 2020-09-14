/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.cancelpurchase;

import com.store.controllers.util.Util;
import com.store.entities.Cancelpurchaseauditorie;
import com.store.entities.Owner;
import com.store.entities.Product;
import com.store.entities.User;
import com.store.facade.CancelpurchaseauditorieFacade;
import com.store.facade.OwnerFacade;
import com.store.facade.ProductFacade;
import com.store.facade.UserFacade;
import com.store.model.CancelPurchaseItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "cancelPurchaseController")
@ViewScoped
public class CancelPurchaseController implements Serializable {
    
    @EJB private CancelpurchaseauditorieFacade cancelpurchaseauditorieEJB;
    @EJB private ProductFacade productEJB;
    @EJB private OwnerFacade ownerEJB;
    private Date maxiVisibleDate;
    private Date finishDate;
    private Date initDate;
    private boolean endDate =false;
    private Date dateSelected;
    private List<Cancelpurchaseauditorie> cancelPurchases;
    private Cancelpurchaseauditorie cancelpurchaseauditorieSelected;
    private List<CancelPurchaseItem> cancelPurchaseItems;

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public boolean isEndDate() {
        return endDate;
    }

    public void setEndDate(boolean endDate) {
        this.endDate = endDate;
    }

    public Date getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(Date dateSelected) {
        this.dateSelected = dateSelected;
    }

    public List<Cancelpurchaseauditorie> getCancelPurchases() {
        return cancelPurchases;
    }

    public List<CancelPurchaseItem> getCancelPurchaseItems() {
        cancelPurchaseItems=null;
        if(cancelpurchaseauditorieSelected!=null)
        {
            String json = cancelpurchaseauditorieSelected.getPurchase();
            JSONObject jsonPurchase = new JSONObject(json);
            JSONArray jSONArray = jsonPurchase.getJSONArray("purchaseItems");
            cancelPurchaseItems = new ArrayList<>();
            for(int i =0;i<jSONArray.length();i++){
                JSONObject jsonItem = jSONArray.getJSONObject(i);
                long prodId = jsonItem.getLong("prodId");
                int ownId = jsonItem.getInt("ownId");
                Product product = productEJB.findByProdId(prodId);
                Owner owner = ownerEJB.find(ownId);
                CancelPurchaseItem cancelPurchaseItem = new CancelPurchaseItem();
                if(product!=null){
                    cancelPurchaseItem.setBarcode(product.getProdBarCode());
                    cancelPurchaseItem.setName(product.getProdName());
                }
                if(owner!=null)
                {
                    cancelPurchaseItem.setOwner(owner);
                }
                cancelPurchaseItem.setPrice(jsonItem.getInt("priceValue"));
                cancelPurchaseItem.setPricePurchase(jsonItem.getInt("pricePurValue"));
                cancelPurchaseItem.setQuantity(jsonItem.getInt("purItemQuantity"));
                cancelPurchaseItems.add(cancelPurchaseItem);
            }
        }
        return cancelPurchaseItems;
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
    
    
    public void onInitDataSelect(SelectEvent event)
    {
        finishDate = null;
        Date initialDate = (Date) event.getObject();
        endDate = false;
        dateSelected = null;
        cancelPurchases = null;
        if (initialDate != null) {
            initDate = initialDate;
            endDate = true;
        }
        Util.update("formCalendar");
        Util.update(":formPurchase");
    }
    
    
    public void onEndDateSelect(SelectEvent event) {
        Date eDate = (Date) event.getObject();
        if (eDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(eDate);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            finishDate = calendar.getTime();
            cancelPurchases = cancelpurchaseauditorieEJB.findCancelPurchaseInitialDayEndDay(initDate, finishDate);
        }
        Util.update(":formPurchase");
    }
    
    
    public String formatDate(Date date)
    {
        return Util.getFormatCurrentDate(date);
    }
    
    public String idPurchase(Cancelpurchaseauditorie cancelpurchaseauditorie)
    {
        String json = cancelpurchaseauditorie.getPurchase();
        JSONObject jsonPurchase = new JSONObject(json);
        long purId = jsonPurchase.getLong("purId");
        return purId+"";
    }
    
    
    public String datePurchase(Cancelpurchaseauditorie cancelpurchaseauditorie)
    {
        String json = cancelpurchaseauditorie.getPurchase();
        JSONObject jsonPurchase = new JSONObject(json);
        String dateString = jsonPurchase.getString("purDate");
       
        return dateString;
    }
    
    
    public String totalPurchase(Cancelpurchaseauditorie cancelpurchaseauditorie)
    {
        String json = cancelpurchaseauditorie.getPurchase();
        JSONObject jsonPurchase = new JSONObject(json);
        JSONArray jSONArray = jsonPurchase.getJSONArray("purchaseItems");
        int total = 0;
        for(int i =0;i<jSONArray.length();i++)
        {
            JSONObject jsonItem = jSONArray.getJSONObject(i);
            int priceValue = jsonItem.getInt("priceValue");
            int quantity = jsonItem.getInt("purItemQuantity");
            total = total + (priceValue*quantity);
        }
        
        return total+"";
    }
    
    public void selectCancelpurchaseauditorie(Cancelpurchaseauditorie cancelpurchaseauditorie)
    {
        cancelpurchaseauditorieSelected =cancelpurchaseauditorie;
        Util.update(":formProductTable");
        Util.openDialog("dialogProductTable");
    }
}
