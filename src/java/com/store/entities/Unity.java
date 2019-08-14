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
@Table(name = "unity", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unity.findAll", query = "SELECT u FROM Unity u"),
    @NamedQuery(name = "Unity.findByUniId", query = "SELECT u FROM Unity u WHERE u.uniId = :uniId"),
    @NamedQuery(name = "Unity.findByUniName", query = "SELECT u FROM Unity u WHERE u.uniName = :uniName"),
    @NamedQuery(name = "Unity.findByUniAbbreviation", query = "SELECT u FROM Unity u WHERE u.uniAbbreviation = :uniAbbreviation")})
public class Unity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "uniId")
    private Integer uniId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "uniName")
    private String uniName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "uniAbbreviation")
    private String uniAbbreviation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uniId")
    private List<Product> productList;

    public Unity() {
    }

    public Unity(Integer uniId) {
        this.uniId = uniId;
    }

    public Unity(Integer uniId, String uniName, String uniAbbreviation) {
        this.uniId = uniId;
        this.uniName = uniName;
        this.uniAbbreviation = uniAbbreviation;
    }

    public Integer getUniId() {
        return uniId;
    }

    public void setUniId(Integer uniId) {
        this.uniId = uniId;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getUniAbbreviation() {
        return uniAbbreviation;
    }

    public void setUniAbbreviation(String uniAbbreviation) {
        this.uniAbbreviation = uniAbbreviation;
    }

    @XmlTransient
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uniId != null ? uniId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unity)) {
            return false;
        }
        Unity other = (Unity) object;
        if ((this.uniId == null && other.uniId != null) || (this.uniId != null && !this.uniId.equals(other.uniId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.store.entities.Unity[ uniId=" + uniId + " ]";
    }
    
}
