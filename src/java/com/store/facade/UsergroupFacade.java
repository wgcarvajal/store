/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Usergroup;
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
public class UsergroupFacade extends AbstractFacade<Usergroup> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsergroupFacade() {
        super(Usergroup.class);
    }
    
    public Usergroup findByUsUserName(String usUserName)
    {
        Query query = getEntityManager().createNamedQuery("Usergroup.findByUsUserName");
        query.setParameter("usUserName", usUserName);
        List<Usergroup> resuList = query.getResultList();
        return resuList!=null && resuList.size()>0 ? resuList.get(0):null;
    }
    
}