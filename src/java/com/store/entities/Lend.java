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
@Table(name = "lend", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lend.findAll", query = "SELECT l FROM Lend l"),
    @NamedQuery(name = "Lend.findByLendId", query = "SELECT l FROM Lend l WHERE l.lendId = :lendId"),
    @NamedQuery(name = "Lend.findByLendDate", query = "SELECT l FROM Lend l WHERE l.lendDate = :lendDate"),
    @NamedQuery(name = "Lend.findByLendValue", query = "SELECT l FROM Lend l WHERE l.lendValue = :lendValue"),
    @NamedQuery(name = "Lend.findByLendPayment", query = "SELECT l FROM Lend l WHERE l.lendPayment = :lendPayment"),
    @NamedQuery(name = "Lend.findByCliId", query = "SELECT l FROM Lend l WHERE l.cliId.cliId = :cliId ORDER BY l.lendDate DESC"),
    @NamedQuery(name = "Lend.findByCliIdAndStatePending", query = "SELECT l FROM Lend l WHERE l.cliId.cliId = :cliId AND l.lendState = 0 ORDER BY l.lendDate ASC"),
    @NamedQuery(name = "Lend.findByLendState", query = "SELECT l FROM Lend l WHERE l.lendState = :lendState")})
public class Lend implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lendId")
    private Long lendId;
    @Basic(optional = false)
    @Column(name = "lendDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lendDate;
    @Basic(optional = false)
    @Column(name = "lendValue")
    private int lendValue;
    @Column(name = "lendPayment")
    private Integer lendPayment;
    @Basic(optional = false)
    @Column(name = "lendState")
    private int lendState;
    @JoinColumn(name = "cliId", referencedColumnName = "cliId")
    @ManyToOne(optional = false)
    private Client cliId;
    @JoinColumn(name = "usId", referencedColumnName = "usId")
    @ManyToOne(optional = false)
    private User usId;

    public Lend() {
    }

    public Lend(Long lendId) {
        this.lendId = lendId;
    }

    public Lend(Long lendId, Date lendDate, int lendValue, int lendState) {
        this.lendId = lendId;
        this.lendDate = lendDate;
        this.lendValue = lendValue;
        this.lendState = lendState;
    }

    public Long getLendId() {
        return lendId;
    }

    public void setLendId(Long lendId) {
        this.lendId = lendId;
    }

    public Date getLendDate() {
        return lendDate;
    }

    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    public int getLendValue() {
        return lendValue;
    }

    public void setLendValue(int lendValue) {
        this.lendValue = lendValue;
    }

    public Integer getLendPayment() {
        return lendPayment;
    }

    public void setLendPayment(Integer lendPayment) {
        this.lendPayment = lendPayment;
    }

    public int getLendState() {
        return lendState;
    }

    public void setLendState(int lendState) {
        this.lendState = lendState;
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
        hash += (lendId != null ? lendId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lend)) {
            return false;
        }
        Lend other = (Lend) object;
        if ((this.lendId == null && other.lendId != null) || (this.lendId != null && !this.lendId.equals(other.lendId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Lend[ lendId=" + lendId + " ]";
    }
    
}
