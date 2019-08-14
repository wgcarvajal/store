/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author aranda
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    public List<T> findAllOderByName(String columName)
    {   CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root <T> root = cq.from(entityClass);
        cq.select(cq.from(entityClass));
        cq.orderBy(cb.asc(root.get(columName)));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public long findByParamettersCount(Map<String,Object> filters)
    {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root <T> root = cq.from(entityClass);
        cq.select(cb.count(cq.from(entityClass)));
        int contador = 0;
        List<Predicate> restricciones = new ArrayList();
        for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
            
            String filterProperty = it.next();
            Object filterValue = filters.get(filterProperty);
            if(contador == 0)
            {
               restricciones.add(cb.like(root.<String>get(filterProperty), filterValue+"%"));
            }
            else
            {
                restricciones.add(cb.and((cb.like(root.<String>get(filterProperty), filterValue+"%"))));
            }
            contador++;
        }
        if(!restricciones.isEmpty())
        {
            cq.where(restricciones.toArray(new Predicate[restricciones.size()]));
        }
        return (long)getEntityManager().createQuery(cq).getSingleResult();
    }
    
    public List<T> findByParametters(Map<String,Object> filters,int first, int limit)
    {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root <T> root = cq.from(entityClass);
        cq.select(root);
        int contador = 0;
        List<Predicate> restricciones = new ArrayList();
        for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
            
            String filterProperty = it.next();
            Object filterValue = filters.get(filterProperty);
            
            if(contador == 0)
            {
               restricciones.add(cb.like(root.<String>get(filterProperty), filterValue+"%"));
            }
            else
            {
                restricciones.add(cb.and((cb.like(root.<String>get(filterProperty), filterValue+"%"))));
            }
            contador++;
        }
        if(!restricciones.isEmpty())
        {
            cq.where(restricciones.toArray(new Predicate[restricciones.size()]));
        }
        return getEntityManager().createQuery(cq).setFirstResult(first).setMaxResults(limit).getResultList();
    }
    
}
