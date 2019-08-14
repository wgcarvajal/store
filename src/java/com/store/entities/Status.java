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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "status", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Status.findAll", query = "SELECT s FROM Status s"),
    @NamedQuery(name = "Status.findByStaId", query = "SELECT s FROM Status s WHERE s.staId = :staId"),
    @NamedQuery(name = "Status.findByStaNombre", query = "SELECT s FROM Status s WHERE s.staNombre = :staNombre")})
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "staId")
    private Long staId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "staNombre")
    private String staNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staId")
    private List<Purchase> purchaseList;

    public Status() {
    }

    public Status(Long staId) {
        this.staId = staId;
    }

    public Status(Long staId, String staNombre) {
        this.staId = staId;
        this.staNombre = staNombre;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public String getStaNombre() {
        return staNombre;
    }

    public void setStaNombre(String staNombre) {
        this.staNombre = staNombre;
    }

    @XmlTransient
    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (staId != null ? staId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Status)) {
            return false;
        }
        Status other = (Status) object;
        if ((this.staId == null && other.staId != null) || (this.staId != null && !this.staId.equals(other.staId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.store.entities.Status[ staId=" + staId + " ]";
    }
    
}
