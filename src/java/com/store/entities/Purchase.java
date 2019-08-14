/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "purchase", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Purchase.findAll", query = "SELECT p FROM Purchase p"),
    @NamedQuery(name = "Purchase.findByPurId", query = "SELECT p FROM Purchase p WHERE p.purId = :purId"),
    @NamedQuery(name = "Purchase.findByPurDate", query = "SELECT p FROM Purchase p WHERE p.purDate = :purDate"),
    @NamedQuery(name = "Purchase.findByPurFinalAmount", query = "SELECT p FROM Purchase p WHERE p.purFinalAmount = :purFinalAmount"),
    @NamedQuery(name = "Purchase.findByPurDiscount", query = "SELECT p FROM Purchase p WHERE p.purDiscount = :purDiscount"),
    @NamedQuery(name = "Purchase.findByPurPayment", query = "SELECT p FROM Purchase p WHERE p.purPayment = :purPayment")})
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "purId")
    private Long purId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "purDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "purFinalAmount")
    private int purFinalAmount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "purDiscount")
    private int purDiscount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "purPayment")
    private int purPayment;
    @JoinColumn(name = "cliId", referencedColumnName = "cliId")
    @ManyToOne
    private Client cliId;
    @JoinColumn(name = "staId", referencedColumnName = "staId")
    @ManyToOne(optional = false)
    private Status staId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purId")
    private List<Purchaseitem> purchaseitemList;

    public Purchase() {
    }

    public Purchase(Long purId) {
        this.purId = purId;
    }

    public Purchase(Long purId, Date purDate, int purFinalAmount, int purDiscount, int purPayment) {
        this.purId = purId;
        this.purDate = purDate;
        this.purFinalAmount = purFinalAmount;
        this.purDiscount = purDiscount;
        this.purPayment = purPayment;
    }

    public Long getPurId() {
        return purId;
    }

    public void setPurId(Long purId) {
        this.purId = purId;
    }

    public Date getPurDate() {
        return purDate;
    }

    public void setPurDate(Date purDate) {
        this.purDate = purDate;
    }

    public int getPurFinalAmount() {
        return purFinalAmount;
    }

    public void setPurFinalAmount(int purFinalAmount) {
        this.purFinalAmount = purFinalAmount;
    }

    public int getPurDiscount() {
        return purDiscount;
    }

    public void setPurDiscount(int purDiscount) {
        this.purDiscount = purDiscount;
    }

    public int getPurPayment() {
        return purPayment;
    }

    public void setPurPayment(int purPayment) {
        this.purPayment = purPayment;
    }

    public Client getCliId() {
        return cliId;
    }

    public void setCliId(Client cliId) {
        this.cliId = cliId;
    }

    public Status getStaId() {
        return staId;
    }

    public void setStaId(Status staId) {
        this.staId = staId;
    }

    @XmlTransient
    public List<Purchaseitem> getPurchaseitemList() {
        return purchaseitemList;
    }

    public void setPurchaseitemList(List<Purchaseitem> purchaseitemList) {
        this.purchaseitemList = purchaseitemList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purId != null ? purId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Purchase)) {
            return false;
        }
        Purchase other = (Purchase) object;
        if ((this.purId == null && other.purId != null) || (this.purId != null && !this.purId.equals(other.purId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.store.entities.Purchase[ purId=" + purId + " ]";
    }
    
}
