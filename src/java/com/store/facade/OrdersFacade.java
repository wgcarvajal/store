/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Orders;
import com.store.model.OrderEvent;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Wilson Carvajal
 */
@Stateless
public class OrdersFacade extends AbstractFacade<Orders> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdersFacade() {
        super(Orders.class);
    }
    
    public List<OrderEvent> findStartAndFinalDate(Date startDate,Date endDate)
    {
        Query query = getEntityManager().createNamedQuery("Orders.findStartAndFinalDate");
        query.setHint("eclipselink.refresh", true);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        List resuList = query.getResultList();
        return resuList;
    }
    
    public List<Orders> findOrdersByYearsMonthAndDay(int year,int month, int day)
    {
        Query query = getEntityManager().createNamedQuery("Orders.findOrdersByYearsMonthAndDay");
        query.setHint("eclipselink.refresh", true);
        query.setParameter("year", year);
        query.setParameter("month", month);
        query.setParameter("day", day);
        List resuList = query.getResultList();
        return resuList;
    }
    
}
