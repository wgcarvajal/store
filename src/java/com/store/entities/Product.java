/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "product", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByProdId", query = "SELECT p FROM Product p WHERE p.prodId = :prodId"),
    @NamedQuery(name = "Product.findByProdBarCode", query = "SELECT p FROM Product p WHERE p.prodBarCode = :prodBarCode"),
    @NamedQuery(name = "Product.findByProdName", query = "SELECT p FROM Product p WHERE p.prodName = :prodName"),
    @NamedQuery(name = "Product.findByProdStock", query = "SELECT p FROM Product p WHERE p.prodStock = :prodStock"),
    @NamedQuery(name = "Product.findByProdBaseNumber", query = "SELECT p FROM Product p WHERE p.prodBaseNumber = :prodBaseNumber"),
    @NamedQuery(name = "Product.findByProdUnitValue", query = "SELECT p FROM Product p WHERE p.prodUnitValue = :prodUnitValue")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prodId")
    private Long prodId;
    @Column(name = "prodBarCode")
    private BigInteger prodBarCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "prodName")
    private String prodName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prodStock")
    private int prodStock;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prodBaseNumber")
    private int prodBaseNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prodUnitValue")
    private int prodUnitValue;
    @JoinColumn(name = "brandId", referencedColumnName = "brandId")
    @ManyToOne
    private Brand brandId;
    @JoinColumn(name = "uniId", referencedColumnName = "uniId")
    @ManyToOne(optional = false)
    private Unity uniId;
    @JoinColumn(name = "catId", referencedColumnName = "catId")
    @ManyToOne(optional = false)
    private Category catId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodId")
    private List<Price> priceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodId")
    private List<Provides> providesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodId")
    private List<Purchaseitem> purchaseitemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodId")
    private List<Productimage> productimageList;

    public Product() {
    }

    public Product(Long prodId) {
        this.prodId = prodId;
    }

    public Product(Long prodId, String prodName, int prodStock, int prodBaseNumber, int prodUnitValue) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodStock = prodStock;
        this.prodBaseNumber = prodBaseNumber;
        this.prodUnitValue = prodUnitValue;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public BigInteger getProdBarCode() {
        return prodBarCode;
    }

    public void setProdBarCode(BigInteger prodBarCode) {
        this.prodBarCode = prodBarCode;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public int getProdStock() {
        return prodStock;
    }

    public void setProdStock(int prodStock) {
        this.prodStock = prodStock;
    }

    public int getProdBaseNumber() {
        return prodBaseNumber;
    }

    public void setProdBaseNumber(int prodBaseNumber) {
        this.prodBaseNumber = prodBaseNumber;
    }

    public int getProdUnitValue() {
        return prodUnitValue;
    }

    public void setProdUnitValue(int prodUnitValue) {
        this.prodUnitValue = prodUnitValue;
    }

    public Brand getBrandId() {
        return brandId;
    }

    public void setBrandId(Brand brandId) {
        this.brandId = brandId;
    }

    public Unity getUniId() {
        return uniId;
    }

    public void setUniId(Unity uniId) {
        this.uniId = uniId;
    }

    public Category getCatId() {
        return catId;
    }

    public void setCatId(Category catId) {
        this.catId = catId;
    }

    @XmlTransient
    public List<Price> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    @XmlTransient
    public List<Provides> getProvidesList() {
        return providesList;
    }

    public void setProvidesList(List<Provides> providesList) {
        this.providesList = providesList;
    }

    @XmlTransient
    public List<Purchaseitem> getPurchaseitemList() {
        return purchaseitemList;
    }

    public void setPurchaseitemList(List<Purchaseitem> purchaseitemList) {
        this.purchaseitemList = purchaseitemList;
    }

    @XmlTransient
    public List<Productimage> getProductimageList() {
        return productimageList;
    }

    public void setProductimageList(List<Productimage> productimageList) {
        this.productimageList = productimageList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prodId != null ? prodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.prodId == null && other.prodId != null) || (this.prodId != null && !this.prodId.equals(other.prodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.store.entities.Product[ prodId=" + prodId + " ]";
    }
    
}
