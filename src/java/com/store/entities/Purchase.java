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
    @NamedQuery(name = "Purchase.findByCliIdCredit", query = "SELECT p FROM Purchase p WHERE p.cliId.cliId = :cliId AND (p.purState = 2 OR p.purState = 3) ORDER BY p.purDate DESC"),
    @NamedQuery(name = "Purchase.findByCliIdAndStatePending", query = "SELECT p FROM Purchase p WHERE p.cliId.cliId = :cliId AND p.purState = 2 ORDER BY p.purDate ASC"),
    @NamedQuery(name = "Purchase.findByPurDiscount", query = "SELECT p FROM Purchase p WHERE p.purDiscount = :purDiscount"),
    @NamedQuery(name = "Purchase.findYears", query = "SELECT EXTRACT(YEAR FROM p.purDate) FROM Purchase p GROUP BY EXTRACT(YEAR FROM p.purDate)"),
    @NamedQuery(name = "Purchase.findTotalEachMonthByYear", query = "SELECT EXTRACT(MONTH FROM p.purDate), SUM(p.purFinalAmount) FROM Purchase p WHERE EXTRACT(YEAR FROM p.purDate) =:year  GROUP BY EXTRACT(MONTH FROM p.purDate)"),
    @NamedQuery(name = "Purchase.findSaleForResume", query = "SELECT p FROM Purchase p WHERE p.purState = 0 and p.usId.usId = :cashierId"),
    @NamedQuery(name = "Purchase.findPurshaseUsIdAndDay", query = "SELECT p FROM Purchase p WHERE p.purDate >= :initialDate and p.purDate <= :endDate and p.usId.usId = :cashierId and p.purState = 1"),
    @NamedQuery(name = "Purchase.findPurshaseTotalInitialDayEndDay", query = "SELECT pt.ownId,SUM(pt.purToTotal) AS total, SUM(pt.purToGain) AS gain,SUM(pt.purToIva) AS iva FROM Purchase p INNER JOIN Purchasetotal pt WHERE p.purDate >= :initialDate and p.purDate <= :endDate and p.purState = 1 and p.purId = pt.purId.purId group by pt.ownId"),
    @NamedQuery(name = "Purchase.findProductQuantityInitialDayEndDay", query = "SELECT pi.prodId,SUM(pi.purItemQuantity) As quantity,SUM(pi.priceValue * pi.purItemQuantity),SUM(pi.iva * pi.purItemQuantity),SUM(pi.pricePurValue * pi.purItemQuantity) FROM Purchase p INNER JOIN Purchaseitem pi WHERE p.purDate >= :initialDate and p.purDate <= :endDate and p.purState = 1 and p.purId = pi.purId.purId and pi.ownId.ownId = :ownId group by pi.prodId order by quantity DESC"),
    @NamedQuery(name = "Purchase.findPurchaseBetweenInitDateAndEndDate", query = "SELECT  p FROM Purchase p WHERE p.purDate >= :initialDate and p.purDate <= :endDate and p.purState = 1 order by p.purDate Desc"),
    @NamedQuery(name = "Purchase.findByPurPayment", query = "SELECT p FROM Purchase p WHERE p.purPayment = :purPayment")})
public class Purchase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "purId")
    private Long purId;
    @Basic(optional = false)
    @Column(name = "purDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purDate;
    @Column(name = "purFinalAmount")
    private Integer purFinalAmount;
    @Column(name = "purDiscount")
    private Integer purDiscount;
    @Column(name = "purPayment")
    private Integer purPayment;
    @Basic(optional = false)
    @Column(name = "purState")
    private int purState;
    @Column(name = "purBill")
    private String purBill;
    @JoinColumn(name = "cliId", referencedColumnName = "cliId")
    @ManyToOne
    private Client cliId;
    @JoinColumn(name = "usId", referencedColumnName = "usId")
    @ManyToOne(optional = false)
    private User usId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purId")
    private List<Purchaseitem> purchaseitemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purId")
    private List<Purchasetotal> purchasetotalList;

    public Purchase() {
    }

    public Purchase(Long purId) {
        this.purId = purId;
    }

    public Purchase(Long purId, Date purDate, int purState) {
        this.purId = purId;
        this.purDate = purDate;
        this.purState = purState;
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

    public Integer getPurFinalAmount() {
        return purFinalAmount;
    }

    public void setPurFinalAmount(Integer purFinalAmount) {
        this.purFinalAmount = purFinalAmount;
    }

    public Integer getPurDiscount() {
        return purDiscount;
    }

    public void setPurDiscount(Integer purDiscount) {
        this.purDiscount = purDiscount;
    }

    public Integer getPurPayment() {
        return purPayment;
    }

    public void setPurPayment(Integer purPayment) {
        this.purPayment = purPayment;
    }

    public int getPurState() {
        return purState;
    }

    public void setPurState(int purState) {
        this.purState = purState;
    }

    public String getPurBill() {
        return purBill;
    }

    public void setPurBill(String purBill) {
        this.purBill = purBill;
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
        return "entities.Purchase[ purId=" + purId + " ]";
    }
}
