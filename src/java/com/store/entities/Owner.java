/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "owner", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Owner.findAll", query = "SELECT o FROM Owner o")})
public class Owner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ownId")
    private Integer ownId;
    @Basic(optional = false)
    @Column(name = "ownName")
    private String ownName;
    @Basic(optional = false)
    @Lob
    @Column(name = "ownDescription")
    private String ownDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownId")
    private List<Product> productList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownId")
    private List<Purchaseitem> purchaseitemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownId")
    private List<Purchasetotal> purchasetotalList;

    public Owner() {
    }

    public Owner(Integer ownId) {
        this.ownId = ownId;
    }

    public Owner(Integer ownId, String ownName, String ownDescription) {
        this.ownId = ownId;
        this.ownName = ownName;
        this.ownDescription = ownDescription;
    }

    public Integer getOwnId() {
        return ownId;
    }

    public void setOwnId(Integer ownId) {
        this.ownId = ownId;
    }

    public String getOwnName() {
        return ownName;
    }

    public void setOwnName(String ownName) {
        this.ownName = ownName;
    }

    public String getOwnDescription() {
        return ownDescription;
    }

    public void setOwnDescription(String ownDescription) {
        this.ownDescription = ownDescription;
    }

    @XmlTransient
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @XmlTransient
    public List<Purchaseitem> getPurchaseitemList() {
        return purchaseitemList;
    }

    public void setPurchaseitemList(List<Purchaseitem> purchaseitemList) {
        this.purchaseitemList = purchaseitemList;
    }

    @XmlTransient
    public List<Purchasetotal> getPurchasetotalList() {
        return purchasetotalList;
    }

    public void setPurchasetotalList(List<Purchasetotal> purchasetotalList) {
        this.purchasetotalList = purchasetotalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ownId != null ? ownId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Owner)) {
            return false;
        }
        Owner other = (Owner) object;
        if ((this.ownId == null && other.ownId != null) || (this.ownId != null && !this.ownId.equals(other.ownId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Owner[ ownId=" + ownId + " ]";
    }
    
}
