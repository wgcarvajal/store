/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Client;
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
 * @author aranda
 */
@Stateless
public class ClientFacade extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientFacade() {
        super(Client.class);
    }
    
    public Client findByProdId( Long cliId ){
        Query query = getEntityManager().createNamedQuery("Client.findByCliId");
        query.setParameter("cliId", cliId);
        List<Client> resuList = query.getResultList();
        return resuList.size() > 0 ? resuList.get(0):null;
    }

    @Override
    public long findByParamettersCount(Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root <Client> root = cq.from(Client.class);
        cq.select(cb.count(root));
        int contador = 0;
        List<Predicate> restricciones = new ArrayList();
        for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
            String filterProperty = it.next();
            Object filterValue = filters.get(filterProperty);
            Expression<String> x = root.<String>get(filterProperty);
            String pattern = filterValue+"%";
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
        return (long)getEntityManager().createQuery(cq).getSingleResult();
    }
    
    

    @Override
    public List<Client> findByParametters(Map<String, Object> filters, int first, int limit) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root <Client> root = cq.from(Client.class);
        cq.select(root);
        int contador = 0;
        List<Predicate> restricciones = new ArrayList();
        for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
            
            String filterProperty = it.next();
            Object filterValue = filters.get(filterProperty);
            Expression<String> x = root.<String>get(filterProperty);
            String pattern = filterValue+"%";
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
        return getEntityManager().createQuery(cq).setFirstResult(first).setMaxResults(limit).getResultList();
    }
    
    public boolean identificationAlreadyExists(String cliIdentity)
    {
        Query query = getEntityManager().createNamedQuery("Client.findByCliIdentity");
        query.setParameter("cliIdentity", cliIdentity);
        List<Client> resuList = query.getResultList();
        return resuList.size() > 0;
    }
    
}
