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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wilson carvajal
 */
@Entity
@Table(name = "pricepurchase", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pricepurchase.findAll", query = "SELECT p FROM Pricepurchase p"),
    @NamedQuery(name = "Pricepurchase.findCurrentByProdId", query = "SELECT p FROM Pricepurchase p WHERE p.prodId.prodId = :prodId AND p.pricePurFinalDate IS NULL"),
    @NamedQuery(name = "Pricepurchase.findByPricePurId", query = "SELECT p FROM Pricepurchase p WHERE p.pricePurId = :pricePurId"),
    @NamedQuery(name = "Pricepurchase.findByPricePurValue", query = "SELECT p FROM Pricepurchase p WHERE p.pricePurValue = :pricePurValue"),
    @NamedQuery(name = "Pricepurchase.findByPricePurInitialDate", query = "SELECT p FROM Pricepurchase p WHERE p.pricePurInitialDate = :pricePurInitialDate"),
    @NamedQuery(name = "Pricepurchase.findByPricePurFinalDate", query = "SELECT p FROM Pricepurchase p WHERE p.pricePurFinalDate = :pricePurFinalDate")})
public class Pricepurchase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pricePurId")
    private Long pricePurId;
    @Basic(optional = false)
    @Column(name = "pricePurValue")
    private int pricePurValue;
    @Basic(optional = false)
    @Column(name = "pricePurInitialDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pricePurInitialDate;
    @Column(name = "pricePurFinalDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pricePurFinalDate;
    @JoinColumn(name = "prodId", referencedColumnName = "prodId")
    @ManyToOne(optional = false)
    private Product prodId;

    public Pricepurchase() {
    }

    public Pricepurchase(Long pricePurId) {
        this.pricePurId = pricePurId;
    }

    public Pricepurchase(Long pricePurId, int pricePurValue, Date pricePurInitialDate) {
        this.pricePurId = pricePurId;
        this.pricePurValue = pricePurValue;
        this.pricePurInitialDate = pricePurInitialDate;
    }

    public Long getPricePurId() {
        return pricePurId;
    }

    public void setPricePurId(Long pricePurId) {
        this.pricePurId = pricePurId;
    }

    public int getPricePurValue() {
        return pricePurValue;
    }

    public void setPricePurValue(int pricePurValue) {
        this.pricePurValue = pricePurValue;
    }

    public Date getPricePurInitialDate() {
        return pricePurInitialDate;
    }

    public void setPricePurInitialDate(Date pricePurInitialDate) {
        this.pricePurInitialDate = pricePurInitialDate;
    }

    public Date getPricePurFinalDate() {
        return pricePurFinalDate;
    }

    public void setPricePurFinalDate(Date pricePurFinalDate) {
        this.pricePurFinalDate = pricePurFinalDate;
    }

    public Product getProdId() {
        return prodId;
    }

    public void setProdId(Product prodId) {
        this.prodId = prodId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pricePurId != null ? pricePurId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pricepurchase)) {
            return false;
        }
        Pricepurchase other = (Pricepurchase) object;
        if ((this.pricePurId == null && other.pricePurId != null) || (this.pricePurId != null && !this.pricePurId.equals(other.pricePurId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pricepurchase[ pricePurId=" + pricePurId + " ]";
    }
    
}
