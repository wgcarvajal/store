/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.order;

import com.store.controllers.util.Util;
import com.store.facade.OrdersFacade;
import com.store.model.OrderEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "ordersController")
@ViewScoped
public class OrdersController implements Serializable{
    
    @EJB
    private OrdersFacade ordersEJB;
    
    private ScheduleModel model;
    private ScheduleModel lazyEventModel;
    
    private DefaultScheduleEvent event = new DefaultScheduleEvent();
    
    @PostConstruct
    public void init() {
        model = new DefaultScheduleModel();
        lazyEventModel = new LazyScheduleModel() {
            public void showEvent(int currentYear,int currentMonth,int currentDay,long total,long totalPending)
            {
                event=new DefaultScheduleEvent();
                event.setTitle("T: $"+Util.getFormatPrice(total)+"\nP: $"+Util.getFormatPrice(totalPending));
                Calendar calendar = Calendar.getInstance();
                calendar.set(currentYear,currentMonth-1, currentDay,0,0,0);
                event.setStartDate(calendar.getTime());
                calendar.set(currentYear, currentMonth-1, currentDay,23,59,59);
                event.setEndDate(calendar.getTime());
                event.setAllDay(true);
                if(totalPending>0)
                    event.setStyleClass("pending-event");
                else
                    event.setStyleClass("close-event");
                addEvent(event);
            }
            @Override
            public void loadEvents(Date start, Date end) {
                clear();
                List<OrderEvent> list = ordersEJB.findStartAndFinalDate(start, end);
                int currentYear = -1;
                int currentMonth = -1;
                int currentDay = -1;
                long totalPending = 0;
                long total = 0;
                for(OrderEvent orderEvent: list)
                {
                    if(currentDay==-1 && currentMonth ==-1 && currentYear == -1)
                    {
                        currentDay =orderEvent.getDay();
                        currentMonth = orderEvent.getMonth();
                        currentYear = orderEvent.getYear();
                    }
                    
                    if(currentDay == orderEvent.getDay() && currentMonth == orderEvent.getMonth() && currentYear== orderEvent.getYear())
                    {
                        if(orderEvent.getState() == 1)
                        {
                            totalPending = totalPending + orderEvent.getTotal();
                        }
                        total = total + orderEvent.getTotal();
                    }
                    else
                    {
                        showEvent(currentYear, currentMonth, currentDay, total, totalPending);
                        currentDay =orderEvent.getDay();
                        currentMonth = orderEvent.getMonth();
                        currentYear = orderEvent.getYear();
                        total = orderEvent.getTotal();
                        totalPending = 0;
                        if(orderEvent.getState()==1)
                        {
                            totalPending = orderEvent.getTotal();
                        }
                        
                    }
                }
                
                if(currentDay!=-1 && currentMonth !=-1 && currentYear != -1)
                {
                    showEvent(currentYear, currentMonth, currentDay, total, totalPending);
                }
                System.out.println("Start: "+ start.toString() +" End: "+end.toString());
            }
        };
    }

    public DefaultScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(DefaultScheduleEvent event) {
        this.event = event;
    }

    public ScheduleModel getModel() {
        return model;
    }

    public void setModel(ScheduleModel model) {
        this.model = model;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }
    
    
     public void onEventSelect(SelectEvent selectEvent) {
       DefaultScheduleEvent e = (DefaultScheduleEvent)selectEvent.getObject();
       Calendar c = Calendar.getInstance(); 
        c.setTime(e.getStartDate());
        gotOrdersByDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DATE));
       System.out.println(selectEvent.getObject()+"");
    }
    
    
    public void onDateSelect(SelectEvent selectEvent) 
    {
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        currentDate = c.getTime();
        Date date = (Date)selectEvent.getObject();
        c = Calendar.getInstance(); 
        c.setTime(date); 
        c.add(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        date = c.getTime();
        if (currentDate.before(date) || currentDate.equals(date)) {
            gotOrdersByDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));
            System.out.println(c.getTime());
        }
    }
    
    public void gotOrdersByDate(int year,int month,int day)
    {
        try {
            String uri = Util.projectPath+"/admin/orders/orders.xhtml?i=0&y="+year+"&m="+month+"&d="+day;
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(OrdersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
