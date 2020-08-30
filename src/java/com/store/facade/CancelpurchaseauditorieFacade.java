/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Cancelpurchaseauditorie;
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
public class CancelpurchaseauditorieFacade extends AbstractFacade<Cancelpurchaseauditorie> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CancelpurchaseauditorieFacade() {
        super(Cancelpurchaseauditorie.class);
    }
    
    public List<Cancelpurchaseauditorie> findCancelPurchaseInitialDayEndDay(Date initialDate, Date endDate)
    {
       Query query = getEntityManager().createNamedQuery("Cancelpurchaseauditorie.findCancelPurchaseInitialDayEndDay");
       query.setHint("eclipselink.refresh", true);
       query.setParameter("initialDate", initialDate);
       query.setParameter("endDate", endDate);
       List<Cancelpurchaseauditorie> list = query.getResultList();
       if(list!=null && list.size()>0)
       {
           return list;
       }
       return null;
    }
    
}
