/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Cash;
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
public class CashFacade extends AbstractFacade<Cash> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CashFacade() {
        super(Cash.class);
    }
    
    public Cash findByCashIP(String cashIP)
    {
        Query query = getEntityManager().createNamedQuery("Cash.findByCashIP");
        query.setParameter("cashIP",  cashIP);
        List<Cash> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0?resultList.get(0):null;
    }
}
