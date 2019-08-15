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
@Table(name = "provides", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Provides.findAll", query = "SELECT p FROM Provides p"),
    @NamedQuery(name = "Provides.findByProvidesId", query = "SELECT p FROM Provides p WHERE p.providesId = :providesId"),
    @NamedQuery(name = "Provides.findCurrentByProdId", query = "SELECT p FROM Provides p WHERE p.prodId.prodId = :prodId And p.providesFinalDate IS NULL"),
    @NamedQuery(name = "Provides.findByProvidesInitialDate", query = "SELECT p FROM Provides p WHERE p.providesInitialDate = :providesInitialDate"),
    @NamedQuery(name = "Provides.findByProvidesFinalDate", query = "SELECT p FROM Provides p WHERE p.providesFinalDate = :providesFinalDate")})
public class Provides implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "providesId")
    private Long providesId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "providesInitialDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date providesInitialDate;
    @Column(name = "providesFinalDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date providesFinalDate;
    @JoinColumn(name = "prodId", referencedColumnName = "prodId")
    @ManyToOne(optional = false)
    private Product prodId;
    @JoinColumn(name = "provId", referencedColumnName = "provId")
    @ManyToOne(optional = false)
    private Provider provId;

    public Provides() {
    }

    public Provides(Long providesId) {
        this.providesId = providesId;
    }

    public Provides(Long providesId, Date providesInitialDate) {
        this.providesId = providesId;
        this.providesInitialDate = providesInitialDate;
    }

    public Long getProvidesId() {
        return providesId;
    }

    public void setProvidesId(Long providesId) {
        this.providesId = providesId;
    }

    public Date getProvidesInitialDate() {
        return providesInitialDate;
    }

    public void setProvidesInitialDate(Date providesInitialDate) {
        this.providesInitialDate = providesInitialDate;
    }

    public Date getProvidesFinalDate() {
        return providesFinalDate;
    }

    public void setProvidesFinalDate(Date providesFinalDate) {
        this.providesFinalDate = providesFinalDate;
    }

    public Product getProdId() {
        return prodId;
    }

    public void setProdId(Product prodId) {
        this.prodId = prodId;
    }

    public Provider getProvId() {
        return provId;
    }

    public void setProvId(Provider provId) {
        this.provId = provId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (providesId != null ? providesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provides)) {
            return false;
        }
        Provides other = (Provides) object;
        if ((this.providesId == null && other.providesId != null) || (this.providesId != null && !this.providesId.equals(other.providesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.store.entities.Provides[ providesId=" + providesId + " ]";
    }
    
}
