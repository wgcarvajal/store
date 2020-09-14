/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Provider;
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
public class ProviderFacade extends AbstractFacade<Provider> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProviderFacade() {
        super(Provider.class);
    }
    
    public List<Provider> findAllProviders()
    {
        Query query = getEntityManager().createNamedQuery("Provider.findAll");
        query.setHint("eclipselink.refresh", true);
        return query.getResultList();
    }
    
    
    public Provider findProvider(long provId)
    {
        Query query = getEntityManager().createNamedQuery("Provider.findByProvId");
        query.setHint("eclipselink.refresh", true);
        query.setParameter("provId", provId);
        return (Provider)query.getSingleResult();
    }
    
    
    
}
