/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Purchasetotal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wilson Carvajal
 */
@Stateless
public class PurchasetotalFacade extends AbstractFacade<Purchasetotal> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PurchasetotalFacade() {
        super(Purchasetotal.class);
    }
}

