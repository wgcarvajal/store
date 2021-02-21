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
 * @author Wilson Carvajal
 */
@Entity
@Table(name = "orders", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findByOrId", query = "SELECT o FROM Orders o WHERE o.orId = :orId"),
    @NamedQuery(name = "Orders.findByOrDate", query = "SELECT o FROM Orders o WHERE o.orDate = :orDate"),
    @NamedQuery(name = "Orders.findByOrDateReceived", query = "SELECT o FROM Orders o WHERE o.orDateReceived = :orDateReceived"),
    @NamedQuery(name = "Orders.findByOrDeliveryDate", query = "SELECT o FROM Orders o WHERE o.orDeliveryDate = :orDeliveryDate"),
    @NamedQuery(name = "Orders.findByOrState", query = "SELECT o FROM Orders o WHERE o.orState = :orState"),
    @NamedQuery(name = "Orders.findStartAndFinalDate", query = "SELECT NEW com.store.model.OrderEvent(FUNC('YEAR', o.orDeliveryDate),FUNC('MONTH', o.orDeliveryDate),FUNC('DAY', o.orDeliveryDate),SUM(o.orTotal),o.orState) FROM Orders o WHERE o.orDeliveryDate BETWEEN :startDate and :endDate GROUP BY FUNC('DAY', o.orDeliveryDate),FUNC('MONTH', o.orDeliveryDate),FUNC('YEAR', o.orDeliveryDate),o.orState"),
    @NamedQuery(name = "Orders.findOrdersByYearsMonthAndDay", query = "SELECT o FROM Orders o WHERE FUNC('YEAR', o.orDeliveryDate) = :year AND FUNC('MONTH', o.orDeliveryDate) = :month AND FUNC('DAY', o.orDeliveryDate) =:day"),
    @NamedQuery(name = "Orders.findByOrTotal", query = "SELECT o FROM Orders o WHERE o.orTotal = :orTotal")})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orId")
    private Long orId;
    @Basic(optional = false)
    @Column(name = "orDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orDate;
    @Column(name = "orDateReceived")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orDateReceived;
    @Basic(optional = false)
    @Column(name = "orDeliveryDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orDeliveryDate;
    @Basic(optional = false)
    @Column(name = "orState")
    private int orState;
    @Column(name = "orTotal")
    private Integer orTotal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orId")
    private List<Orderitem> orderitemList;
    @JoinColumn(name = "provId", referencedColumnName = "provId")
    @ManyToOne(optional = false)
    private Provider provId;
    @JoinColumn(name = "usId", referencedColumnName = "usId")
    @ManyToOne(optional = false)
    private User usId;

    public Orders() {
    }

    public Orders(Long orId) {
        this.orId = orId;
    }

    public Orders(Long orId, Date orDate, Date orDeliveryDate, int orState) {
        this.orId = orId;
        this.orDate = orDate;
        this.orDeliveryDate = orDeliveryDate;
        this.orState = orState;
    }

    public Long getOrId() {
        return orId;
    }

    public void setOrId(Long orId) {
        this.orId = orId;
    }

    public Date getOrDate() {
        return orDate;
    }

    public void setOrDate(Date orDate) {
        this.orDate = orDate;
    }

    public Date getOrDateReceived() {
        return orDateReceived;
    }

    public void setOrDateReceived(Date orDateReceived) {
        this.orDateReceived = orDateReceived;
    }

    public Date getOrDeliveryDate() {
        return orDeliveryDate;
    }

    public void setOrDeliveryDate(Date orDeliveryDate) {
        this.orDeliveryDate = orDeliveryDate;
    }

    public int getOrState() {
        return orState;
    }

    public void setOrState(int orState) {
        this.orState = orState;
    }

    public Integer getOrTotal() {
        return orTotal;
    }

    public void setOrTotal(Integer orTotal) {
        this.orTotal = orTotal;
    }

    @XmlTransient
    public List<Orderitem> getOrderitemList() {
        return orderitemList;
    }

    public void setOrderitemList(List<Orderitem> orderitemList) {
        this.orderitemList = orderitemList;
    }

    public Provider getProvId() {
        return provId;
    }

    public void setProvId(Provider provId) {
        this.provId = provId;
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
        hash += (orId != null ? orId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.orId == null && other.orId != null) || (this.orId != null && !this.orId.equals(other.orId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Orders[ orId=" + orId + " ]";
    }
    
}
