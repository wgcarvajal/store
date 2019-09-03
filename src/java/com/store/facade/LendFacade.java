/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Lend;
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
public class LendFacade extends AbstractFacade<Lend> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LendFacade() {
        super(Lend.class);
    }
    
    public List<Lend> findByCliId(long cliId)
    {
        Query query = getEntityManager().createNamedQuery("Lend.findByCliId");
        query.setParameter("cliId", cliId);
        List<Lend> resuList = query.getResultList();
        return resuList.size() > 0 ? resuList:null;
    }
    
    public List<Lend> findByCliIdAndStatePending(long cliId)
    {
        Query query = getEntityManager().createNamedQuery("Lend.findByCliIdAndStatePending");
        query.setParameter("cliId", cliId);
        List<Lend> resuList = query.getResultList();
        return resuList.size() > 0 ? resuList:null;
    }
}
