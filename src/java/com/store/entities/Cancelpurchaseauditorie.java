/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Wilson Carvajal
 */
@Entity
@Table(name = "cancelpurchaseauditorie", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cancelpurchaseauditorie.findAll", query = "SELECT c FROM Cancelpurchaseauditorie c"),
    @NamedQuery(name = "Cancelpurchaseauditorie.findByCanpuraudId", query = "SELECT c FROM Cancelpurchaseauditorie c WHERE c.canpuraudId = :canpuraudId"),
    @NamedQuery(name = "Cancelpurchaseauditorie.findByCanpuraudDate", query = "SELECT c FROM Cancelpurchaseauditorie c WHERE c.canpuraudDate = :canpuraudDate"),
    @NamedQuery(name = "Cancelpurchaseauditorie.findCancelPurchaseInitialDayEndDay", query = "SELECT c FROM Cancelpurchaseauditorie c  WHERE c.canpuraudDate >= :initialDate and c.canpuraudDate <= :endDate")})
public class Cancelpurchaseauditorie implements Serializable {
     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "canpuraudId")
    private Long canpuraudId;
    @Basic(optional = false)
    @Column(name = "canpuraudDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date canpuraudDate;
    @Basic(optional = false)
    @Lob
    @Column(name = "purchase")
    private String purchase;
    @JoinColumn(name = "usuId", referencedColumnName = "usId")
    @ManyToOne(optional = false)
    private User usuId;

    public Cancelpurchaseauditorie() {
    }

    public Cancelpurchaseauditorie(Long canpuraudId) {
        this.canpuraudId = canpuraudId;
    }

    public Cancelpurchaseauditorie(Long canpuraudId, Date canpuraudDate, String purchase) {
        this.canpuraudId = canpuraudId;
        this.canpuraudDate = canpuraudDate;
        this.purchase = purchase;
    }

    public Long getCanpuraudId() {
        return canpuraudId;
    }

    public void setCanpuraudId(Long canpuraudId) {
        this.canpuraudId = canpuraudId;
    }

    public Date getCanpuraudDate() {
        return canpuraudDate;
    }

    public void setCanpuraudDate(Date canpuraudDate) {
        this.canpuraudDate = canpuraudDate;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public User getUsuId() {
        return usuId;
    }

    public void setUsuId(User usuId) {
        this.usuId = usuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (canpuraudId != null ? canpuraudId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cancelpurchaseauditorie)) {
            return false;
        }
        Cancelpurchaseauditorie other = (Cancelpurchaseauditorie) object;
        if ((this.canpuraudId == null && other.canpuraudId != null) || (this.canpuraudId != null && !this.canpuraudId.equals(other.canpuraudId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cancelpurchaseauditorie[ canpuraudId=" + canpuraudId + " ]";
    }
}
