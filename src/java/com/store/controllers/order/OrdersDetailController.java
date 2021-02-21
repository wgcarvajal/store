/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.order;

import com.store.controllers.products.ProductController;
import com.store.controllers.util.Util;
import com.store.entities.Orders;
import com.store.entities.Provider;
import com.store.facade.OrdersFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "ordersDetailController")
@ViewScoped
public class OrdersDetailController implements Serializable{
    
    @EJB
    private OrdersFacade ordersEJB;
    private Date date;
    private int total;
    private int pendingTotal;
    private List<Orders> orders;
    private Orders orderSelected;
    
    @PostConstruct
    public void init()
    {
        
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String year = paramMap.get("y");
        String mont = paramMap.get("m");
        String day = paramMap.get("d");
        
        if (year != null && !year.equals("") && mont != null && !mont.equals("") &&
                day != null && !day.equals("")) {
            try {
                int y = Integer.parseInt(year);
                int m = Integer.parseInt(mont);
                int d = Integer.parseInt(day);
                Calendar calendar = Calendar.getInstance();
                calendar.set(y,m-1, d,0,0,2);
                date = calendar.getTime();
                
                orders = ordersEJB.findOrdersByYearsMonthAndDay(y, m, d);
                total = 0;
                pendingTotal = 0;
                for(Orders order:orders)
                {
                    total = total + order.getOrTotal();
                    if(order.getOrState()==1){
                        pendingTotal = pendingTotal + order.getOrTotal();
                    }
                }
                
            } catch (NumberFormatException e) {
                goIndex();
            }
        } else {
            goIndex();
        }
    }
    
    private void goIndex() {
        try {
            String uri = Util.projectPath+"/admin/index.xhtml?i=0";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Date getDate() {
        return date;
    }
    
    public String formatDate(Date date)
    {
        return Util.getFormatCurrentDateTranslate(date);
    }

    public int getTotal() {
        return total;
    }

    public int getPendingTotal() {
        return pendingTotal;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public Orders getOrderSelected() {
        return orderSelected;
    }

    public void setOrderSelected(Orders orderSelected) {
        this.orderSelected = orderSelected;
    }
    
    public String formatShortDateWithParentesis(Date date)
    {
        if(date!=null)
            return "("+Util.getFormatDate(date)+")";
        return "";
    }
    public String formatShortDate(Date date)
    {
        if(date!=null)
            return Util.getFormatDate(date);
        return "";
    }
    
    public String getStatus(int status)
    {
        if(status == 1)
        {
            return ResourceBundle.getBundle("/Bundle").
                                getString("Pending");
        }
        else if(status == 2)
        {
            return ResourceBundle.getBundle("/Bundle").
                                getString("Received"); 
        }
            return ResourceBundle.getBundle("/Bundle").getString("Returned");
    }
    
    public boolean isAvailableScheduleOrder()
    {
        Date current = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        current = calendar.getTime();
        boolean r = current.compareTo(date)< 0;
        System.out.println("return "+ r);
        return (r);
        
    }
    
}
