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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "price", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Price.findAll", query = "SELECT p FROM Price p"),
    @NamedQuery(name = "Price.findByPriceId", query = "SELECT p FROM Price p WHERE p.priceId = :priceId"),
    @NamedQuery(name = "Price.findCurrentByProdId", query = "SELECT p FROM Price p WHERE p.prodId.prodId = :prodId AND p.priceFinalDate IS NULL"),
    @NamedQuery(name = "Price.findByPriceValue", query = "SELECT p FROM Price p WHERE p.priceValue = :priceValue"),
    @NamedQuery(name = "Price.findByPriceInitialDate", query = "SELECT p FROM Price p WHERE p.priceInitialDate = :priceInitialDate"),
    @NamedQuery(name = "Price.findByPriceFinalDate", query = "SELECT p FROM Price p WHERE p.priceFinalDate = :priceFinalDate")})
public class Price implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "priceId")
    private Long priceId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "priceValue")
    private int priceValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "priceInitialDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date priceInitialDate;
    @Column(name = "priceFinalDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date priceFinalDate;
    @JoinColumn(name = "prodId", referencedColumnName = "prodId")
    @ManyToOne(optional = false)
    private Product prodId;

    public Price() {
    }

    public Price(Long priceId) {
        this.priceId = priceId;
    }

    public Price(Long priceId, int priceValue, Date priceInitialDate) {
        this.priceId = priceId;
        this.priceValue = priceValue;
        this.priceInitialDate = priceInitialDate;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public int getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(int priceValue) {
        this.priceValue = priceValue;
    }

    public Date getPriceInitialDate() {
        return priceInitialDate;
    }

    public void setPriceInitialDate(Date priceInitialDate) {
        this.priceInitialDate = priceInitialDate;
    }

    public Date getPriceFinalDate() {
        return priceFinalDate;
    }

    public void setPriceFinalDate(Date priceFinalDate) {
        this.priceFinalDate = priceFinalDate;
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
        hash += (priceId != null ? priceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Price)) {
            return false;
        }
        Price other = (Price) object;
        if ((this.priceId == null && other.priceId != null) || (this.priceId != null && !this.priceId.equals(other.priceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.store.entities.Price[ priceId=" + priceId + " ]";
    }
    
}
