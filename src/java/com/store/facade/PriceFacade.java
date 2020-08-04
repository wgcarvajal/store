/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Price;
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
public class PriceFacade extends AbstractFacade<Price> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PriceFacade() {
        super(Price.class);
    }
    
    public Price findCurrentByProdId(long prodId)
    {
        Query query = getEntityManager().createNamedQuery("Price.findCurrentByProdId");
        query.setHint("eclipselink.refresh", true);
        query.setParameter("prodId", prodId);
        List<Price> resuList = query.getResultList();
        return resuList.size() > 0 ? resuList.get(0):null;
    }
    
}
