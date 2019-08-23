/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "purchaseitem", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Purchaseitem.findAll", query = "SELECT p FROM Purchaseitem p"),
    @NamedQuery(name = "Purchaseitem.findByPurItemId", query = "SELECT p FROM Purchaseitem p WHERE p.purItemId = :purItemId"),
    @NamedQuery(name = "Purchaseitem.findByPurItemQuantity", query = "SELECT p FROM Purchaseitem p WHERE p.purItemQuantity = :purItemQuantity")})
public class Purchaseitem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "purItemId")
    private Long purItemId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "purItemQuantity")
    private int purItemQuantity;
    @JoinColumn(name = "prodId", referencedColumnName = "prodId")
    @ManyToOne(optional = false)
    private Product prodId;
    @JoinColumn(name = "purId", referencedColumnName = "purId")
    @ManyToOne(optional = false)
    private Purchase purId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "priceValue")
    private int priceValue;

    public Purchaseitem() {
    }

    public Purchaseitem(Long purItemId) {
        this.purItemId = purItemId;
    }

    public Purchaseitem(Long purItemId, int purItemQuantity) {
        this.purItemId = purItemId;
        this.purItemQuantity = purItemQuantity;
    }

    public Long getPurItemId() {
        return purItemId;
    }

    public void setPurItemId(Long purItemId) {
        this.purItemId = purItemId;
    }

    public int getPurItemQuantity() {
        return purItemQuantity;
    }

    public void setPurItemQuantity(int purItemQuantity) {
        this.purItemQuantity = purItemQuantity;
    }

    public Product getProdId() {
        return prodId;
    }

    public void setProdId(Product prodId) {
        this.prodId = prodId;
    }

    public Purchase getPurId() {
        return purId;
    }

    public void setPurId(Purchase purId) {
        this.purId = purId;
    }

    public int getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(int priceValue) {
        this.priceValue = priceValue;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purItemId != null ? purItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Purchaseitem)) {
            return false;
        }
        Purchaseitem other = (Purchaseitem) object;
        if ((this.purItemId == null && other.purItemId != null) || (this.purItemId != null && !this.purItemId.equals(other.purItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.store.entities.Purchaseitem[ purItemId=" + purItemId + " ]";
    }
    
}
