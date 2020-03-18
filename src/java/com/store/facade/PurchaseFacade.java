/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Purchase;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aranda
 */
@Stateless
public class PurchaseFacade extends AbstractFacade<Purchase> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PurchaseFacade() {
        super(Purchase.class);
    }
    
    public List<Purchase> findByCliIdCredit(long cliId)
    {
        Query query = getEntityManager().createNamedQuery("Purchase.findByCliIdCredit");
        query.setParameter("cliId", cliId);
        List<Purchase> resuList = query.getResultList();
        return resuList.size() > 0 ? resuList:null;
    }
    
    public List<Purchase> findByCliIdAndStatePending(long cliId)
    {
        Query query = getEntityManager().createNamedQuery("Purchase.findByCliIdAndStatePending");
        query.setParameter("cliId", cliId);
        List<Purchase> resuList = query.getResultList();
        return resuList.size() > 0 ? resuList:null;
    }
    
    public List findYears(){
        Query query = getEntityManager().createNamedQuery("Purchase.findYears");
        return  query.getResultList();
    }
    
    public List findTotalEachMonthByYear(int year){
        Query query = getEntityManager().createNamedQuery("Purchase.findTotalEachMonthByYear");
        query.setParameter("year", year);
        return query.getResultList();
    }
    
}
