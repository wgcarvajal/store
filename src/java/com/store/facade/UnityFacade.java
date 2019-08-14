/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Unity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aranda
 */
@Stateless
public class UnityFacade extends AbstractFacade<Unity> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UnityFacade() {
        super(Unity.class);
    }
    
}
