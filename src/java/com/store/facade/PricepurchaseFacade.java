/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Pricepurchase;
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
public class PricepurchaseFacade extends AbstractFacade<Pricepurchase> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PricepurchaseFacade() {
        super(Pricepurchase.class);
    }
    
    public Pricepurchase findCurrentByProdId(long prodId)
    {
        Query query = getEntityManager().createNamedQuery("Pricepurchase.findCurrentByProdId");
        query.setParameter("prodId", prodId);
        List<Pricepurchase> resuList = query.getResultList();
        return resuList.size() > 0 ? resuList.get(0):null;
    }
}
