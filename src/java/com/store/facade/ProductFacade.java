/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Product;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Wilson Carvajal
 */
@Stateless
public class ProductFacade extends AbstractFacade<Product> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }
    
    public List<Product> findAllWithoutComposite()
    {
        Query query = getEntityManager().createNamedQuery("Product.findAllWithoutComposite");
        query.setHint("eclipselink.refresh", true);
        List<Product> resuList = query.getResultList();
        return resuList;
    }
    
    public Product findByProdId( Long prodId ){
        Query query = getEntityManager().createNamedQuery("Product.findByProdId");
        query.setHint("eclipselink.refresh", true);
        query.setParameter("prodId", prodId);
        List<Product> resuList = query.getResultList();
        return resuList.size() > 0 ? resuList.get(0):null;
    }
    
    public Product findByBarCode( String prodBarCode ){
        Query query = getEntityManager().createNamedQuery("Product.findByProdBarCode");
        query.setHint("eclipselink.refresh", true);
        query.setParameter("prodBarCode", prodBarCode);
        List<Product> resuList = query.getResultList();
        return resuList.size() > 0 ? resuList.get(0):null;
    }

    @Override
    public long findByParamettersCount(Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root <Product> root = cq.from(Product.class);
        cq.select(cb.count(root));
        int contador = 0;
        List<Predicate> restricciones = new ArrayList();
        for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
            
            String filterProperty = it.next();
            Object filterValue = filters.get(filterProperty);
            Expression<String> x = root.<String>get(filterProperty);
            String pattern = filterValue+"%";
            if(filterProperty.equals("prodName"))
            {
                pattern = "%"+filterValue+"%";
            }
            else if(filterProperty.equals("brandId"))
            {
                x = root.<String>get(filterProperty).<String>get("brandName");
            }
            else if(filterProperty.equals("catId"))
            {
                x = root.<String>get(filterProperty).<String>get("catName");
            }
            if(contador == 0)
            {
               restricciones.add(cb.like(x, pattern));
            }
            else
            {
                restricciones.add(cb.and((cb.like(x, pattern))));
            }
            contador++;
        }
        if(!restricciones.isEmpty())
        {
            cq.where(restricciones.toArray(new Predicate[restricciones.size()]));
        }
        return (long)getEntityManager().createQuery(cq).setHint("eclipselink.refresh", true).getSingleResult();
    }
    
    

    @Override
    public List<Product> findByParametters(Map<String, Object> filters, int first, int limit) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root <Product> root = cq.from(Product.class);
        cq.select(root);
        int contador = 0;
        List<Predicate> restricciones = new ArrayList();
        for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
            
            String filterProperty = it.next();
            Object filterValue = filters.get(filterProperty);
            Expression<String> x = root.<String>get(filterProperty);
            String pattern = filterValue+"%";
            if(filterProperty.equals("prodName"))
            {
                pattern = "%"+filterValue+"%";
            }
            else if(filterProperty.equals("brandId"))
            {
                x = root.<String>get(filterProperty).<String>get("brandName");
            }
            else if(filterProperty.equals("catId"))
            {
                x = root.<String>get(filterProperty).<String>get("catName");
            }
            else if(filterProperty.equals("prodStock"))
            {
                pattern = filterValue+"";
            }
            if(contador == 0)
            {
               restricciones.add(cb.like(x, pattern));
            }
            else
            {
                restricciones.add(cb.and((cb.like(x, pattern))));
            }
            contador++;
        }
        if(!restricciones.isEmpty())
        {
            cq.where(restricciones.toArray(new Predicate[restricciones.size()]));
        }
        return getEntityManager().createQuery(cq).setHint("eclipselink.refresh", true).setFirstResult(first).setMaxResults(limit).getResultList();
    }
    
    
    public boolean barcodeAlreadyExists(String prodBarCode)
    {
        Query query = getEntityManager().createNamedQuery("Product.findByProdBarCode");
        query.setHint("eclipselink.refresh", true);
        query.setParameter("prodBarCode", prodBarCode);
        List<Product> resuList = query.getResultList();
        return resuList.size() > 0;
    }
    
    public List<Object[]> searchProductName(String prodName)
    {
        Query query = getEntityManager().createNamedQuery("Product.searchProductName");
        query.setHint("eclipselink.refresh", true);
        query.setParameter("prodName", "%"+prodName+"%");
        List<Object[]> resuList = query.getResultList();
        return resuList;
    }
    
    public List<Object[]> searchProductId(long prodId)
    {
        Query query = getEntityManager().createNamedQuery("Product.searchProductId");
        query.setHint("eclipselink.refresh", true);
        query.setParameter("prodId", prodId);
        List<Object[]> resuList = query.getResultList();
        return resuList;
    }
    
}
