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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author WIlson carvajal
 */
@Entity
@Table(name = "cash", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cash.findAll", query = "SELECT c FROM Cash c"),
    @NamedQuery(name = "Cash.findByCashId", query = "SELECT c FROM Cash c WHERE c.cashId = :cashId"),
    @NamedQuery(name = "Cash.findByCashName", query = "SELECT c FROM Cash c WHERE c.cashName = :cashName"),
    @NamedQuery(name = "Cash.findByCashIP", query = "SELECT c FROM Cash c WHERE c.cashIP = :cashIP"),
    @NamedQuery(name = "Cash.findByCashPaperSize", query = "SELECT c FROM Cash c WHERE c.cashPaperSize = :cashPaperSize"),
    @NamedQuery(name = "Cash.findByCashPrintName", query = "SELECT c FROM Cash c WHERE c.cashPrintName = :cashPrintName"),
    @NamedQuery(name = "Cash.findByCashPrintCommandOpenCashDrawer", query = "SELECT c FROM Cash c WHERE c.cashPrintCommandOpenCashDrawer = :cashPrintCommandOpenCashDrawer")})
public class Cash implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cashId")
    private Integer cashId;
    @Basic(optional = false)
    @Column(name = "cashName")
    private String cashName;
    @Basic(optional = false)
    @Column(name = "cashIP")
    private String cashIP;
    @Basic(optional = false)
    @Column(name = "cashPaperSize")
    private int cashPaperSize;
    @Basic(optional = false)
    @Column(name = "cashPrintName")
    private String cashPrintName;
    @Basic(optional = false)
    @Column(name = "cashPrintCommandOpenCashDrawer")
    private String cashPrintCommandOpenCashDrawer;

    public Cash() {
    }

    public Cash(Integer cashId) {
        this.cashId = cashId;
    }

    public Cash(Integer cashId, String cashName, String cashIP, int cashPaperSize, String cashPrintName, String cashPrintCommandOpenCashDrawer) {
        this.cashId = cashId;
        this.cashName = cashName;
        this.cashIP = cashIP;
        this.cashPaperSize = cashPaperSize;
        this.cashPrintName = cashPrintName;
        this.cashPrintCommandOpenCashDrawer = cashPrintCommandOpenCashDrawer;
    }

    public Integer getCashId() {
        return cashId;
    }

    public void setCashId(Integer cashId) {
        this.cashId = cashId;
    }

    public String getCashName() {
        return cashName;
    }

    public void setCashName(String cashName) {
        this.cashName = cashName;
    }

    public String getCashIP() {
        return cashIP;
    }

    public void setCashIP(String cashIP) {
        this.cashIP = cashIP;
    }

    public int getCashPaperSize() {
        return cashPaperSize;
    }

    public void setCashPaperSize(int cashPaperSize) {
        this.cashPaperSize = cashPaperSize;
    }

    public String getCashPrintName() {
        return cashPrintName;
    }

    public void setCashPrintName(String cashPrintName) {
        this.cashPrintName = cashPrintName;
    }

    public String getCashPrintCommandOpenCashDrawer() {
        return cashPrintCommandOpenCashDrawer;
    }

    public void setCashPrintCommandOpenCashDrawer(String cashPrintCommandOpenCashDrawer) {
        this.cashPrintCommandOpenCashDrawer = cashPrintCommandOpenCashDrawer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cashId != null ? cashId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cash)) {
            return false;
        }
        Cash other = (Cash) object;
        if ((this.cashId == null && other.cashId != null) || (this.cashId != null && !this.cashId.equals(other.cashId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cash[ cashId=" + cashId + " ]";
    }
    
}
