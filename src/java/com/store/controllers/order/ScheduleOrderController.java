/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.order;

import com.store.controllers.util.Util;
import com.store.entities.Provider;
import com.store.facade.ProviderFacade;
import com.store.facade.ProvidesFacade;
import com.store.model.ProvidesModel;
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
@ManagedBean(name = "scheduleOrderController")
@ViewScoped
public class ScheduleOrderController implements Serializable{
   
    @EJB
    private ProviderFacade providerEJB;
    @EJB
    private ProvidesFacade providesEJB;
    private Date date;
    private Date minDate;
    private Provider provider;
    private List<Provider> providers;
    private List<ProvidesModel> provides;
    private List<ProvidesModel>providesSelected;
    private String quantity;
    private boolean editDate = false;
    
    
    public void openScheduleOrder(Date date)
    {
        provider = null;
        providers = providerEJB.findAllProviders();
        minDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(minDate);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        minDate = c.getTime();
        this.date = date;
        Util.update(":scheduleOrderForm:scheduleOrderPanel");
        Util.openDialog("scheduleOrderDialog");
    }
    
    public String formatDay(Date date)
    {
        if(date!=null)
        {
            return Util.getFormatDateReturnDay(date);
        }
        return "";
    }
    
    public String formatDate(Date date)
    {
        if(date!=null)
        {
            return Util.getFormatCurrentDateTranslate(date);
        }
        return null;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isEditDate() {
        return editDate;
    }
    
    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }
    
    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public List<ProvidesModel> getProvides() {
        return provides;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public List<ProvidesModel> getProvidesSelected() {
        return providesSelected;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    
    public void onDateSelect(SelectEvent event)
    {
        Date newDate = (Date)event.getObject();
        editDate = false;
        if(newDate.before(minDate))
        {
            date = minDate;
        }
        Util.update("scheduleOrderForm:scheduleOrderPanel");
    }
    
    public void providerChange(){ 
        providesSelected = new ArrayList<>();
        if(provider!=null)
        {
            provides = providesEJB.findProductsByProvId(provider.getProvId());
        }
        else{
            provides = null;
        }
        Util.update("scheduleOrderForm:providesPanel");
    }
    
    public void addItem(ProvidesModel providesModel)
    {
        providesSelected.add(providesModel);
        provides.remove(providesModel);
        Util.update("scheduleOrderForm:providesPanel");
    }
    
    public void removeItem(ProvidesModel providesModel)
    {
        provides.add(providesModel);
        providesSelected.remove(providesModel);
        Util.update("scheduleOrderForm:providesPanel");
    }
    
    
    public String quantity(int stock, int base)
    {
        if(stock<=0)
        {
            quantity = base+"";
        }
        else
        {
            quantity = (base - stock)+"";
        }
        return quantity;
    }
    
    public void editDateChange()
    {
        editDate = true;
        Util.update("scheduleOrderForm:scheduleOrderPanel");
    }
}
