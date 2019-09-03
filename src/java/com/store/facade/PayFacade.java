/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Pay;
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
public class PayFacade extends AbstractFacade<Pay> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PayFacade() {
        super(Pay.class);
    }
    
    public List<Pay> findByCliId(long cliId)
    {
        Query query = getEntityManager().createNamedQuery("Pay.findByCliId");
        query.setParameter("cliId", cliId);
        List<Pay> resuList = query.getResultList();
        return resuList.size() > 0 ? resuList:null;
    }
}
