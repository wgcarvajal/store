/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Provides;
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
public class ProvidesFacade extends AbstractFacade<Provides> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProvidesFacade() {
        super(Provides.class);
    }
    
    public Provides findCurrentByProdId(long prodId)
    {
        Query query = getEntityManager().createNamedQuery("Provides.findCurrentByProdId");
        query.setParameter("prodId", prodId);
        List<Provides> resuList = query.getResultList();
        return resuList.size() > 0 ? resuList.get(0):null;
    }
}
