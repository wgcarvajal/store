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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Wilson Carvajal
 */
@Entity
@Table(name = "orderitem", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orderitem.findAll", query = "SELECT o FROM Orderitem o"),
    @NamedQuery(name = "Orderitem.findByOrItemId", query = "SELECT o FROM Orderitem o WHERE o.orItemId = :orItemId"),
    @NamedQuery(name = "Orderitem.findByOrItemProdUnregister", query = "SELECT o FROM Orderitem o WHERE o.orItemProdUnregister = :orItemProdUnregister"),
    @NamedQuery(name = "Orderitem.findByOrItemQuantity", query = "SELECT o FROM Orderitem o WHERE o.orItemQuantity = :orItemQuantity"),
    @NamedQuery(name = "Orderitem.findByOrItemUnitPrice", query = "SELECT o FROM Orderitem o WHERE o.orItemUnitPrice = :orItemUnitPrice")})
public class Orderitem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orItemId")
    private Long orItemId;
    @Column(name = "orItemProdUnregister")
    private String orItemProdUnregister;
    @Basic(optional = false)
    @Column(name = "orItemQuantity")
    private int orItemQuantity;
    @Basic(optional = false)
    @Column(name = "orItemUnitPrice")
    private int orItemUnitPrice;
    @JoinColumn(name = "orId", referencedColumnName = "orId")
    @ManyToOne(optional = false)
    private Orders orId;
    @JoinColumn(name = "prodId", referencedColumnName = "prodId")
    @ManyToOne
    private Product prodId;

    public Orderitem() {
    }

    public Orderitem(Long orItemId) {
        this.orItemId = orItemId;
    }

    public Orderitem(Long orItemId, int orItemQuantity, int orItemUnitPrice) {
        this.orItemId = orItemId;
        this.orItemQuantity = orItemQuantity;
        this.orItemUnitPrice = orItemUnitPrice;
    }

    public Long getOrItemId() {
        return orItemId;
    }

    public void setOrItemId(Long orItemId) {
        this.orItemId = orItemId;
    }

    public String getOrItemProdUnregister() {
        return orItemProdUnregister;
    }

    public void setOrItemProdUnregister(String orItemProdUnregister) {
        this.orItemProdUnregister = orItemProdUnregister;
    }

    public int getOrItemQuantity() {
        return orItemQuantity;
    }

    public void setOrItemQuantity(int orItemQuantity) {
        this.orItemQuantity = orItemQuantity;
    }

    public int getOrItemUnitPrice() {
        return orItemUnitPrice;
    }

    public void setOrItemUnitPrice(int orItemUnitPrice) {
        this.orItemUnitPrice = orItemUnitPrice;
    }

    public Orders getOrId() {
        return orId;
    }

    public void setOrId(Orders orId) {
        this.orId = orId;
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
        hash += (orItemId != null ? orItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orderitem)) {
            return false;
        }
        Orderitem other = (Orderitem) object;
        if ((this.orItemId == null && other.orItemId != null) || (this.orItemId != null && !this.orItemId.equals(other.orItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Orderitem[ orItemId=" + orItemId + " ]";
    }
    
}
