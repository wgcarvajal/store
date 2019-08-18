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
@Table(name = "producttype", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producttype.findAll", query = "SELECT p FROM Producttype p"),
    @NamedQuery(name = "Producttype.findByProdtypeId", query = "SELECT p FROM Producttype p WHERE p.prodtypeId = :prodtypeId"),
    @NamedQuery(name = "Producttype.findByProdtypeValue", query = "SELECT p FROM Producttype p WHERE p.prodtypeValue = :prodtypeValue")})
public class Producttype implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prodtypeId")
    private Integer prodtypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "prodtypeValue")
    private String prodtypeValue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodtypeId")
    private List<Product> productList;

    public Producttype() {
    }

    public Producttype(Integer prodtypeId) {
        this.prodtypeId = prodtypeId;
    }

    public Producttype(Integer prodtypeId, String prodtypeValue) {
        this.prodtypeId = prodtypeId;
        this.prodtypeValue = prodtypeValue;
    }

    public Integer getProdtypeId() {
        return prodtypeId;
    }

    public void setProdtypeId(Integer prodtypeId) {
        this.prodtypeId = prodtypeId;
    }

    public String getProdtypeValue() {
        return prodtypeValue;
    }

    public void setProdtypeValue(String prodtypeValue) {
        this.prodtypeValue = prodtypeValue;
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
        hash += (prodtypeId != null ? prodtypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producttype)) {
            return false;
        }
        Producttype other = (Producttype) object;
        if ((this.prodtypeId == null && other.prodtypeId != null) || (this.prodtypeId != null && !this.prodtypeId.equals(other.prodtypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.Producttype[ prodtypeId=" + prodtypeId + " ]";
    }
    
}
