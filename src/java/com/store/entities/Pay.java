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
@Table(name = "pay", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pay.findAll", query = "SELECT p FROM Pay p"),
    @NamedQuery(name = "Pay.findByPayId", query = "SELECT p FROM Pay p WHERE p.payId = :payId"),
    @NamedQuery(name = "Pay.findByPayDate", query = "SELECT p FROM Pay p WHERE p.payDate = :payDate"),
    @NamedQuery(name = "Pay.findByCliId", query = "SELECT p FROM Pay p WHERE p.cliId.cliId = :cliId ORDER BY p.payDate DESC"),
    @NamedQuery(name = "Pay.findByPayValue", query = "SELECT p FROM Pay p WHERE p.payValue = :payValue")})
public class Pay implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "payId")
    private Long payId;
    @Basic(optional = false)
    @Column(name = "payDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payDate;
    @Basic(optional = false)
    @Column(name = "payValue")
    private int payValue;
    @JoinColumn(name = "cliId", referencedColumnName = "cliId")
    @ManyToOne(optional = false)
    private Client cliId;
    @JoinColumn(name = "usId", referencedColumnName = "usId")
    @ManyToOne(optional = false)
    private User usId;

    public Pay() {
    }

    public Pay(Long payId) {
        this.payId = payId;
    }

    public Pay(Long payId, Date payDate, int payValue) {
        this.payId = payId;
        this.payDate = payDate;
        this.payValue = payValue;
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public int getPayValue() {
        return payValue;
    }

    public void setPayValue(int payValue) {
        this.payValue = payValue;
    }

    public Client getCliId() {
        return cliId;
    }

    public void setCliId(Client cliId) {
        this.cliId = cliId;
    }

    public User getUsId() {
        return usId;
    }

    public void setUsId(User usId) {
        this.usId = usId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (payId != null ? payId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pay)) {
            return false;
        }
        Pay other = (Pay) object;
        if ((this.payId == null && other.payId != null) || (this.payId != null && !this.payId.equals(other.payId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pay[ payId=" + payId + " ]";
    }
    
}
