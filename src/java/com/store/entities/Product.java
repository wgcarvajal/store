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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "product", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByProdId", query = "SELECT p FROM Product p WHERE p.prodId = :prodId"),
    @NamedQuery(name = "Product.findAllWithoutComposite", query = "SELECT p FROM Product p WHERE p.prodComposition IS NULL"),
    @NamedQuery(name = "Product.findByProdBarCode", query = "SELECT p FROM Product p WHERE p.prodBarCode = :prodBarCode"),
    @NamedQuery(name = "Product.findByProdName", query = "SELECT p FROM Product p WHERE p.prodName = :prodName"),
    @NamedQuery(name = "Product.searchProductName", query = "SELECT p,pr,pripur FROM Product p INNER JOIN Price pr INNER JOIN Pricepurchase pripur  WHERE LOWER(p.prodName) LIKE :prodName AND p.prodId = pr.prodId.prodId AND pr.priceFinalDate IS NULL AND p.prodId = pripur.prodId.prodId AND pripur.pricePurFinalDate IS NULL ORDER BY p.prodName ASC"),
    @NamedQuery(name = "Product.searchProductId", query = "SELECT p,pr,pripur FROM Product p INNER JOIN Price pr INNER JOIN Pricepurchase pripur  WHERE p.prodId LIKE :prodId AND p.prodId = pr.prodId.prodId AND pr.priceFinalDate IS NULL AND p.prodId = pripur.prodId.prodId AND pripur.pricePurFinalDate IS NULL"),
    @NamedQuery(name = "Product.findByProdStock", query = "SELECT p FROM Product p WHERE p.prodStock = :prodStock"),
    @NamedQuery(name = "Product.findByProdBaseNumber", query = "SELECT p FROM Product p WHERE p.prodBaseNumber = :prodBaseNumber"),
    @NamedQuery(name = "Product.findByProdUnitValue", query = "SELECT p FROM Product p WHERE p.prodUnitValue = :prodUnitValue"),
    @NamedQuery(name = "Product.findByCatId", query = "SELECT p FROM Product p WHERE p.catId.catId = :catId"),
    @NamedQuery(name = "Product.findByBrandId", query = "SELECT p FROM Product p WHERE p.brandId.brandId = :brandId")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prodId")
    private Long prodId;
    @Column(name = "prodBarCode")
    private String prodBarCode;
    @Basic(optional = false)
    @Column(name = "prodName")
    private String prodName;
    @Column(name = "prodNameBill")
    private String prodNameBill;
    @Basic(optional = false)
    @Column(name = "prodStock")
    private int prodStock;
    @Basic(optional = false)
    @Column(name = "prodIva")
    private int prodIva;
    @Basic(optional = false)
    @Column(name = "prodBaseNumber")
    private int prodBaseNumber;
    @Basic(optional = false)
    @Column(name = "prodUnitValue")
    private int prodUnitValue;
    @Column(name = "prodCompositionValue")
    private Integer prodCompositionValue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodId")
    private List<Pricepurchase> pricepurchaseList;
    @JoinColumn(name = "brandId", referencedColumnName = "brandId")
    @ManyToOne
    private Brand brandId;
    @JoinColumn(name = "ownId", referencedColumnName = "ownId")
    @ManyToOne(optional = false)
    private Owner ownId;
    @OneToMany(mappedBy = "prodComposition")
    private List<Product> productList;
    @JoinColumn(name = "prodComposition", referencedColumnName = "prodId")
    @ManyToOne
    private Product prodComposition;
    @JoinColumn(name = "prodtypeId", referencedColumnName = "prodtypeId")
    @ManyToOne(optional = false)
    private Producttype prodtypeId;
    @JoinColumn(name = "uniId", referencedColumnName = "uniId")
    @ManyToOne(optional = false)
    private Unity uniId;
    @JoinColumn(name = "catId", referencedColumnName = "catId")
    @ManyToOne(optional = false)
    private Category catId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodId")
    private List<Purchaseitem> purchaseitemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodId")
    private List<Productimage> productimageList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodId")
    private List<Price> priceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodId")
    private List<Provides> providesList;

    public Product() {
    }

    public Product(Long prodId) {
        this.prodId = prodId;
    }

    public Product(Long prodId, String prodName, int prodStock, int prodIva, int prodBaseNumber, int prodUnitValue) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodStock = prodStock;
        this.prodIva = prodIva;
        this.prodBaseNumber = prodBaseNumber;
        this.prodUnitValue = prodUnitValue;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public String getProdBarCode() {
        return prodBarCode;
    }

    public void setProdBarCode(String prodBarCode) {
        this.prodBarCode = prodBarCode;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdNameBill() {
        return prodNameBill;
    }

    public void setProdNameBill(String prodNameBill) {
        this.prodNameBill = prodNameBill;
    }

    public int getProdStock() {
        return prodStock;
    }

    public void setProdStock(int prodStock) {
        this.prodStock = prodStock;
    }

    public int getProdIva() {
        return prodIva;
    }

    public void setProdIva(int prodIva) {
        this.prodIva = prodIva;
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

    public Integer getProdCompositionValue() {
        return prodCompositionValue;
    }

    public void setProdCompositionValue(Integer prodCompositionValue) {
        this.prodCompositionValue = prodCompositionValue;
    }

    @XmlTransient
    public List<Pricepurchase> getPricepurchaseList() {
        return pricepurchaseList;
    }

    public void setPricepurchaseList(List<Pricepurchase> pricepurchaseList) {
        this.pricepurchaseList = pricepurchaseList;
    }

    public Brand getBrandId() {
        return brandId;
    }

    public void setBrandId(Brand brandId) {
        this.brandId = brandId;
    }

    public Owner getOwnId() {
        return ownId;
    }

    public void setOwnId(Owner ownId) {
        this.ownId = ownId;
    }

    @XmlTransient
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Product getProdComposition() {
        return prodComposition;
    }

    public void setProdComposition(Product prodComposition) {
        this.prodComposition = prodComposition;
    }

    public Producttype getProdtypeId() {
        return prodtypeId;
    }

    public void setProdtypeId(Producttype prodtypeId) {
        this.prodtypeId = prodtypeId;
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
        return "entities.Product[ prodId=" + prodId + " ]";
    }
    
}
