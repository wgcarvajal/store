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
 * @author aranda
 */
@Entity
@Table(name = "purchasetotal", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Purchasetotal.findAll", query = "SELECT p FROM Purchasetotal p")})
public class Purchasetotal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "purToId")
    private Long purToId;
    @Basic(optional = false)
    @Column(name = "purToTotal")
    private int purToTotal;
    @Basic(optional = false)
    @Column(name = "purToGain")
    private int purToGain;
    @Basic(optional = false)
    @Column(name = "purToIva")
    private int purToIva;
    @JoinColumn(name = "ownId", referencedColumnName = "ownId")
    @ManyToOne(optional = false)
    private Owner ownId;
    @JoinColumn(name = "purId", referencedColumnName = "purId")
    @ManyToOne(optional = false)
    private Purchase purId;

    public Purchasetotal() {
    }

    public Purchasetotal(Long purToId) {
        this.purToId = purToId;
    }

    public Purchasetotal(Long purToId, int purToTotal, int purToGain, int purToIva) {
        this.purToId = purToId;
        this.purToTotal = purToTotal;
        this.purToGain = purToGain;
        this.purToIva = purToIva;
    }

    public Long getPurToId() {
        return purToId;
    }

    public void setPurToId(Long purToId) {
        this.purToId = purToId;
    }

    public int getPurToTotal() {
        return purToTotal;
    }

    public void setPurToTotal(int purToTotal) {
        this.purToTotal = purToTotal;
    }

    public int getPurToGain() {
        return purToGain;
    }

    public void setPurToGain(int purToGain) {
        this.purToGain = purToGain;
    }

    public int getPurToIva() {
        return purToIva;
    }

    public void setPurToIva(int purToIva) {
        this.purToIva = purToIva;
    }

    public Owner getOwnId() {
        return ownId;
    }

    public void setOwnId(Owner ownId) {
        this.ownId = ownId;
    }

    public Purchase getPurId() {
        return purId;
    }

    public void setPurId(Purchase purId) {
        this.purId = purId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purToId != null ? purToId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Purchasetotal)) {
            return false;
        }
        Purchasetotal other = (Purchasetotal) object;
        if ((this.purToId == null && other.purToId != null) || (this.purToId != null && !this.purToId.equals(other.purToId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Purchasetotal[ purToId=" + purToId + " ]";
    }
    
}
