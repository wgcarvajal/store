/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Category;
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
public class CategoryFacade extends AbstractFacade<Category> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
    }
    
    public List<Category> findByCatId( Long catId ){
        Query query = getEntityManager().createNamedQuery("Category.findByCatId");
        query.setParameter("catId", catId);
        List<Category> resuList = query.getResultList();
        return resuList;
    }
    
    public List<Category> findByCatName( String catName ){
        Query query = getEntityManager().createNamedQuery("Category.findByCatName");
        query.setParameter("catName", catName);
        List<Category> resuList = query.getResultList();
        return resuList;
    }
    
    public boolean existCategory(String catName)
    {
        Query query = getEntityManager().createNamedQuery("Category.findByCatName");
        query.setParameter("catName",  catName);
        List<Category> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
     public List<Category> findByColumn(String search)
    {
        Query query = getEntityManager().createNamedQuery("Category.findByColumn");
        query.setParameter("catName", "%" + search + "%");
        List<Category> resultList = query.getResultList();
        return resultList;
    }
     
    public boolean isCategoryUsed(Long id)
    {
        Query query = getEntityManager().createNamedQuery("Product.findByCatId");
        query.setParameter("catId",  id);
        List<Category> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
}
