/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Brand;
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
public class BrandFacade extends AbstractFacade<Brand> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BrandFacade() {
        super(Brand.class);
    }
    
    public List<Brand> findByBrandId( Long brandId ){
        Query query = getEntityManager().createNamedQuery("Brand.findByBrandId");
        query.setParameter("brandId", brandId);
        List<Brand> resuList = query.getResultList();
        return resuList;
    }
    
    public List<Brand> findByBrandName( String brandName ){
        Query query = getEntityManager().createNamedQuery("Brand.findByBrandName");
        query.setParameter("brandName", brandName);
        List<Brand> resuList = query.getResultList();
        return resuList;
    }
    
    public boolean existBrand(String brandName)
    {
        Query query = getEntityManager().createNamedQuery("Brand.findByBrandName");
        query.setParameter("brandName",  brandName);
        List<Brand> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
     public List<Brand> findByColumn(String search)
    {
        Query query = getEntityManager().createNamedQuery("Brand.findByColumn");
        query.setParameter("brandName", "%" + search + "%");
        List<Brand> resultList = query.getResultList();
        return resultList;
    }
     
    public boolean isBrandUsed(Long id)
    {
        Query query = getEntityManager().createNamedQuery("Product.findByBrandId");
        query.setParameter("brandId",  id);
        List<Brand> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
}
