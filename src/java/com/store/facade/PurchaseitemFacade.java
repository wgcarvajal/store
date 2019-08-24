/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Purchaseitem;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author aranda
 */
@Stateless
public class PurchaseitemFacade extends AbstractFacade<Purchaseitem> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PurchaseitemFacade() {
        super(Purchaseitem.class);
    }
    
    public int deleteByPurIdAndProdId(Long purId,Long prodId)
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Purchaseitem> cq = cb.createCriteriaDelete(Purchaseitem.class);
        Root<Purchaseitem> root = cq.from(Purchaseitem.class);
        List<Predicate> restriccions = new ArrayList();
        Expression<String> x = root.<String>get("purId").<String>get("purId");
        Expression<String> y = root.<String>get("prodId").<String>get("prodId");
        restriccions.add(cb.equal(x, purId));
        restriccions.add(cb.and(cb.equal(y, prodId)));
        cq.where(restriccions.toArray(new Predicate[restriccions.size()]));
        return em.createQuery(cq).executeUpdate();
    }
    
    public int deleteByPurId(Long purId)
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Purchaseitem> cq = cb.createCriteriaDelete(Purchaseitem.class);
        Root<Purchaseitem> root = cq.from(Purchaseitem.class);
        List<Predicate> restriccions = new ArrayList();
        Expression<String> x = root.<String>get("purId").<String>get("purId");
        restriccions.add(cb.equal(x, purId));
        cq.where(restriccions.toArray(new Predicate[restriccions.size()]));
        return em.createQuery(cq).executeUpdate();
    }
    
}
