/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Productimage;
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
public class ProductimageFacade extends AbstractFacade<Productimage> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductimageFacade() {
        super(Productimage.class);
    }
    
    public List<Productimage> findByProdId(Long prodId)
    {
        Query query = getEntityManager().createNamedQuery("Productimage.findByProdId");
        query.setParameter("prodId", prodId);
        List<Productimage> resultList = query.getResultList();
        return resultList;
    }
    
}
