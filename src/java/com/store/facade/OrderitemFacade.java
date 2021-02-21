/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Orderitem;
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
public class OrderitemFacade extends AbstractFacade<Orderitem> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderitemFacade() {
        super(Orderitem.class);
    }
    
    public List<Orderitem> example()
    {
        Query query = getEntityManager().createNamedQuery("Product.findAllWithoutComposite");
        query.setHint("eclipselink.refresh", true);
        List<Orderitem> resuList = query.getResultList();
        return resuList;
    }
    
}
