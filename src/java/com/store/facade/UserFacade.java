/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.facade;

import com.store.entities.Groupp_;
import com.store.entities.User;
import com.store.entities.User_;
import com.store.entities.Usergroup;
import com.store.entities.Usergroup_;
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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Wilson Carvajal
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public User findByUsId( Long usId ){
        Query query = getEntityManager().createNamedQuery("User.findByUsId");
        query.setParameter("usId", usId);
        List<User> resuList = query.getResultList();
        return resuList.size() > 0 ? resuList.get(0):null;
    }
    
    public User findByUsUserName( String usUserName ){
        Query query = getEntityManager().createNamedQuery("User.findByUsUserName");
        query.setParameter("usUserName", usUserName);
        List<User> resuList = query.getResultList();
        return resuList.size() > 0 ? resuList.get(0):null;
    }
    
    public long findByParamettersCount(Map<String, Object> filters,String group) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root <User> root = cq.from(User.class);
        cq.select(cb.count(root));
        Join<User, Usergroup> join = root.join(User_.usergroupList, JoinType.INNER);
        join.on(cb.equal(join.get(Usergroup_.grouId).get(Groupp_.grouId),group));
        List<Predicate> restricciones = new ArrayList();
        restricciones.add(join.getOn());
        for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
            String filterProperty = it.next();
            Object filterValue = filters.get(filterProperty);
            Expression<String> x = root.<String>get(filterProperty);
            String pattern = filterValue+"%";
            restricciones.add(cb.and((cb.like(x, pattern))));
            
        }
        if(!restricciones.isEmpty())
        {
            cq.where(restricciones.toArray(new Predicate[restricciones.size()]));
        }
        return (long)getEntityManager().createQuery(cq).getSingleResult();
    }
    
    

    public List<User> findByParametters(Map<String, Object> filters, int first, int limit,String group) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root <User> root = cq.from(User.class);
        cq.select(root);
        Join<User, Usergroup> join = root.join(User_.usergroupList, JoinType.INNER);
        join.on(cb.equal(join.get(Usergroup_.grouId).get(Groupp_.grouId),group));
        List<Predicate> restricciones = new ArrayList();
        restricciones.add(join.getOn());
        for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
            
            String filterProperty = it.next();
            Object filterValue = filters.get(filterProperty);
            Expression<String> x = root.<String>get(filterProperty);
            String pattern = filterValue+"%";
            restricciones.add(cb.and((cb.like(x, pattern))));
        }
        if(!restricciones.isEmpty())
        {
            
            cq.where(restricciones.toArray(new Predicate[restricciones.size()]));
        }
        return getEntityManager().createQuery(cq).setFirstResult(first).setMaxResults(limit).getResultList();
    }
    
    public boolean identificationAlreadyExists(String usIdentification)
    {
        Query query = getEntityManager().createNamedQuery("User.findByUsIdentification");
        query.setParameter("usIdentification", usIdentification);
        List<User> resuList = query.getResultList();
        return resuList.size() > 0;
    }
    
    public boolean userNameAlreadyExists(String usUserName)
    {
        Query query = getEntityManager().createNamedQuery("User.findByUsUserName");
        query.setParameter("usUserName", usUserName);
        List<User> resuList = query.getResultList();
        return resuList.size() > 0;
    }
    
    public boolean emailAlreadyExists(String usEmail)
    {
        Query query = getEntityManager().createNamedQuery("User.findByUsEmail");
        query.setParameter("usEmail", usEmail);
        List<User> resuList = query.getResultList();
        return resuList.size() > 0;
    }
    
}
