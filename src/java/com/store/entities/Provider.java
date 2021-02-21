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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "provider", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Provider.findAll", query = "SELECT p FROM Provider p"),
    @NamedQuery(name = "Provider.findByProvId", query = "SELECT p FROM Provider p WHERE p.provId = :provId"),
    @NamedQuery(name = "Provider.findByProvName", query = "SELECT p FROM Provider p WHERE p.provName = :provName"),
    @NamedQuery(name = "Provider.findByProvPhones", query = "SELECT p FROM Provider p WHERE p.provPhones = :provPhones"),
    @NamedQuery(name = "Provider.findByProvAddress", query = "SELECT p FROM Provider p WHERE p.provAddress = :provAddress"),
    @NamedQuery(name = "Provider.findByProvRut", query = "SELECT p FROM Provider p WHERE p.provRut = :provRut"),
    @NamedQuery(name = "Provider.findByProvNit", query = "SELECT p FROM Provider p WHERE p.provNit = :provNit"),
    @NamedQuery(name = "Provider.findByProvWeb", query = "SELECT p FROM Provider p WHERE p.provWeb = :provWeb")})
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "provId")
    private Long provId;
    @Basic(optional = false)
    @Column(name = "provName")
    private String provName;
    @Column(name = "provEmployeeName")
    private String provEmployeeName;
    @Basic(optional = false)
    @Column(name = "provPhones")
    private String provPhones;
    @Basic(optional = false)
    @Column(name = "provAddress")
    private String provAddress;
    @Column(name = "provRut")
    private String provRut;
    @Column(name = "provNit")
    private String provNit;
    @Column(name = "provWeb")
    private String provWeb;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "provId")
    private List<Provides> providesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "provId")
    private List<Orders> ordersList;

    public Provider() {
    }

    public Provider(Long provId) {
        this.provId = provId;
    }

    public Provider(Long provId, String provName, String provPhones, String provAddress) {
        this.provId = provId;
        this.provName = provName;
        this.provPhones = provPhones;
        this.provAddress = provAddress;
    }

    public Long getProvId() {
        return provId;
    }

    public void setProvId(Long provId) {
        this.provId = provId;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getProvEmployeeName() {
        return provEmployeeName;
    }

    public void setProvEmployeeName(String provEmployeeName) {
        this.provEmployeeName = provEmployeeName;
    }

    public String getProvPhones() {
        return provPhones;
    }

    public void setProvPhones(String provPhones) {
        this.provPhones = provPhones;
    }

    public String getProvAddress() {
        return provAddress;
    }

    public void setProvAddress(String provAddress) {
        this.provAddress = provAddress;
    }

    public String getProvRut() {
        return provRut;
    }

    public void setProvRut(String provRut) {
        this.provRut = provRut;
    }

    public String getProvNit() {
        return provNit;
    }

    public void setProvNit(String provNit) {
        this.provNit = provNit;
    }

    public String getProvWeb() {
        return provWeb;
    }

    public void setProvWeb(String provWeb) {
        this.provWeb = provWeb;
    }

    @XmlTransient
    public List<Provides> getProvidesList() {
        return providesList;
    }

    public void setProvidesList(List<Provides> providesList) {
        this.providesList = providesList;
    }

    @XmlTransient
    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (provId != null ? provId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provider)) {
            return false;
        }
        Provider other = (Provider) object;
        if ((this.provId == null && other.provId != null) || (this.provId != null && !this.provId.equals(other.provId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Provider[ provId=" + provId + " ]";
    }
    
}
